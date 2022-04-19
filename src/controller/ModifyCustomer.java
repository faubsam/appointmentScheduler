package controller;

import DAO.CountryDAO;
import DAO.DivisionDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Country;
import model.Customer;
import controller.ViewCustomer;
import DAO.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomer implements Initializable {
    public Button modifyCustomerSaveButton;
    public Button modifyCustomerCancelButton;
    public TextField modifyCustomerPhone;
    public TextField modifyCustomerPostalCode;
    public TextField modifyCustomerAddress;
    public TextField modifyCustomerName;
    public TextField modifyCustomerID;
    public ComboBox<Country> modifyCustomerCountry;
    public ComboBox<FirstLevelDivision> modifyCustomerDivision;
    /**
     * Customer object to modify
     */
    private static Customer c;
    /**
     * List of all countries from the db
     */
    private static ObservableList<Country> countries = CountryDAO.getAll();

    /**
     * Populates the fields with data from the customer to modify.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            c = ViewCustomer.getSelectedCustomer();
        } catch (NullPointerException e) {

        }
        modifyCustomerID.setText(String.valueOf(c.getCustomerID()));
        modifyCustomerPhone.setText(c.getPhone());
        modifyCustomerAddress.setText(c.getAddress());
        modifyCustomerName.setText(c.getName());
        modifyCustomerPostalCode.setText(c.getPostalCode());
        int customerDivID = c.getDivisionID();
        int customerCountryID = CountryDAO.getCountryFromDivision(customerDivID);


        modifyCustomerCountry.setItems(countries);
        modifyCustomerCountry.setValue(CountryDAO.get(customerCountryID));
        ObservableList<FirstLevelDivision> divs = DivisionDAO.getDivisionsForCountry(customerCountryID);
        modifyCustomerDivision.setItems(divs);
        modifyCustomerDivision.setValue(DivisionDAO.get(customerDivID));


    }

    /**
     * Save the modifications to the DB.
     *
     * @param actionEvent Click on the save button
     * @throws IOException
     */
    public void onModifyCustomerSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = c.getCustomerID();
            c.setName(modifyCustomerName.getText());
            c.setAddress(modifyCustomerAddress.getText());
            c.setPhone(modifyCustomerPhone.getText());
            c.setPostalCode(modifyCustomerPostalCode.getText());
            c.setDivisionID(modifyCustomerDivision.getSelectionModel().getSelectedItem().getDivisionID());
            if(modifyCustomerAddress.getText().isBlank() || modifyCustomerName.getText().isBlank() ||
            modifyCustomerPhone.getText().isBlank() || modifyCustomerPostalCode.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill out all of the required fields.");
                alert.showAndWait();
            } else {
                CustomerDAO.update(id, c);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Cancel the modifications to the customer.
     *
     * @param actionEvent Click on the cancel button
     * @throws IOException
     */
    public void onModifyCustomerCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ViewCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("Customers Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Update the divisions combo box if the customer's country changes.
     *
     * @param actionEvent Select a country from the Combo Box
     */
    public void onModifyCustomerCountry(ActionEvent actionEvent) {
        Country c = modifyCustomerCountry.getSelectionModel().getSelectedItem();
        if (c != null) {
            ObservableList<FirstLevelDivision> divs = DivisionDAO.getDivisionsForCountry(c.getCountryID());
            modifyCustomerDivision.setItems(divs);
            modifyCustomerDivision.setVisibleRowCount(5);
        }
    }

}
