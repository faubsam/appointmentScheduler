package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    public Button addCustomerSaveButton;
    public Button addCustomerCancelButton;
    public ComboBox<FirstLevelDivision> addCustomerDivision;
    public ComboBox<Country> addCustomerCountry;
    public TextField addCustomerID;
    public TextField addCustomerName;
    public TextField addCustomerAddress;
    public TextField addCustomerPostalCode;
    public TextField addCustomerPhone;
    /**
     * Country variable used to get the list of divisions
     */
    private static Country country;

    /**
     * Initializes the country combo box with all available countries
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> countries = CountryDAO.getAll();
        addCustomerCountry.setItems(countries);

    }

    /**
     * Saves the customer to the DB
     * @param actionEvent Click on the save button
     * @throws Exception
     */
    public void onAddCustomerSaveButton(ActionEvent actionEvent) throws Exception {
        try {
            int id = 0;
            String name = addCustomerName.getText();
            String address = addCustomerAddress.getText();
            String postal = addCustomerPostalCode.getText();
            String phone = addCustomerPhone.getText();

            int divisionID = addCustomerDivision.getValue().getDivisionID();
            if(name.isBlank() || address.isBlank() || postal.isBlank() || phone.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill out all of the required fields.");
                alert.showAndWait();
            } else {
                Customer c = new Customer(id, name, address, postal, phone, divisionID);
                CustomerDAO.insert(c);
                Parent root = FXMLLoader.load(getClass().getResource("/view/ViewCustomer.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1074, 795);
                stage.setTitle("Customers Page");
                stage.setScene(scene);
                stage.show();
            }

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill out all of the required fields.");
            alert.showAndWait();
        }

    }

    /**
     * Cancels the data and returns
     * @param actionEvent click on the cancel button
     * @throws IOException
     */
    public void onAddCustomerCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("Customers Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the divisions combo box based on the selected country
     * @param actionEvent Click on the combo box and select a country from the list
     */
    public void onAddCustomerCountry(ActionEvent actionEvent) {
        country = addCustomerCountry.getSelectionModel().getSelectedItem();
        if (country != null) {
            ObservableList<FirstLevelDivision> divs = DivisionDAO.getDivisionsForCountry(country.getCountryID());
            addCustomerDivision.setItems(divs);
            addCustomerDivision.setVisibleRowCount(5);
        }
    }

    public void onAddCustomerDivision(ActionEvent actionEvent) {
    }
}
