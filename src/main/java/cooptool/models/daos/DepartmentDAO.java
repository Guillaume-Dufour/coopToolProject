package cooptool.models.daos;

import cooptool.models.objects.Department;

import java.util.List;

public abstract class DepartmentDAO {

    private static final DepartmentDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getDepartmentDAO();
    }

    public static DepartmentDAO getInstance() {
        return INSTANCE;
    }

    public abstract boolean create(Department department);

    public abstract boolean update(Department department);

    public abstract List<Department> getAllDepartments();

    public abstract List<Department> getAvailableDepartments();
}
