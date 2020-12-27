package cooptool.models.daos;

import java.sql.Connection;

public class MySQLQuickHelpPostDAO extends QuickHelpPostDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLQuickHelpPostDAO() {
        super();
    }



    
}
