package cooptool.models.daos;

import cooptool.models.daos.persistent.NotificationDAO;
import cooptool.models.objects.Notification;
import cooptool.models.objects.User;
import cooptool.utils.TimeUtils;

import java.sql.*;
import java.time.LocalDateTime;
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

        String requete = "INSERT INTO notification (id_user, content_notification, date_creation_notification, id_object_notification, type_notification) " +
                "VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, notification.getUser().getId());
            preparedStatement.setString(2, notification.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(4, notification.getObjectId());
            preparedStatement.setInt(5, notification.getTypeNotification().getValue());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public int getNbUnreadNotifications(User user) {

        String requete = "SELECT COUNT(*) as nb " +
                "FROM notification " +
                "WHERE id_user = ? AND is_read = 0;";

        int nbNotifications = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                nbNotifications = rs.getInt("nb");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return nbNotifications;
    }

    @Override
    public boolean upadteStatusRead(Notification notification) {
        return false;
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

    @Override
    public boolean deleteAllNotificationsByUser(User user) {

        String requete = "DELETE FROM notification WHERE id_user = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
