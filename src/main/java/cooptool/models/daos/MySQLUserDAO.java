package cooptool.models.daos;

import cooptool.models.daos.persistent.UserDAO;
import cooptool.models.objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQLUserDAO class
 */
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
                user = MySQLFactoryObject.createUser(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findUserByDepartment(Department department){
        ArrayList<User> listUser = new ArrayList<>();
        User user = null;
        String statement =
                "SELECT * " +
                        "FROM user u " +
                        "JOIN department d ON d.id_department = u.id_department " +
                        "WHERE d.id_department = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, department.getId());

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                user = MySQLFactoryObject.createUser(result);
                listUser.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUser;
    }

    @Override
    public boolean create(User user) {
        String statement =
                "INSERT INTO user (last_name_user, first_name_user, mail_user, password_user, type_user, id_department) " +
                        "VALUES (?,?,?,?,?,?);";
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement(statement);

            preparedStatement.setString(1, ((StudentRole) user.getRole()).getLastName());
            preparedStatement.setString(2, (((StudentRole) user.getRole()).getFirstName()));
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, STUDENT_ROLE);
            preparedStatement.setInt(6, ((StudentRole) user.getRole()).getDepartment().getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        String statement =
                "UPDATE `user` " +
                        "SET `last_name_user`=?,`first_name_user`=?,`description_user`=?,`id_department`=? " +
                        "WHERE id_user=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, ((StudentRole) user.getRole()).getLastName());
            preparedStatement.setString(2, ((StudentRole) user.getRole()).getFirstName());
            preparedStatement.setString(3, ((StudentRole) user.getRole()).getDescription());
            preparedStatement.setInt(4, ((StudentRole) user.getRole()).getDepartment().getId());
            preparedStatement.setInt(5, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updatePassword(User user) {
        String statement =
                "UPDATE `user` " +
                        "SET password_user = ?" +
                        "WHERE id_user = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateValidation(int id){
        String statement =
                "UPDATE user " +
                        "SET validate = 1 " +
                        "WHERE id_user = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(User user) {
        String statement =
                "DELETE FROM `user` WHERE `user`.`id_user` = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean createValidationCode(int userId, int validationCode) {
        String statement =
                "INSERT INTO validation_user (id_user, code_validation) " +
                        "VALUES (?,?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2, validationCode);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public int getCodeByUser(int id){
        int res = -1;
        String statement =
                "SELECT code_validation " +
                        "FROM validation_user v, user u " +
                        "WHERE v.id_user = u.id_user " +
                        "AND v.id_user = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                res = result.getInt("code_validation");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean deleteCodeByUser(int id){
        String statement =
                "DELETE FROM `validation_user` WHERE id_user = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
}
