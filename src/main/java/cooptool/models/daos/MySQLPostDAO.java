package cooptool.models.daos;

import java.sql.Connection;

public class MySQLPostDAO extends PostDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLPostDAO() {
        super();
    }




}
