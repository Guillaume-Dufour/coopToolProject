package cooptool.business.facades;

import cooptool.exceptions.DepartmentNotConformed;
import cooptool.models.daos.persistent.DepartmentDAO;
import cooptool.models.objects.Department;

import java.util.List;

/**
 * DepartmentFacade class
 */
public class DepartmentFacade {

    /**
     * Instance of the DepartmentFacade
     */
    private static final DepartmentFacade INSTANCE;

    /**
     * Attribute to access to the departmentDAO
     */
    private final DepartmentDAO departmentDAO = DepartmentDAO.getInstance();

    /**
     * Attribute to access to the available departments list
     */
    private final List<Department> availablesDepartments = departmentDAO.getAvailableDepartments();

    static {
        INSTANCE = new DepartmentFacade();
    }

    private DepartmentFacade() {}

    /**
     * Get the DepartmentFacade instance
     * @return DepartmentFacade instance
     */
    public static DepartmentFacade getInstance() {
        return INSTANCE;
    }

    /**
     * Create a new department with the parameters
     * @param name Name of the department
     * @param abbreviation Abbreviation of the department
     * @param year Year of the department
     * @return True if the creation is successful, false otherwise
     * @throws DepartmentNotConformed If the parameters are not conformed to create a new department
     */
    public boolean create(String name, String abbreviation, int year) throws DepartmentNotConformed {

        if (name.length() <= 100 && abbreviation.length() <= 10 && (year >= 1 && year <= 5)) {
            return departmentDAO.create(new Department(name, year, abbreviation));
        }
        else {
            throw new DepartmentNotConformed();
        }
    }

    /**
     * Update the department in parameter with the parameters
     * @param department Department to update
     * @param name New name of the department
     * @param abbreviation New abbreviation of the department
     * @param year New year of the department
     * @return True if the update is successful, false otherwise
     * @throws DepartmentNotConformed If the parameters are not conformed to update the department
     */
    public boolean update(Department department, String name, String abbreviation, int year) throws DepartmentNotConformed {

        if (name.length() <= 100 && abbreviation.length() <= 10 && (year >= 1 && year <= 5)) {

            department.setSpeciality(name);
            department.setAbbreviation(abbreviation);
            department.setYear(year);

            return departmentDAO.update(department);
        }
        else {
            throw new DepartmentNotConformed();
        }
    }

    /**
     * Update the availability of the department in parameter
     * @param department Department we want to update the availability
     */
    public void updateAvailability(Department department) {
        departmentDAO.update(department);
    }

    /**
     * Get the list of all available departments
     * @return List of all available departments
     */
    public List<Department> getAvailableDepartments() {
        return availablesDepartments;
    }

    /**
     * Get the list of all departments
     * @return List of all departments
     */
    public List<Department> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }
}
