package cooptool.business.facades;

import cooptool.models.daos.persistent.NotificationDAO;
import cooptool.models.objects.Notification;
import cooptool.models.objects.NotificationType;
import cooptool.models.objects.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Timer;
import java.util.TimerTask;

/**
 * NotificationFacade class
 */
public class NotificationFacade {

    private static class LazyHolder {
        static final NotificationFacade INSTANCE = new NotificationFacade();
    }

    /**
     * Attribute to access to the NotificationDAO methods
     */
    private final NotificationDAO notificationDAO = NotificationDAO.getInstance();

    /**
     * Attribute to store the notifications
     */
    private final ObservableList<Notification> notifications;

    /**
     * Timer that launch the thread to get the notifications
     */
    private Timer timer;

    /**
     * NotificationFacade constructor
     */
    private NotificationFacade() {
        notifications = FXCollections.observableArrayList();
    }

    /**
     * Get the NotificationFacade instance
     * @return NotificationFacade instance
     */
    public static NotificationFacade getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Get the observableList of notifications
     * @return ObservableList of notifications
     */
    public ObservableList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * TimerTask that get the notifications of the user in parameter
     * @param user User we want to get the notifications
     * @return TimerTask that get the notifications
     */
    public TimerTask getNotificationsByUser(User user) {
        return new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    notifications.setAll(notificationDAO.getNotificationsByUser(user));
                    System.out.println("Get notifications");
                });
            }
        };
    }

    /**
     * Method that launch the timertask to get the notifications
     * @param user User we want to get the notifications
     */
    public void searchNotifications(User user) {
        timer = new Timer();
        timer.schedule(getNotificationsByUser(user), 0, 5 * 1000);
    }

    /**
     * Stop the timer
     */
    public void stopTimer() {
        try {
            timer.cancel();
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Create a new notification with the parameters
     * @param user Owner of the notification
     * @param content Message of the notification
     * @param objectId ID of the concerned object
     * @param typeNotification Type of notification
     * @return True if the creation is successful, false otherwise
     */
    public boolean create(User user, String content, int objectId, NotificationType typeNotification) {
        return notificationDAO.create(new Notification(user, content, objectId, typeNotification));
    }

    /**
     * Delete the notification <br>
     * The notification is remove from the observableList that store them
     * @param notification Notification we want to delete
     */
    public void delete(Notification notification) {

        boolean res = notificationDAO.delete(notification);

        if (res) {
            notifications.remove(notification);
        }
    }

    /**
     * Delete all notifications of the user in parameter
     * @param user User we want to delete all notifications
     */
    public void deleteAllNotifications(User user) {
        boolean res = notificationDAO.deleteAllNotificationsByUser(user);

        if (res) {
            notifications.clear();
        }
    }

    /**
     * Change the status of the notification (read / unread)
     * @param notification Notification we want to upate the status
     * @return True if the update is successful, false otherwise
     */
    public boolean changeStatusToRead(Notification notification) {
        notification.changeStatusToRead();

        return notificationDAO.updateStatusRead(notification);
    }
}
