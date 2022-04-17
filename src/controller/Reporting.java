package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Reporting implements Initializable {
    public Button generateReportButton;
    public ToggleGroup reportSelectorGroup;
    public Button backToHomeButton;
    public RadioButton appointmentsByTypeButton;
    public RadioButton appointmentsByContactsButton;
    public RadioButton appointmentsByCountryButton;
    /**
     * List of all contacts
     */
    private static ObservableList<Contact> contactsList = ContactsDAO.getAll();
    /**
     * Combo box to select the contact for the report
     */
    public ComboBox<Contact> contactsReportComboBox;
    /**
     * Get the selected contact object for the report
     */
    private static Contact selectedContact;

    /**
     * Set the combo box to choose the contact
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsReportComboBox.setItems(contactsList);
    }

    /**
     * Generate report button based on the selected report.
     * The first report generates data for the selected contact.
     * The second report shows appointments by type and month.
     * The third report shows the number of appointments by country.
     * @param actionEvent Select the radio button for the report and click on the "Generate Report" button.
     * @throws SQLException
     * @throws IOException
     */
    public void onGenerateReportButton(ActionEvent actionEvent) throws SQLException, IOException {
        if (appointmentsByContactsButton.isSelected()) {
            selectedContact = contactsReportComboBox.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("/view/contactsReport.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1074, 795);
            stage.setTitle("Contact Report");
            stage.setScene(scene);
            stage.show();
        } else if(appointmentsByCountryButton.isSelected()) {

            Parent root = FXMLLoader.load(getClass().getResource("/view/countryReport.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1074, 795);
            stage.setTitle("Country Report");
            stage.setScene(scene);
            stage.show();
        } else if (appointmentsByTypeButton.isSelected()) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/customerReportPage.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1074, 795);
            stage.setTitle("Appointments by type and month Report");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Back to the home page.
     * @param actionEvent Click on the "Back to Home" button
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
     * Return the ID of the selected contact.
     * @return int contactID
     */
    public static int getSelectedContact() {
        return selectedContact.getContactID();
    }
}
