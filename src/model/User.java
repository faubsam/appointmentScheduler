package model;

import DAO.AppointmentsDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.TimeConversions;

import java.io.*;
import java.time.LocalDateTime;

public class User {
    /**
     * User ID
     */
    private int userID;
    /**
     * Username
     */
    private String username;
    /**
     * User password
     */
    private String password;

    /**
     * Constructor for user object
     * @param id
     * @param username
     * @param password
     */
    public User(int id, String username, String password) {
        this.userID = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Getter - user id
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter - user id
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter - username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter - password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter - password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks for appointment in the next 15 minutes for this user
     * Displays an alert saying if the check if true or not
     * @param userID
     * @return boolean true or false
     */
    public static boolean checkForAppointmentin15Minutes(int userID) {
        boolean result = false;
        ObservableList<Appointment> appts = AppointmentsDAO.getAll();
        for (Appointment appt: appts) {
            if (appt.getUserID() == userID
                    && appt.getStart().isBefore(LocalDateTime.now().plusMinutes(15))
                    && appt.getStart().isAfter(LocalDateTime.now().minusMinutes(1))) {
                result = true;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("You have an appointment within the next 15 minutes!\n" +
                        "Appointment ID: " + appt.getAppointmentID() + "\n" +
                        "Appointment Date: " + appt.getStart().toLocalDate() + "\n" +
                        "Appointment Time: " + appt.getStart().toLocalTime());
                alert.showAndWait();
                return result;
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("You have no appointments within the next 15 minutes.");
        alert.showAndWait();
        return result;
    }

    /**
     * Logs all user login attempts
     * @param name username
     * @param status  success or failure
     * @throws FileNotFoundException
     */
    public static void logUserAttempt(String name, int status) throws FileNotFoundException {
        try {
            File logFile = new File("login_activity.txt");
            if (!logFile.isFile()) {
                logFile.createNewFile();
            }
            FileWriter writer = new FileWriter(logFile, true);
            String log;
            if (status == 0) {
                log = TimeConversions.toUTCTime(LocalDateTime.now())
                        + "     Login attempt by user: " + name
                        + "     Result: Authentication successful\n";
            } else {
                log = TimeConversions.toUTCTime(LocalDateTime.now())
                        + "     Login attempt by user: " + name
                        + "     Result: Authentication failed\n";
            }
            writer.write(log);
            writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     * Method toString for display in ComboBox
      * @return String object with user ID and username
     */
    public String toString() {
        return this.userID + ": " + this.username;
    }

}
