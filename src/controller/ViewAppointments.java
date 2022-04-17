package controller;

import DAO.AppointmentsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewAppointments implements Initializable{
    public RadioButton viewAppointmentsByMonthButton;
    public ToggleGroup viewAppointmentsGroup;
    public RadioButton viewAllAppointmentsButton;
    public RadioButton viewAppointmentsByWeekButton;
    public TableView viewAppointmentsTable;
    public Button backToHomeButton;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public TableColumn viewAppointmentsID;
    public TableColumn viewAppointmentsTitle;
    public TableColumn viewAppointmentsDescription;
    public TableColumn viewAppointmentsLocation;
    public TableColumn viewAppointmentsType;
    public TableColumn viewAppointmentsStart;
    public TableColumn viewAppointmentsEnd;
    public TableColumn viewAppointmentsCustomerID;
    public TableColumn viewAppointmentsUserID;
    public TableColumn viewAppointmentsContactID;
    private static ObservableList<Appointment> appointments;
    private static Appointment selectedAppointment = null;

    /**
     * View all appointments from the db in the table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = AppointmentsDAO.getAll();

        viewAppointmentsID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        viewAppointmentsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        viewAppointmentsDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        viewAppointmentsLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        viewAppointmentsType.setCellValueFactory(new PropertyValueFactory<>("type"));
        viewAppointmentsStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        viewAppointmentsEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        viewAppointmentsCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        viewAppointmentsUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        viewAppointmentsContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        viewAppointmentsTable.setItems(appointments);

    }

    /**
     * Button to view appointments by month and display the filtered list in the table.
     *
     * <p> LAMBDA EXPRESSION is used to filter the appointments using the stream API in order the view appointments
     * for the upcoming 31 days starting from the current day. The lambda makes use of the appointments' parameters
     * to filter the result and return an observable list object.</p>
     * @param actionEvent on select radio button
     */
    public void onViewAppointmentsByMonthButton(ActionEvent actionEvent) {
        appointments = AppointmentsDAO.getAll();
        LocalDateTime monthEnd = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth());

        Stream<Appointment> apptsStream = appointments.stream();
        ObservableList<Appointment> apptsResults = apptsStream.filter(appointment -> appointment.getStart()
                .isBefore(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).withHour(23)))
                .filter(appointment -> appointment.getStart().isAfter(LocalDateTime.now().minusMinutes(15)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        viewAppointmentsTable.setItems(apptsResults);
    }

    /**
     * Button to view appointments by week and display the filtered list in the table.
     *
     * <p>LAMBDA EXPRESSION is used to filter the appointments using the stream API in order the view appointments
     * for the upcoming 7 days starting from the current day. The lambda makes use of the appointments' parameters
     * to filter the result and return an observable list object.</p>
     * @param actionEvent on select radio button
     */
    public void onViewAppointmentsByWeekButton(ActionEvent actionEvent) {
        appointments = AppointmentsDAO.getAll();


        Stream<Appointment> apptsStream = appointments.stream();
        ObservableList<Appointment> apptsResults = apptsStream.filter(appointment -> appointment.getStart()
                .isBefore(LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(23)))
                .filter(appointment -> appointment.getStart().isAfter(LocalDateTime.now().minusMinutes(15)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        viewAppointmentsTable.setItems(apptsResults);
    }

    /**
     * Button to view all appointments.
     * @param actionEvent select the radio button
     */
    public void onViewAllAppointmentsButton(ActionEvent actionEvent) {
        appointments = AppointmentsDAO.getAll();
        viewAppointmentsTable.setItems(appointments);
    }

    /**
     * Back to home page.
     * @param actionEvent click on the "Back to Home" button.
     * @throws IOException
     */
    public void onBackToHomeButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Add a new appointment.
     * @param actionEvent Click on the "Add Appointment" button
     * @throws IOException
     */
    public void onAddAppointmentButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("New Appointment");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Modify an existing appointment.
     * @param actionEvent Click on the "Modify Appointment" button.
     * @throws IOException
     */
    public void onModifyAppointmentButton(ActionEvent actionEvent) throws IOException {
        selectedAppointment = (Appointment) viewAppointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1074, 795);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select an appointment to modify");
            alert.showAndWait();
        }
    }

    /**
     * Delete an appointment and reload the updated table.
     * @param actionEvent Click on the Delete button.
     * @throws SQLException
     */
    public void onDeleteAppointmentButton(ActionEvent actionEvent) throws SQLException {
        selectedAppointment = (Appointment) viewAppointmentsTable.getSelectionModel().getSelectedItem();
        String apptType = selectedAppointment.getType();
        int apptID = selectedAppointment.getAppointmentID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this " + apptType + " appointment? Appointment ID: " + apptID);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int aid = selectedAppointment.getAppointmentID();
            AppointmentsDAO.delete(aid);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Appointment with ID: " + aid + " and type \"" + apptType + "\" has been deleted");
            alert2.showAndWait();
        }
        appointments = AppointmentsDAO.getAll();
        viewAppointmentsTable.setItems(appointments);
    }

    /**
     * Return selected appointment for use in time conversion functions
     * @return Appointment object
     */
    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }
}
