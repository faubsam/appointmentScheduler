package model;

import DAO.AppointmentsDAO;
import javafx.collections.ObservableList;
import utils.TimeConversions;

import java.time.LocalDateTime;

public class Appointment {
    /**
     * AppointmentId: int
     */
    private int appointmentID;
    /**
     * Title: string
     */
    private String title;
    /**
     * Description: string
     */
    private String description;
    /**
     * Location: string
     */
    private String location;
    /**
     * Type: string
     */
    private String type;
    /**
     * LocalDateTime: start
     */
    private LocalDateTime start;
    /**
     * LocalDateTime end
     */
    private LocalDateTime end;
    /**
     * CustomerId: int
     */
    private int customerID;
    /**
     * UserId: int
     */
    private int userID;
    /**
     * ContactId: int
     */
    private int contactID;

    /**
     * Setter - id
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter - title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter - title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter - description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter - description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter - location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter - location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter - type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter - type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter - start time
     *
     * @return LocalDateTime object Start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Set start time
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Getter - end time
     * @return LocalDateTime object End
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Set end time
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Getter - customer id
     * @return customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter - customer id
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter - user ID
     * @return user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter - user ID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter - contact ID
     * @return contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter - contact ID
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter - appointment ID
     * @return appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Constructor
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Method to check whether a new or modified appointment overlaps with a current appointment for a customer
     * @param start start time LocalDateTime
     * @param end end time LocalDateTime
     * @param id customer ID
     * @return int appointmentID or 0 if not found
     */
    public static int checkForOverlappingAppointment(LocalDateTime start, LocalDateTime end, int id) {
        int apptID = 0;
        ObservableList<Appointment> customerAppointments = AppointmentsDAO.getAllAppointmentsForCustomer(id);

        for(Appointment appt: customerAppointments) {
            LocalDateTime apptStart = appt.getStart();
            LocalDateTime apptEnd = appt.getEnd();

                // && end.isBefore(apptEnd)
            if (start.isBefore(apptStart) && end.isAfter(apptStart)) {
                apptID = appt.getAppointmentID();
                break;
    // start.isAfter(apptStart) && end.isAfter(apptEnd) && start.isBefore(apptEnd)
            } else if (end.isAfter(apptEnd) && start.isBefore(apptEnd)) {
                apptID = appt.getAppointmentID();
                break;

            } else if (start.isBefore(apptStart) && end.isAfter(apptEnd)) {
                apptID = appt.getAppointmentID();
                break;

            } else if (start.isEqual(apptStart) || end.isEqual(apptEnd)) {
                apptID = appt.getAppointmentID();
                break;

            } else if (start.isAfter(apptStart) && end.isBefore(apptEnd)) {
                apptID = appt.getAppointmentID();
                break;
            }
        }

        return apptID;
    }


}
