package cooptool.models.daos;

import java.sql.Connection;

public class MySQLDepartmentDAO extends DepartmentDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLDepartmentDAO() {
        super();
    }
}
