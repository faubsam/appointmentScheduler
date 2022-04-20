package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.TimeConversions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

public class ModifyAppointment implements Initializable {
    public Button modifyAppointmentSaveButton;
    public Button modifyAppointmentCancelButton;
    public TextField modifyAppointmentAppointmentID;
    public TextField modifyAppointmentTitle;
    public TextField modifyAppointmentLocation;
    public TextField modifyAppointmentDescription;
    public TextField modifyAppointmentType;
    public TextField modifyAppointmentCustomerID;
    public TextField modifyAppointmentUserID;
    public ComboBox<Contact> modifyAppointmentContact;
    public DatePicker modifyAppointmentEndDate;
    public DatePicker modifyAppointmentStartDate;
    public ComboBox<LocalTime> modifyAppointmentStartTime;
    public ComboBox<LocalTime> modifyAppointmentEndTime;
    public ComboBox<Customer> modifyAppointmentCustomer;
    public ComboBox<User> modifyAppointmentUser;
    LocalTime start;
    LocalTime end;

    /**
     * Appointment variable to hold the appointment to modify.
     */
    private static Appointment a;

    /**
     * Populate the fields with data from the appointment to modify.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            a = ViewAppointments.getSelectedAppointment();
        } catch (NullPointerException e) {

        }
        ZonedDateTime estStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime currentZoneStart = estStart.withZoneSameInstant(ZoneId.systemDefault());

        ZonedDateTime estEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22,0),ZoneId.of("America/New_York"));
        ZonedDateTime currentZoneEnd = estEnd.withZoneSameInstant(ZoneId.systemDefault());
        int count = 0;
        while(currentZoneStart.isBefore(currentZoneEnd.plusMinutes(1))) {
            modifyAppointmentStartTime.getItems().add(currentZoneStart.toLocalTime());
            modifyAppointmentEndTime.getItems().add(currentZoneStart.toLocalTime());
            currentZoneStart = currentZoneStart.plusMinutes(15);
            count++;
        }
        modifyAppointmentStartTime.getItems().remove(count-1);
        modifyAppointmentEndTime.getItems().remove(0);

        start = a.getStart().toLocalTime();
        end = a.getEnd().toLocalTime();
        modifyAppointmentEndTime.setValue(end);
        modifyAppointmentStartTime.setValue(start);

        try {
            ObservableList<Contact> contacts = ContactsDAO.getAll();
            modifyAppointmentContact.setItems(contacts);
            ObservableList<Customer> customers = CustomerDAO.getAll();
            modifyAppointmentCustomer.setItems(customers);

            ObservableList<User> users = UserDAO.getAll();
            modifyAppointmentUser.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        modifyAppointmentAppointmentID.setText(String.valueOf(a.getAppointmentID()));
        modifyAppointmentContact.setValue(ContactsDAO.get(a.getContactID()));
        modifyAppointmentTitle.setText(a.getTitle());
        modifyAppointmentDescription.setText(a.getDescription());
        modifyAppointmentLocation.setText(a.getLocation());
        modifyAppointmentType.setText(a.getType());
        modifyAppointmentCustomer.setValue(CustomerDAO.get(a.getCustomerID()));

        try {
            modifyAppointmentUser.setValue(UserDAO.get(a.getUserID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        modifyAppointmentStartDate.setValue(a.getStart().toLocalDate());
        modifyAppointmentEndDate.setValue(a.getEnd().toLocalDate());

    }

    /**
     * Save the appointment with modifications to the DB.
     * Verify times for overlapping appointments.
     * @param actionEvent Click on the save button
     * @throws IOException
     * @throws SQLException
     */
    public void onModifyAppointmentSaveButton(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            LocalDate startDate = modifyAppointmentStartDate.getValue();
            LocalTime startTime = modifyAppointmentStartTime.getValue();
            LocalDate endDate = modifyAppointmentEndDate.getValue();
            LocalTime endTime = modifyAppointmentEndTime.getValue();

            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);
            int customerID = Integer.parseInt(String.valueOf(modifyAppointmentCustomer.getSelectionModel().getSelectedItem().getCustomerID()));
            String title = modifyAppointmentTitle.getText();
            String description = modifyAppointmentDescription.getText();
            String location = modifyAppointmentLocation.getText();
            String type = modifyAppointmentType.getText();

            if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill out all of the required fields.");
                alert.showAndWait();
            } else {
                int id = a.getAppointmentID();
                a.setTitle(title);
                a.setDescription(description);
                a.setLocation(location);
                a.setType(type);
                a.setCustomerID(customerID);
                a.setUserID(Integer.parseInt(String.valueOf(modifyAppointmentUser.getSelectionModel().getSelectedItem().getUserID())));
                a.setContactID(Integer.parseInt(String.valueOf(modifyAppointmentContact.getSelectionModel().getSelectedItem().getContactID())));
                a.setStart(start);
                a.setEnd(end);

                if (start.isEqual(end) || start.isAfter(end)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Your appointment end time must be after the start time");
                    alert.showAndWait();
                } else if (!startDate.isEqual(endDate)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Your appointment end and start date must be on the same day");
                    alert.showAndWait();
                } else {

                    int conflict = Appointment.checkForOverlappingAppointment(start, end, customerID);
                    if (conflict == 0 || conflict == id) {
                        AppointmentsDAO.update(a);
                        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 800, 600);
                        stage.setTitle("View Appointments");
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Your appointment overlaps with an existing appointment. Please adjust the times. Conflicting appointment ID: " + conflict);
                        alert.showAndWait();
                    }

                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid data entered - Customer ID or User ID does not exist. Please try again");
            alert.showAndWait();
            e.printStackTrace();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill out all of the required fields.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Cancels all modifications to the appointment and returns to the ViewAppointments page.
     * @param actionEvent Click on the Cancel button.
     * @throws IOException
     */
    public void onModifyAppointmentCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("View Appointments");
        stage.setScene(scene);
        stage.show();
    }

}
