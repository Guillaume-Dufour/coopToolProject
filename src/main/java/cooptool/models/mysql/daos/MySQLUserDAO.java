package cooptool.models.mysql.daos;

import cooptool.models.UserDAO;
import cooptool.models.mysql.MySQLConnection;
import cooptool.models.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUserDAO extends UserDAO {

    Connection connection = MySQLConnection.getInstance();

    @Override
    public User findUserByMail(String mail) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE mail_user =?"
            );
            statement.setString(1, mail);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                int id = result.getInt("id_user");
                //TODO : est-ce qu'on met le type ?
                String mail_user = result.getString("mail_user");
                String firstName = result.getString("first_name_user");
                //user = new User(id,mail_user,firstName);
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
    public void udpate(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
