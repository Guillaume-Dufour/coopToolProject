package cooptool.models.daos;

import cooptool.models.objects.Notification;
import cooptool.models.objects.User;

import java.util.List;

public abstract class NotificationDAO {

    private static class LazyHolder {
        static final NotificationDAO INSTANCE = AbstractDAOFactory.getInstance().getNotificationDAO();
    }

    public NotificationDAO getInstance() {
        return LazyHolder.INSTANCE;
    }

    public abstract List<Notification> getNotificationsByUser(User user);

    public abstract boolean create(Notification notification);

    public abstract boolean delete(Notification notification);
}
