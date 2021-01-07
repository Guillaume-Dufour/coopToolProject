package cooptool.models.daos;

import cooptool.utils.PropertiesResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton
 * Create the connection with the database
 */
public class MySQLConnection {

    private static class LazyHolder {
        static Connection INSTANCE = null;

        static {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                INSTANCE = DriverManager.getConnection(PropertiesResource.getDatabaseProperties().getProperty("JDBC_URL"));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            finally{
                try {
                    INSTANCE.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MySQLConnection() {

    }

    /**
     * @return Connection with the database
     */
    public static Connection getInstance() {
        return LazyHolder.INSTANCE;
    }
}
