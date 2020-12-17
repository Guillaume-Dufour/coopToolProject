package cooptool.models.daos;

import cooptool.models.objects.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDepartmentDAO extends DepartmentDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLDepartmentDAO() {
        super();
    }

    @Override
    public List<Department> getAllDepartments() {

        List<Department> departments = new ArrayList<>();

        String requete = "SELECT * FROM department WHERE available = 1";

        try {
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(requete);

            if (result.next()) {

                Department department = new Department(
                        result.getInt("id_department"),
                        result.getString("name_department"),
                        result.getInt("year_department"),
                        result.getString("abbreviation_department"),
                        result.getInt("available")
                );

                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }
}
