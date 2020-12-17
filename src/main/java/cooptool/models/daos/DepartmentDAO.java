package cooptool.models.daos;

import cooptool.models.objects.Department;
import cooptool.models.objects.User;

public abstract class DepartmentDAO {

    private static DepartmentDAO INSTANCE = null;

    public static DepartmentDAO getInstance() {
        if(INSTANCE == null) {
            AbstractDAOFactory f = AbstractDAOFactory.getInstance();
            INSTANCE = f.getDepartmentDAO();
        }
        return INSTANCE;
    }

    public abstract Department find(int id);
    public abstract boolean update(Department department);
    public abstract boolean delete(Department department);
    public abstract boolean create(Department department);
}
