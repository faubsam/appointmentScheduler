package controller;

import DAO.AppointmentsDAO;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerReporting implements Initializable {
    public TableView customerReportTable;
    public Button backToReportsButton;
    public TableColumn customerReportTypeCol;
    public TableColumn customerReportMonthCol;
    public TableColumn customerReportCountCol;

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
     * Populates the table with data from the report by type and month
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerReportCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
            customerReportMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
            customerReportTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            customerReportTable.setItems(AppointmentsDAO.getAppointmentByTypeAndMonthReport());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
