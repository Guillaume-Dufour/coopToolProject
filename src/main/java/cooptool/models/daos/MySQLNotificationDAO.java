package cooptool.models.daos;

import cooptool.models.daos.persistent.NotificationDAO;
import cooptool.models.enumDatabase.NotificationTable;
import cooptool.models.enumDatabase.UserTable;
import cooptool.models.objects.Notification;
import cooptool.models.objects.User;
import cooptool.utils.TimeUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQLNotificationDAO class
 */
public class MySQLNotificationDAO extends NotificationDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLNotificationDAO() {
        super();
    }

    @Override
    public List<Notification> getNotificationsByUser(User user) {

        List<Notification> notifications = new ArrayList<>();

        String requete = "SELECT * " +
                "FROM notification n " +
                "JOIN user u on n.%s = u.%s AND u.%s = ?;";

        requete = String.format(requete,
                NotificationTable.ID_USER,
                UserTable.ID_USER,
                UserTable.ID_USER
        );

        try {

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
    public boolean updateStatusRead(Notification notification) {

        String requete = "UPDATE notification SET is_read = ? WHERE id_notification = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);
            preparedStatement.setInt(1, notification.getIsRead());
            preparedStatement.setInt(2, notification.getId());
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
