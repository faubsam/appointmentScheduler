package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    /**
     * Get all customers in the customers table
     * @return ObservableList of customer objects
     */
    public static ObservableList<Customer> getAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM customers";
            PreparedStatement ps = Query.getQuery(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division = rs.getInt("Division_ID");
                Customer c = new Customer(customerID, name, address, postalCode, phone, division);
                customers.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * Get a customer by ID
     * @param id customer ID
     * @return Customer object with customer_id = id
     */
    public static Customer get(int id) {
        Customer c = null;
        try {
            String query = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division = rs.getInt("Division_ID");
                c = new Customer(customerID, name, address, postalCode, phone, division);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Delete a customer from the customer's table
     * @param id customer ID to delete
     * @return int number of rows affected by the query
     * @throws SQLException
     */
    public static int delete(int id) throws SQLException {
        String query = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = Query.getQuery(query);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Insert a new customer
     * @param customer customer object to insert in the customers table
     * @return int number of rows affected by the query
     * @throws SQLException
     */
    public static int insert(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(NULL, ?, ?, ?, ?, ?)";
        PreparedStatement ps = Query.getQuery(query);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        /*ps.setObject(5, LocalDateTime.now());
        ps.setString(6, "user");
        ps.setObject(7, LocalDateTime.now());
        ps.setString(8, "user");*/
        ps.setInt(5, customer.getDivisionID());


        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /**
     * Update an existing customer
     * @param id ID of the customer to update
     * @param c the customer object to update
     * @return int number of rows affected by the query
     * @throws SQLException
     */
    public static int update(int id, Customer c) throws SQLException {

        String query = "UPDATE customers SET Customer_name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = Query.getQuery(query);
        ps.setString(1, c.getName());
        ps.setString(2, c.getAddress());
        ps.setString(3, c.getPostalCode());
        ps.setString(4, c.getPhone());
        ps.setInt(5, c.getDivisionID());
        ps.setInt(6, c.getCustomerID());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}


