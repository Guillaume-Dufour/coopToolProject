package cooptool.models.mysql;

import java.sql.Connection;

/**
 * Singleton
 * Create the connection with the database
 */
public class MySQLConnection {

    private static Connection connection = null;

    /**
     *
     * @return Connection with the database
     */
    public static Connection getInstance() {

        // TO DO

        return connection;
    }
}
