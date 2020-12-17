package cooptool.models.daos;

import cooptool.models.objects.Department;

public class MySQLDepartmentDAO extends DepartmentDAO{

    protected MySQLDepartmentDAO() {
        super();
    }

    @Override
    public Department find(int id) {
        return null;
    }

    @Override
    public boolean update(Department department) {
        return false;
    }

    @Override
    public boolean delete(Department department) {
        return false;
    }

    @Override
    public boolean create(Department department) {
        return false;
    }
}
