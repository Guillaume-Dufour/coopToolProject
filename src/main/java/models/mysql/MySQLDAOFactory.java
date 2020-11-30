package models.mysql;

import models.AbstractDAOFactory;
import models.UserDAO;
import models.mysql.daos.MySQLUserDAO;

public class MySQLDAOFactory extends AbstractDAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }
}
