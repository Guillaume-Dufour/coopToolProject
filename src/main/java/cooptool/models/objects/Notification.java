package cooptool.models.objects;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private User user;
    private String content;
    private LocalDateTime dateCreation;
    private int objectId;
    private NotificationType typeNotification;
    private int isRead;

    public Notification(int id, User user, String content, LocalDateTime dateCreation, int objectId, NotificationType typeNotification, int isRead) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.dateCreation = dateCreation;
        this.objectId = objectId;
        this.typeNotification = typeNotification;
        this.isRead = isRead;
    }

    public Notification(User user, String content, int objectId, NotificationType typeNotification) {
        this(0, user, content, LocalDateTime.now(), objectId, typeNotification, 0);
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

    public int getObjectId() {
        return objectId;
    }

    public NotificationType getTypeNotification() {
        return typeNotification;
    }

    public int getIsRead() {
        return isRead;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", dateCreation=" + dateCreation +
                ", objectId=" + objectId +
                ", typeNotification=" + typeNotification +
                '}';
    }
}
