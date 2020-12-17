package cooptool.models.daos;

public abstract class AbstractDAOFactory {

    private static AbstractDAOFactory instance = null;

    public static AbstractDAOFactory getInstance() {
            if (instance == null) {
                instance = new MySQLDAOFactory();
            }

            return instance;
    }

    public abstract UserDAO getUserDAO();
    public abstract DepartmentDAO getDepartmentDAO();

}
