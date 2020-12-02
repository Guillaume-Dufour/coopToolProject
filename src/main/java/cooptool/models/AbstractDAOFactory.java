package cooptool.models;

import cooptool.models.mysql.MySQLDAOFactory;

public abstract class AbstractDAOFactory {

    public static AbstractDAOFactory getFactory(FactoryType type) {

        if (type == FactoryType.MySQL_Factory) {
            return new MySQLDAOFactory();
        }

        return null;

    }

    public abstract UserDAO getUserDAO();

}
