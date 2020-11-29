package models;

import models.mysql.SQLDAOFactory;

public abstract class AbstractDAOFactory {

    public static AbstractDAOFactory getFactory(FactoryType type) {
        return new SQLDAOFactory();
    }

    public abstract UserDAO getUserDAO();

}
