package cooptool.models.daos;

public abstract class DepartmentDAO {

    private static final DepartmentDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getDepartmentDAO();
    }
}
