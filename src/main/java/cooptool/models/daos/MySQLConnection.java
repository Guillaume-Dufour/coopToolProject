package cooptool.models.daos;

import cooptool.utils.PropertiesResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQLConnection singleton <br>
 * Connection with the database
 */
public class MySQLConnection {

    private static class LazyHolder {
        static Connection INSTANCE = null;

        static {
            try {
                INSTANCE = DriverManager.getConnection(PropertiesResource.getDatabaseProperties().getProperty("JDBC_URL"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private MySQLConnection() {

    }

    /**
     * Get the connection of the MySQLConnection
     * @return The connection to the database
     */
    public static Connection getInstance() {
        return LazyHolder.INSTANCE;
    }
}
