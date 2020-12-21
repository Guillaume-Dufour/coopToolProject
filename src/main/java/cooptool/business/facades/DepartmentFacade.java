package cooptool.business.facades;

import cooptool.models.daos.DepartmentDAO;
import cooptool.models.objects.Department;

import java.util.List;

public class DepartmentFacade {

    private static final DepartmentFacade INSTANCE;
    private final DepartmentDAO departmentDAO = DepartmentDAO.getInstance();
    private final List<Department> availablesDepartments = departmentDAO.getAvailableDepartments();

    static {
        INSTANCE = new DepartmentFacade();
    }

    private DepartmentFacade() {}

    public static DepartmentFacade getInstance() {
        return INSTANCE;
    }

    public Department getDepartment(){
        return null;
    }

    public List<Department> getAvailableDepartments() {
        return availablesDepartments;
    }

    public List<Department> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }
}
