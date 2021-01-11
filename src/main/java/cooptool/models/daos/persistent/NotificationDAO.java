package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Notification;
import cooptool.models.objects.User;

import java.util.List;

/**
 * NotificationDAO class
 */
public abstract class NotificationDAO {

    private static class LazyHolder {
        static final NotificationDAO INSTANCE = AbstractDAOFactory.getInstance().getNotificationDAO();
    }

    protected NotificationDAO() {}

    /**
     * Get the NotificationDAO instance
     * @return NotificationDAO instance
     */
    public static NotificationDAO getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Return the list of notifications of a user in parameter
     * @param user User we want the notifications
     * @return List of notifications of a user
     */
    public abstract List<Notification> getNotificationsByUser(User user);

    /**
     * Create a new notification in parameter in the database
     * @param notification Notification we want to insert
     * @return True if the insertion is successful, false otherwise
     */
    public abstract boolean create(Notification notification);

    /**
     * Delete the notification in parameter in the database
     * @param notification Notification we want to delete
     * @return True is the deletion is successful, false otherwise
     */
    public abstract boolean delete(Notification notification);

    /**
     * Delete all the notifications for the user in parameter
     * @param user User we want to delete all the notifications
     * @return True if the deletion is successful, false otherwise
     */
    public abstract boolean deleteAllNotificationsByUser(User user);

    /**
     * Update the status read / unread of a notification
     * @param notification Notification we want to update the status
     * @return True if the deletion is successful, false otherwise
     */
    public abstract boolean updateStatusRead(Notification notification);
}
