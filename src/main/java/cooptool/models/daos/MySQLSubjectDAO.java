package cooptool.models.daos;

import cooptool.models.daos.persistent.SubjectDAO;
import cooptool.models.enumDatabase.SubjectTable;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MySQLSubjectDAO class
 */
public class MySQLSubjectDAO extends SubjectDAO {

    private final Connection connection = MySQLConnection.getInstance();

    protected MySQLSubjectDAO() {
        super();
    }

    @Override
    public boolean create(Subject subject) {

        String query = "INSERT INTO subject (%s, %s) " +
                "VALUES (?, ?);";

        query = String.format(query,
                SubjectTable.NAME_SUBJECT,
                SubjectTable.ID_DEPARTMENT
        );

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, subject.getName());
            preparedStatement.setInt(2, subject.getDepartment().getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Subject subject) {

        String query = "UPDATE subject " +
                "SET name_subject = ?, " +
                "available = ? ," +
                "id_department = ? " +
                "WHERE id_subject = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

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

        String requete = "SELECT * " +
                "FROM subject s " +
                "JOIN department d ON d.id_department = s.id_department AND d.id_department = ? " +
                "ORDER BY s.name_subject";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, department.getId());

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Subject subject = MySQLFactoryObject.createSubject(result);
                subjects.add(subject);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return subjects;
    }

    @Override
    public List<Subject> getSubjectsByPromotion(String abbreviation) {
        List<Subject> subjects = new ArrayList<>();

        String query =
                "SELECT * " +
                "FROM subject s " +
                "JOIN department d ON d.id_department = s.id_department AND d.abbreviation_department = ? " +
                "ORDER BY s.name_subject";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, abbreviation);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Subject subject = MySQLFactoryObject.createSubject(result);
                subjects.add(subject);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    @Override
    public List<Subject> getAllSubjects() {

        List<Subject> subjects = new ArrayList<>();

        String query = "SELECT * " +
                "FROM subject s " +
                "JOIN department d on d.id_department = s.id_department";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Subject subject = MySQLFactoryObject.createSubject(result);
                subjects.add(subject);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    @Override
    public List<Subject> getAvailableSubjectsByDepartment(Department department) {
        return getSubjectsByDepartment(department).stream().filter(subject -> subject.getAvailable() == 1).collect(Collectors.toList());
    }
}
