package cooptool.models.daos;

import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySQLSubjectDAO extends SubjectDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLSubjectDAO() {
        super();
    }

    @Override
    public boolean create(Subject subject) {
        return false;
    }

    @Override
    public boolean update(Subject subject) {

        String requete = "UPDATE subject " +
                "SET name_subject = ?, " +
                "available = ? ," +
                "id_department = ? " +
                "WHERE id_subject = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);

            preparedStatement.setString(1, subject.getName());
            preparedStatement.setInt(2, subject.getAvailable());
            preparedStatement.setInt(3, subject.getDepartment().getId());
            preparedStatement.setInt(4, subject.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Subject> getSubjectsByDepartment(Department department) {

        List<Subject> subjects = new ArrayList<>();

        String requete = "SELECT * FROM subject WHERE id_department = ? ORDER BY name_subject";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, department.getId());

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {

                Subject subject = new Subject(
                        result.getInt("id_subject"),
                        result.getString("name_subject"),
                        result.getInt("available"),
                        department
                );

                subjects.add(subject);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return subjects;
    }

    @Override
    public List<Subject> getAvailableSubjectsByDepartment(Department department) {
        return getSubjectsByDepartment(department).stream().filter(subject -> subject.getAvailable() == 1).collect(Collectors.toList());
    }
}
