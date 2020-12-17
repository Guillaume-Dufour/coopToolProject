package cooptool.models.daos;

import cooptool.models.objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUserDAO extends UserDAO {

    Connection connection = MySQLConnection.getInstance();
    private static final int ADMIN_ROLE = 0;
    private static final int STUDENT_ROLE = 1;

    protected MySQLUserDAO() {
        super();
    }

    @Override
    public User findUserByMail(String mail) {

        User user = null;
        String statement =
                "SELECT * " +
                "FROM user u " +
                "LEFT JOIN department d ON d.id_department = u.id_department " +
                "WHERE u.mail_user = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, mail);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {

                int id = result.getInt("id_user");
                int typeUser = result.getInt("type_user");
                String password = result.getString("password_user");
                UserRole userRole = null;

                if (typeUser == STUDENT_ROLE) {

                    String firstName = result.getString("first_name_user");
                    String lastName = result.getString("last_name_user");
                    String description = result.getString("description_user");
                    int departmentId = result.getInt("id_department");
                    String nameDepartment = result.getString("name_department");
                    int year = result.getInt("year_department");
                    String abbreviation = result.getString("abbreviation_department");
                    int available = result.getInt("available");

                    Department department = new Department(
                          departmentId, nameDepartment, year, abbreviation, available
                    );
                    userRole = new StudentRole(
                            firstName, lastName, description, department
                    );
                }
                else if(typeUser == ADMIN_ROLE) {
                    userRole = new AdminRole();
                }
                user = new User(id, mail, password, userRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public boolean create(User user) {
        String statement =
                "INSERT INTO user (last_name_user, first_name_user, mail_user, password_user, type_user, id_department) " +
                        "VALUES (?,?,?,?,?,?);";
        PreparedStatement preparedStatement = null;
        try {

            System.out.println(user);
            preparedStatement = connection.prepareStatement(statement);
            System.out.println("connection ok");

            preparedStatement.setString(1,((StudentRole)user.getRole()).getLastName());
            preparedStatement.setString(2,(((StudentRole) user.getRole()).getFirstName()));
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, STUDENT_ROLE);
            //preparedStatement.setInt(6, ((StudentRole) user.getRole()).getDepartment().getId());
            preparedStatement.setInt(6, 1);

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

            System.out.println("apres");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }
}
