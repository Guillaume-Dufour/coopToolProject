package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Department;

import java.util.List;

/**
 * DepartmentDAO class
 */
public abstract class DepartmentDAO {

    /**
     * Instance of the DepartmentDAO
     */
    private static final DepartmentDAO INSTANCE = AbstractDAOFactory.getInstance().getDepartmentDAO();

    protected DepartmentDAO() {}

    /**
     * Get the DepartmentDAO instance
     * @return DepartmentDAO instance
     */
    public static DepartmentDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Inserts a new department in the database
     * @param department Department object to insert
     * @return True if the insertion is successful, false otherwise
     */
    public abstract boolean create(Department department);

    /**
     * Update the department in parameter in the database
     * @param department Department object to update
     * @return True if the update is successful, false otherwise
     */
    public abstract boolean update(Department department);

    /**
     * Return all departments objects
     * @return List of all departments of the database
     */
    public abstract List<Department> getAllDepartments();

    /**
     * Return all available departments objects
     * @return List of all available departments of the database
     */
    public abstract List<Department> getAvailableDepartments();
}
