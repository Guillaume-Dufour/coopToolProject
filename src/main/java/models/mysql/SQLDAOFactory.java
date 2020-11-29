package models.mysql;

import models.AbstractDAOFactory;
import models.UserDAO;
import models.mysql.daos.SQLUserDAO;

public class SQLDAOFactory extends AbstractDAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new SQLUserDAO();
    }
}
