package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Gets list of all users in the users table
     * @return ObservableList of user objects
     * @throws SQLException
     */
    public static ObservableList<User> getAll() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String query = "SELECT * FROM users";
        PreparedStatement ps = Query.getQuery(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int userID = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");

            User user = new User(userID, name, password);
            allUsers.add(user);
        }

        return allUsers;
    }

    /**
     * Get a user by username
     * @param username string
     * @return returns a user object
     * @throws SQLException
     */
    public static User get(String username) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement ps = Query.getQuery(query);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");

            user = new User(userID, name, password);
        }
        return user;
    }

    /**
     * Gets the user object with the user ID requested
     * @param id user to get
     * @return User object with user_ID = id
     * @throws SQLException
     */
    public static User get(int id) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement ps = Query.getQuery(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");

            user = new User(userID, name, password);
        }
        return user;
    }


}
