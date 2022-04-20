package controller;

import DAO.AppointmentsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactsReport implements Initializable {
    public TableView contactsReportTable;
    public TableColumn contactsReportIDCol;
    public TableColumn contactsReportTitleCol;
    public TableColumn contactReportDescriptionCol;
    public TableColumn contactsReportLocationCol;
    public TableColumn contactsReportTypeCol;
    public TableColumn contactsReportStartCol;
    public TableColumn contactsReportEndCol;
    public TableColumn contactsReportCustomerIDCol;
    public TableColumn contactsReportUserIDCol;
    public TableColumn contactsReportContactIDCol;
    public Button backToReportsButton;
    /**
     * Static member used to generate the report for this contact
     */
    private static int contactID = 0;

    /**
     * Back to the previous page
     * @param actionEvent Click on the "Back to reports page" button
     * @throws IOException
     */
    public void onBackToReportsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reporting.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("Generate Reports");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the table with the data from the report
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactID = Reporting.getSelectedContact();
        } catch (NullPointerException e) {

        }
        if(contactID == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a contact");
            alert.showAndWait();

        } else {
            contactsReportIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            contactsReportTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            contactReportDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            contactsReportTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            contactsReportStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            contactsReportEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            contactsReportCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            contactsReportUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            contactsReportContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            contactsReportTable.setItems(AppointmentsDAO.getContactsReport(contactID));
        }
    }
}
