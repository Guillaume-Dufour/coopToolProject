package cooptool.models.daos;

import cooptool.models.objects.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySQLDepartmentDAO extends DepartmentDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLDepartmentDAO() {
        super();
    }

    @Override
    public List<Department> getAllDepartments() {

        List<Department> departments = new ArrayList<>();

        String requete = "SELECT * FROM department ORDER BY abbreviation_department, year_department";

        try {
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(requete);

            while (result.next()) {

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

    @Override
    public List<Department> getAvailableDepartments() {
        return getAllDepartments().stream().filter(department -> department.getAvailable() == 1).collect(Collectors.toList());
    }

    @Override
    public boolean create(Department department) {

        String requete = "INSERT INTO department (name_department, abbreviation_department, year_department) " +
                "VALUES (?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);

            preparedStatement.setString(1, department.getSpeciality());
            preparedStatement.setString(2, department.getAbbreviation());
            preparedStatement.setInt(3, department.getYear());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Department department) {

        String requete = "UPDATE department " +
                "SET name_department = ?, " +
                "abbreviation_department = ?, " +
                "year_department = ?, " +
                "available = ? " +
                "WHERE id_department = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);

            preparedStatement.setString(1, department.getSpeciality());
            preparedStatement.setString(2, department.getAbbreviation());
            preparedStatement.setInt(3, department.getYear());
            preparedStatement.setInt(4, department.getAvailable());
            preparedStatement.setInt(5, department.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }
}
