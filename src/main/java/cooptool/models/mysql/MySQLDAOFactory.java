package cooptool.models.mysql;

import cooptool.models.AbstractDAOFactory;
import cooptool.models.UserDAO;
import cooptool.models.mysql.daos.MySQLUserDAO;

public class MySQLDAOFactory extends AbstractDAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }
}
