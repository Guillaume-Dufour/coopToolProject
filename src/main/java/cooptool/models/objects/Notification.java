package cooptool.models.objects;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private User user;
    private String content;
    private LocalDateTime dateCreation;
    private NotificationType typeNotification;

    public Notification(int id, User user, String content, LocalDateTime dateCreation, NotificationType typeNotification) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.dateCreation = dateCreation;
        this.typeNotification = typeNotification;
    }

    public Notification(User user, String content, NotificationType typeNotification) {
        this(0, user, content, LocalDateTime.now(), typeNotification);
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    @Override
    public String toString() {
        return content;
    }
}
