package cooptool.models.daos;

import java.sql.Connection;

public class MySQLMentoringDemandDAO extends MentoringDemandDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLMentoringDemandDAO() {
        super();
    }
}
