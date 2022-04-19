package controller;

import DAO.AppointmentsDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCustomer implements Initializable {
    public Button backToHomeButton;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public TableView<Customer> customersTable;
    public TableColumn customersTableID;
    public TableColumn customersTableName;
    public TableColumn customersTablePostalCode;
    public TableColumn customersTableAddress;
    public TableColumn customersTablePhone;

    public TableColumn customersTableDivision;
    /**
     * Customer to modify which will be selected in the TableView.
     */
    private static Customer selectedCustomer = null;
    /**
     * List of divisions in the db
     */
    private static ObservableList<FirstLevelDivision> divs = DivisionDAO.getAll();

    /**
     * List of customers
     */
    private static ObservableList<Customer> customers = CustomerDAO.getAll();
    /**
     * List of countries
     */
    private static ObservableList<Country> countries = CountryDAO.getAll();
    /**
     * Initialize the table with all customers.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //customers = CustomerDAO.getAll();

        customersTableID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customersTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customersTableAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customersTablePostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customersTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customersTableDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customersTable.setItems(customers);

        customersTableName.setCellFactory(TextFieldTableCell.forTableColumn());
        customersTableAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        customersTablePostalCode.setCellFactory(TextFieldTableCell.forTableColumn());
        customersTablePhone.setCellFactory(TextFieldTableCell.forTableColumn());
        customersTableDivision.setCellFactory(ComboBoxTableCell.forTableColumn(divs));

    }

    /**
     * Back to the application home page.
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
     * Go to the "Add Customer" screen.
     * @param actionEvent Click on the "Add Customer" button.
     * @throws IOException
     */
    public void onAddCustomerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074, 795);
        stage.setTitle("New Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Go to the "Modify Customer" screen
     * @param actionEvent Click on the "Modify Customer" button
     * @throws IOException
     */
    public void onModifyCustomerButton(ActionEvent actionEvent) throws IOException {
        selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1074, 795);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a customer to modify");
            alert.showAndWait();
        }
    }

    /**
     * Delete a customer from the table
     * @param actionEvent Click on the "Delete Customer" button
     * @throws SQLException
     */
    public void onDeleteCustomerButton(ActionEvent actionEvent) throws SQLException {
        selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();

        if(AppointmentsDAO.getAllAppointmentsForCustomer(selectedCustomer.getCustomerID()).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int custID = selectedCustomer.getCustomerID();
                CustomerDAO.delete(custID);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Customer with ID: " + selectedCustomer.getCustomerID() + " has been deleted");
                alert2.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Cannot delete a customer that still has appointments.");
            alert.showAndWait();
        }

        customers = CustomerDAO.getAll();
        customersTable.setItems(customers);


    }

    /**
     * Return selected customer to modify
     * @return Customer object
     */
    public static Customer getSelectedCustomer(){
        return selectedCustomer;
    }

    /**
     * Update a customer in the table
     * @param cellEditEvent Double click on the cell
     * @throws SQLException
     */
    public void onCustomersTableNameCommit(TableColumn.CellEditEvent cellEditEvent) throws SQLException {
        selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        String newName = cellEditEvent.getNewValue().toString();
        if (!newName.isBlank()) {
            selectedCustomer.setName(cellEditEvent.getNewValue().toString());
            CustomerDAO.update(selectedCustomer.getCustomerID(), selectedCustomer);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This field cannot be empty.");
            alert.showAndWait();

        }

    }

    /**
     * Update a customer address in the table
     * @param cellEditEvent Double click on the cell
     * @throws SQLException
     */
    public void onCustomersTableAddressCommit(TableColumn.CellEditEvent cellEditEvent) throws SQLException {
        selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        String newAddress = cellEditEvent.getNewValue().toString();
        if(!newAddress.isBlank()) {
            selectedCustomer.setAddress(newAddress);
            CustomerDAO.update(selectedCustomer.getCustomerID(), selectedCustomer);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This field cannot be empty.");
            alert.showAndWait();
        }
    }

    /**
     * Update a customer postal code in the table
     * @param cellEditEvent Double click on the cell
     * @throws SQLException
     */
    public void onCustomersTablePostalCommit(TableColumn.CellEditEvent cellEditEvent) throws SQLException {
        selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        String newPostal = cellEditEvent.getNewValue().toString();
        if(!newPostal.isBlank()) {
            selectedCustomer.setPostalCode(newPostal);
            CustomerDAO.update(selectedCustomer.getCustomerID(), selectedCustomer);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This field cannot be empty.");
            alert.showAndWait();
        }
    }

    /**
     * Update a customer phone number in the table
     * @param cellEditEvent Double click on the cell
     * @throws SQLException
     */
    public void onCustomersTablePhoneCommit(TableColumn.CellEditEvent cellEditEvent) throws SQLException {
        selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        String newPhone = cellEditEvent.getNewValue().toString();
        if(!newPhone.isBlank()) {
            selectedCustomer.setPhone(newPhone);
            CustomerDAO.update(selectedCustomer.getCustomerID(), selectedCustomer);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This field cannot be empty.");
            alert.showAndWait();
        }
    }

    /**
     * Update a customer division in the table
     * @param cellEditEvent Select from the Combo Box
     * @throws SQLException
     */
    public void onCustomersTableDivisionCommit(TableColumn.CellEditEvent cellEditEvent) throws SQLException {
        selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
        FirstLevelDivision div = (FirstLevelDivision) cellEditEvent.getNewValue();

        selectedCustomer.setDivisionID(div.getDivisionID());
        CustomerDAO.update(selectedCustomer.getCustomerID(), selectedCustomer);
    }


}
