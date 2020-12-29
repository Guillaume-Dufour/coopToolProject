package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Notification;
import cooptool.models.objects.User;

import java.util.List;

public abstract class NotificationDAO {

    private static class LazyHolder {
        static final NotificationDAO INSTANCE = AbstractDAOFactory.getInstance().getNotificationDAO();
    }

    public static NotificationDAO getInstance() {
        return LazyHolder.INSTANCE;
    }

    public abstract List<Notification> getNotificationsByUser(User user);

    public abstract boolean create(Notification notification);

    public abstract boolean delete(Notification notification);

    public abstract boolean deleteAllNotificationsByUser(User user);

    public abstract int getNbNotificationsByUser(User user);
}
