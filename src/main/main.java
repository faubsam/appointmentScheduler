package main;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;
import utils.JDBC;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class main extends Application {

    public static void main(String[] args) throws SQLException {
       JDBC.openConnection();

       launch(args);


       JDBC.closeConnection();

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));

        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }
}
