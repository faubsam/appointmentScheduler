package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO{

    /**
     * Gets list of all countries in the countries table
     * @return ObservableList of Country objects
     */
    public static ObservableList<Country> getAll() {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM countries";
            PreparedStatement ps = Query.getQuery(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Country c = new Country(countryID, country);
                countries.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return countries;
    }

    /**
     * Get a country by ID
     * @param id country ID
     * @return country object
     */
    public static Country get(int id) {
        Country c = null;
        try {
            String query = "SELECT * from countries WHERE Country_ID = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int cid = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                c = new Country(cid, name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }return c;
    }

    /**
     * Get the ID of a country by it's name
     * @param name country name
     * @return int country ID
     */
    public int getCountryID(String name) {
        int id = 0;
        try {
            String query = "SELECT Country_ID from countries WHERE Country = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                id = rs.getInt("Country_ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Get the country that a division belongs to
     * @param id division id
     * @return country ID
     */
    public static int getCountryFromDivision(int id) {
        int cid = 0;
        try {
            String query = "select countries.Country_ID from countries JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID WHERE first_level_divisions.Division_ID = ?";
            PreparedStatement ps = Query.getQuery(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                cid = rs.getInt("Country_ID");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cid;
    }

}
