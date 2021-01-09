package cooptool.business.facades;

import cooptool.exceptions.DepartmentNotConformed;
import cooptool.models.daos.persistent.DepartmentDAO;
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

    public boolean create(String name, String abbreviation, int year) throws DepartmentNotConformed {

        if (name.length() <= 100 && abbreviation.length() <= 10 && (year >= 1 && year <= 5)) {
            return departmentDAO.create(new Department(name, year, abbreviation));
        }
        else {
            throw new DepartmentNotConformed();
        }
    }

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

    public void updateAvailability(Department department) {
        departmentDAO.update(department);
    }

    public List<Department> getAvailableDepartments() {
        return availablesDepartments;
    }

    public List<Department> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }
}
