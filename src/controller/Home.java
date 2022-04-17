package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public Button homeAppointmentsButton;
    public Button homeCustomersButton;
    public Button homeReportsButton;

    /**
     * Application home page initialized with 3 buttons
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Go to the ViewAppointments page
     * @param actionEvent Click on the Appointments button
     * @throws IOException
     */
    public void onHomeAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("View Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Go to the ViewCustomer page
     * @param actionEvent Click on the Customers button
     * @throws IOException
     */
    public void onHomeCustomersButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("Customers Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Go to the Reporting page
     * @param actionEvent Click on the button
     * @throws IOException
     */
    public void onHomeReportsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reporting.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("Generate Reports");
        stage.setScene(scene);
        stage.show();
    }
}
