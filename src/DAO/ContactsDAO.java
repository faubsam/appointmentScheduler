package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utils.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDAO{

    /**
     * Get all contacts in the contacts table
     * @return ObservableList of contact Objects
     */
    public static ObservableList<Contact> getAll() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM contacts";
            PreparedStatement ps = Query.getQuery(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(contactID, name, email);
                contacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * Get a contact by ID
     * @param id contact ID
     * @return contact object
     */
    public static Contact get(int id) {
        Contact c = null;
        try {
            String query = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                c = new Contact(contactID, name, email);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

}
