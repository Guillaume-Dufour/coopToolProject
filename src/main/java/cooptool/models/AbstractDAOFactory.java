package cooptool.models;

import cooptool.models.mysql.MySQLDAOFactory;

public abstract class AbstractDAOFactory {

    public static AbstractDAOFactory getFactory(FactoryType type) {
        return new MySQLDAOFactory();
    }

    public abstract UserDAO getUserDAO();

}
