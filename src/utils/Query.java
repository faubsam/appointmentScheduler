package utils;

import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query {

    /**
     * Return a prepared statement to make queries to the database
     * @param query String query
     * @return Prepared statement
     * @throws SQLException
     */
    public static PreparedStatement getQuery(String query) throws SQLException {
        PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
        return ps;
    }
}
