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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CountryReport;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CountryReporting implements Initializable {
    public TableView countryReportTable;
    public TableColumn countryReportCountryCol;
    public TableColumn countryReportNumCol;
    public Button backToReportsButton;

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
     * Populates the table with the data from the 3rd report
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            countryReportCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
            countryReportNumCol.setCellValueFactory(new PropertyValueFactory<>("num"));
            countryReportTable.setItems(AppointmentsDAO.getAppointmentsByCountryReport());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
