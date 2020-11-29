package models;

public abstract class AbstractDAOFactory {

    public static AbstractDAOFactory getFactory(FactoryType type) {
        return null;
    }

    public abstract UserDAO getUserDAO();

}
