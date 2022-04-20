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

public class AddAppointment implements Initializable {
    public Button addAppointmentSaveButton;
    public Button addAppointmentCancelButton;
    public TextField addAppointmentAppointmentID;
    public TextField addAppointmentTitle;
    public TextField addAppointmentLocation;
    public TextField addAppointmentDescription;
    public TextField addAppointmentType;
    public TextField addAppointmentCustomerID;
    public ComboBox<Contact> addAppointmentContact;
    public TextField addAppointmentUserID;
    public DatePicker addAppointmentStartDate;
    public DatePicker addAppointmentEndDate;
    public ComboBox<LocalTime> addAppointmentEndTime;
    public ComboBox<LocalTime> addAppointmentStartTime;
    public ComboBox<Customer> addAppointmentCustomer;
    public ComboBox<User> addAppointmentUser;


    /**
     * Initializes the add appointment screen by filling the combo boxes with time intervals of 15 minutes.
     * Sets the contacts combo box with the list of contacts
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZonedDateTime estStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime currentZoneStart = estStart.withZoneSameInstant(ZoneId.systemDefault());

        ZonedDateTime estEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22,0),ZoneId.of("America/New_York"));
        ZonedDateTime currentZoneEnd = estEnd.withZoneSameInstant(ZoneId.systemDefault());
        int count = 0;
        while(currentZoneStart.isBefore(currentZoneEnd.plusMinutes(1))) {
            addAppointmentStartTime.getItems().add(currentZoneStart.toLocalTime());
            addAppointmentEndTime.getItems().add(currentZoneStart.toLocalTime());
            currentZoneStart = currentZoneStart.plusMinutes(15);
            count++;

        }
        addAppointmentStartTime.getItems().remove(count-1);
        addAppointmentEndTime.getItems().remove(0);

        try {
            ObservableList<Contact> contacts = ContactsDAO.getAll();
            addAppointmentContact.setItems(contacts);
            ObservableList<Customer> customers = CustomerDAO.getAll();
            addAppointmentCustomer.setItems(customers);

            ObservableList<User> users = UserDAO.getAll();
            addAppointmentUser.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Saves the apppointment to the database after converting the time.
     * @param actionEvent Click on the save button
     * @throws IOException
     */
    public void onAddAppointmentSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            LocalDate startDate = addAppointmentStartDate.getValue();
            LocalTime startTime = addAppointmentStartTime.getValue();
            LocalDate endDate = addAppointmentEndDate.getValue();
            LocalTime endTime = addAppointmentEndTime.getValue();

            int id = 0;

            String title = addAppointmentTitle.getText();
            String description = addAppointmentDescription.getText();
            String location = addAppointmentLocation.getText();
            String type = addAppointmentType.getText();
            LocalDateTime start = TimeConversions.toUTCTime(LocalDateTime.of(startDate, startTime));
            LocalDateTime end = TimeConversions.toUTCTime(LocalDateTime.of(endDate, endTime));
            int customerID = Integer.parseInt(String.valueOf(addAppointmentCustomer.getSelectionModel().getSelectedItem().getCustomerID()));
            int userID = Integer.parseInt(String.valueOf(addAppointmentUser.getSelectionModel().getSelectedItem().getUserID()));
            int contactID = Integer.parseInt(String.valueOf(addAppointmentContact.getSelectionModel().getSelectedItem().getContactID()));

            if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill out all of the required fields.");
                alert.showAndWait();
            } else {
                if (start.isEqual(end) || start.isAfter(end)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Your appointment end time must be after the start time");
                    alert.showAndWait();
                } else if (!start.toLocalDate().isEqual(end.toLocalDate())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Your appointment end and start date must be on the same day");
                    alert.showAndWait();
                } else {

                    int conflict = Appointment.checkForOverlappingAppointment(start, end, customerID);
                    if (conflict == 0) {
                        Appointment a = new Appointment(id, title, description, location, type, start, end, customerID, userID, contactID);
                        AppointmentsDAO.insert(a);
                        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 1074, 795);
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

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill out all of the required fields.");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid data entered - Customer ID or User ID does not exist. Please try again");
            alert.showAndWait();
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill out all of the required fields.");
            alert.showAndWait();
            e.printStackTrace();
        }

    }

    /**
     * Cancels the data and goes back to the previous page.
     * @param actionEvent Click on the cancel button
     * @throws IOException
     */
    public void onAddAppointmentCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("View Appointments");
        stage.setScene(scene);
        stage.show();
    }


}
