package controller;

import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public Button loginButton;
    public Label localTimeZoneText;
    public TextField userNameText;
    public PasswordField loginPasswordField;
    public Label loginPageTitle;
    public Label loginUsernameLabel;
    public Label loginPasswordLabel;
    public Label loginTimezoneLabel;
    /**
     * Timezone variable to display on the login page
     */
    private ZonedDateTime zoneID;
    /**
     * User object used for logging purposes
     */
    private static User userLoggingIn;
    /**
     * Resource bundle to enable the properties and language adjustments
     */
    private ResourceBundle rb;
    /**
     * AlertText variable can be change based on the user's language
     */
    private static String alertText;

    /**
     * Display the user's local time zone.
     * Call the resource bundle to get the correct properties file based on the user's system language
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneID = ZonedDateTime.now(ZoneId.systemDefault());
        String timezone = zoneID.getZone().toString();
        localTimeZoneText.setText(timezone);


        rb = ResourceBundle.getBundle("resources/LoginPage", Locale.getDefault());
        loginPasswordLabel.setText(rb.getString("loginPasswordLabel"));
        loginButton.setText(rb.getString("loginButton"));
        loginTimezoneLabel.setText(rb.getString("loginTimezoneLabel"));
        loginUsernameLabel.setText(rb.getString("loginUsernameLabel"));
        loginPageTitle.setText(rb.getString("loginPageTitle"));
        alertText = rb.getString("alertText");


    }

    /**
     * Verify the user's credentials against the database.
     * Log all login attempts to a file - login_activity.txt
     * @param actionEvent Click on the login button
     * @throws IOException
     * @throws SQLException
     */
    public void onLoginButton(ActionEvent actionEvent) throws IOException, SQLException {
        String username = userNameText.getText();
        String password = loginPasswordField.getText();
        int status = 1;
        try {

            userLoggingIn = UserDAO.get(username);

            if (userLoggingIn.getUsername().equals(username) && userLoggingIn.getPassword().equals(password)) {
                status = 0;
                User.logUserAttempt(username, status);
                Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1074, 795);
                stage.setTitle("Home Page");
                stage.setScene(scene);
                stage.show();
                User.checkForAppointmentin15Minutes(userLoggingIn.getUserID());
            } else {

                User.logUserAttempt(username, status);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(alertText);
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            User.logUserAttempt(username, status);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(alertText);
            alert.showAndWait();
        }
    }

    /**
     * Returns the user object for use in the logging class
     * @return User object
     */
    public static User getUserLoggingIn(){
        return userLoggingIn;
    }

}
