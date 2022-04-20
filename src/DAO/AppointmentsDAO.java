package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.Appointment;
import model.CountryReport;
import model.CustomersReport;
import utils.Query;
import utils.TimeConversions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class AppointmentsDAO {

    /**
     * Get a list of all appointments in the appointments table
     * @return ObservableList of Appointment objects
     */
    public static ObservableList<Appointment> getAll() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM appointments";
            PreparedStatement ps = Query.getQuery(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");



                Appointment a = new Appointment(appointmentID, title, description, location, type, TimeConversions.toLocalSystemTime(start), TimeConversions.toLocalSystemTime(end), customerID, userID, contactID);
                appointments.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Get an appointment by ID
     * @param id appointment ID
     * @return appointment object
     */
    public Appointment get(int id) {
        Appointment a = null;
        try {
            String query = "SELECT * FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = Query.getQuery(query);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                a = new Appointment(appointmentID, title, description, location, type, TimeConversions.toLocalSystemTime(start), TimeConversions.toLocalSystemTime(end), customerID, userID, contactID);
            }
        } catch(SQLException e){
                e.printStackTrace();
        }
        return a;
    }

    /**
     * Get all appointments assigned to one customer
     * @param id customer ID
     * @return ObservableLits of appointment objects
     */
    public static ObservableList<Appointment> getAllAppointmentsForCustomer(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");



                Appointment a = new Appointment(appointmentID, title, description, location, type, TimeConversions.toLocalSystemTime(start), TimeConversions.toLocalSystemTime(end), customerID, userID, contactID);
                appointments.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Delete an appointment
     * @param id appointment to delete
     * @return int number of rows affected by the query
     * @throws SQLException
     */
    public static int delete(int id) throws SQLException {
            String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
    }

    /**
     * Insert a new appointment into the table
     * @param appt appointment object to insert
     * @return int number of rows affected by the query
     * @throws SQLException
     */
    public static int insert(Appointment appt) throws SQLException {
        String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = Query.getQuery(query);

        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appt.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appt.getEnd()));
        ps.setInt(7, appt.getCustomerID());
        ps.setInt(8, appt.getUserID());
        ps.setInt(9,appt.getContactID());

        int affectedRows = ps.executeUpdate();
        return affectedRows;
    }


    /**
     * Update an existing appointment
     * @param a appointment object to modify
     * @return int number of rows affected by the query
     * @throws SQLException
     */
    public static int update(Appointment a) throws SQLException {
        String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = Query.getQuery(query);

         // remove
        ps.setString(1, a.getTitle());
        ps.setString(2, a.getDescription());
        ps.setString(3, a.getLocation());
        ps.setString(4, a.getType());
        ps.setTimestamp(5, Timestamp.valueOf(a.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(a.getEnd()));
        ps.setInt(7, a.getCustomerID());
        ps.setInt(8, a.getUserID());
        ps.setInt(9, a.getContactID());
        ps.setInt(10, a.getAppointmentID());


        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Create a list of appointments by contact
     * @param id contact ID
     * @return ObservableList of appointment objects
     */
    public static ObservableList<Appointment> getContactsReport(int id){
        ObservableList<Appointment> contactsReportList = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointments WHERE Contact_ID = ?";
        try {
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentID, title, description, location, type, TimeConversions.toLocalSystemTime(start), TimeConversions.toLocalSystemTime(end), customerID, userID, contactID);
                contactsReportList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactsReportList;
    }

    /**
     * List of appointments grouped by type and month
     * @return list of customer report objects
     * @throws SQLException
     */
    public static ObservableList<CustomersReport> getAppointmentByTypeAndMonthReport() throws SQLException {
        String query = "SELECT appointments.type, MONTHNAME(start) as Month, count(*) as apptCount from appointments join customers on appointments.Customer_ID = customers.Customer_ID group by appointments.type order by appointments.start";
        ObservableList<CustomersReport> resultsList = FXCollections.observableArrayList();
        PreparedStatement ps = Query.getQuery(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String type = rs.getString("type");
            String month = rs.getString("Month");
            int countAppts = rs.getInt("apptCount");

            CustomersReport report = new CustomersReport(type, month, countAppts);
            resultsList.add(report);
        }
        return resultsList;
    }

    /**
     * Create a report of appointments by country
     * @return list of country report objects
     * @throws SQLException
     */
    public static ObservableList<CountryReport> getAppointmentsByCountryReport() throws SQLException {
        String query = "SELECT countries.country as country, count(*) as num from appointments join customers on appointments.Customer_ID = customers.Customer_ID join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID join countries on first_level_divisions.Country_ID = countries.Country_ID group by countries.country_id";

        PreparedStatement ps = Query.getQuery(query);
        ResultSet rs = ps.executeQuery();

        ObservableList<CountryReport> results = FXCollections.observableArrayList();
        while(rs.next()) {
            String country = rs.getString("country");
            int num = rs.getInt("num");
            CountryReport report = new CountryReport(country, num);
            results.add(report);
        }

        return results;
    }
}
