package cooptool.business.facades;

import cooptool.models.daos.NotificationDAO;
import cooptool.models.objects.Notification;
import cooptool.models.objects.User;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationFacade {

    private static class LazyHolder {
        static final NotificationFacade INSTANCE = new NotificationFacade();
    }

    private final NotificationDAO notificationDAO = NotificationDAO.getInstance();
    //private final IntegerProperty nbNotifications;
    private final ObservableList<Notification> notifications;
    private final Timer timer;

    private boolean run = false;

    private NotificationFacade() {
        timer = new Timer();
        notifications = FXCollections.observableArrayList();
        //nbNotifications = new SimpleIntegerProperty();
    }

    public static NotificationFacade getInstance() {
        return LazyHolder.INSTANCE;
    }

    public ObservableList<Notification> getNotifications() {
        return notifications;
    }

    /*public TimerTask getNbNotificationsByUser(User user) {
        return new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    nbNotifications.setValue(notificationDAO.getNbNotificationsByUser(user));
                    System.out.println("nb notifs");
                });
            }
        };
    }*/

    public TimerTask getNotificationsByUser(User user) {
        return new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    notifications.setAll(notificationDAO.getNotificationsByUser(user));
                    System.out.println("all notifications");
                });
            }
        };
    }

    public void searchNotifications(User user) {
        timer.schedule(getNotificationsByUser(user), 0, 10 * 1000);
    }

    /*public IntegerProperty getNbNotifications() {
        return nbNotifications;
    }*/

    /*public void changeTaskToNbNotifications(User user) {
        purgeTimer();
        timer.schedule(getNbNotificationsByUser(user), 0, 5 * 1000);
    }

    public void changeTaskToGetNotifications(User user) {
        purgeTimer();
        timer.schedule(getNotificationsByUser(user), 0, 5 * 1000);
    }

    public void purgeTimer() {
        timer.purge();
    }*/

    public void stopTimer() {
        timer.cancel();
    }

    public boolean create() {
        return notificationDAO.create(new Notification(0, null, null));
    }

    public boolean delete(Notification notification) {
        return notificationDAO.delete(notification);
    }
}
