package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import utils.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {

    /**
     * Get all divisions in the divisions table
     * @return ObservableList of FirstLevelDivision objects
     */
    public static ObservableList<FirstLevelDivision> getAll() {

        ObservableList<FirstLevelDivision> divs = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = Query.getQuery(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivision div = new FirstLevelDivision(divisionID, name, countryID);
                divs.add(div);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return divs;
    }

    /**
     * Get a division by id
     * @param id
     * @return FirstLevelDivision object
     */
    public static FirstLevelDivision get(int id) {
        FirstLevelDivision div = null;
        try {
            String query = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divID = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int cID = rs.getInt("Country_ID");
                div = new FirstLevelDivision(divID, divName, cID);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return div;
    }

    /**
     * Get all divisions for a country
     * @param id country ID
     * @return ObservableList of FirstLevelDivision objects
     */
    public static ObservableList<FirstLevelDivision> getDivisionsForCountry(int id) {
        ObservableList<FirstLevelDivision> divs = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivision div = new FirstLevelDivision(divisionID, name, countryID);
                divs.add(div);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return divs;

    }



}
