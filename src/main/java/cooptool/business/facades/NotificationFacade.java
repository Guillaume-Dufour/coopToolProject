package cooptool.business.facades;

import cooptool.models.daos.persistent.NotificationDAO;
import cooptool.models.objects.Notification;
import cooptool.models.objects.NotificationType;
import cooptool.models.objects.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.WritableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationFacade {

    private static class LazyHolder {
        static final NotificationFacade INSTANCE = new NotificationFacade();
    }

    private final NotificationDAO notificationDAO = NotificationDAO.getInstance();
    private final ObservableList<Notification> notifications;
    private Timer timer;

    private NotificationFacade() {
        notifications = FXCollections.observableArrayList();
    }

    public static NotificationFacade getInstance() {
        return LazyHolder.INSTANCE;
    }

    public ObservableList<Notification> getNotifications() {
        return notifications;
    }

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

    public void searchNotifications(User user) {
        timer = new Timer();
        timer.schedule(getNotificationsByUser(user), 0, 5 * 1000);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public boolean create(User user, String content, int objectId, NotificationType typeNotification) {
        return notificationDAO.create(new Notification(user, content, objectId, typeNotification));
    }

    public void delete(Notification notification) {

        boolean res = notificationDAO.delete(notification);

        if (res) {
            notifications.remove(notification);
        }
    }

    public void deleteAllNotifications(User user) {
        boolean res = notificationDAO.deleteAllNotificationsByUser(user);

        if (res) {
            notifications.clear();
        }
    }

    public boolean changeStatusToRead(Notification notification) {
        notification.changeStatusToRead();

        return notificationDAO.updateStatusRead(notification);
    }
}
