package cooptool.models.daos;

import cooptool.models.objects.MySQLFactoryObject;
import cooptool.models.objects.Notification;
import cooptool.models.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLNotificationDAO extends NotificationDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLNotificationDAO() {
        super();
    }

    @Override
    public List<Notification> getNotificationsByUser(User user) {

        List<Notification> notifications = new ArrayList<>();

        try {
            String requete = "SELECT * " +
                    "FROM notification n " +
                    "JOIN user u on n.id_user = u.id_user AND u.id_user = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, user.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                notifications.add(MySQLFactoryObject.createNotification(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    @Override
    public boolean create(Notification notification) {

        String requete = "INSERT INTO notification (id_user, content_notification) VALUES (?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, notification.getUser().getId());
            preparedStatement.setString(2, notification.getContent());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Notification notification) {

        String requete = "DELETE FROM notification WHERE id_notification = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, notification.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
