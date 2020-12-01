package cooptool.models.mysql.daos;

import cooptool.models.UserDAO;
import cooptool.models.mysql.MySQLConnection;
import cooptool.models.objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUserDAO extends UserDAO {

    Connection connection = MySQLConnection.getInstance();
    private static final int ADMIN_ROLE = 0;
    private static final int STUDENT_ROLE = 1;

    @Override
    public User findUserByMail(String mail) {

        User user = null;
        String statement =
                "SELECT * " +
                "FROM user u " +
                "JOIN department d ON d.id_department = u.id_department " +
                "WHERE u.mail_user = ?";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(statement);
            preparedStatement.setString(1, mail);

            ResultSet result = preparedStatement.executeQuery();

            if(result.next()) {
                int id = result.getInt("id_user");
                int typeUser = result.getInt("type_user");
                String password = result.getString("password_user");
                UserRole userRole = null;
                if(typeUser == STUDENT_ROLE){
                    String firstName = result.getString("first_name_user");
                    String lastName = result.getString("last_name_user");
                    String description = result.getString("description_user");
                    int departmentId = result.getInt("id_department");
                    String nameDepartment = result.getString("name_department");
                    int year = result.getInt("year_department");
                    String abbreviation = result.getString("abbreviation_department");
                    int available = result.getInt("available");
                    Department department = new Department(
                          departmentId,nameDepartment,year,abbreviation,available
                    );
                    userRole = new StudentRole(
                            firstName,lastName,description,department
                    );
                }
                else if(typeUser == ADMIN_ROLE){
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
    public void create(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
