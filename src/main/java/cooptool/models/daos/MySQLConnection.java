package cooptool.models.daos;

import cooptool.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton
 * Create the connection with the database
 */
public class MySQLConnection {

    private static Connection connection = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Config.JDBC_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private MySQLConnection() {

    }

    /**
     * @return Connection with the database
     */
    public static Connection getInstance() {
        return connection;
    }
}
