package cooptool.models.objects;

public class Notification {

    private int id;
    private User user;
    private String content;
    private NotificationType typeNotification;

    public Notification(int id, User user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
