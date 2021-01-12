package cooptool.models.objects;

import java.time.LocalDateTime;

/**
 * Notification class
 */
public class Notification {

    /**
     * Notification ID
     */
    private int id;

    /**
     * Notification user
     */
    private User user;

    /**
     * Notification content
     */
    private String content;

    /**
     * Notification creation date
     */
    private LocalDateTime dateCreation;

    /**
     * Notification object ID
     */
    private int objectId;

    /**
     * Notification type
     */
    private NotificationType typeNotification;

    /**
     * Notification state (read / unread)
     */
    private int isRead;

    /**
     * Constructor
     * @param id Notification ID
     * @param user Notification user
     * @param content Notification content
     * @param dateCreation Notification creation date
     * @param objectId Notification object ID
     * @param typeNotification Notification type
     * @param isRead Notification state
     */
    public Notification(int id, User user, String content, LocalDateTime dateCreation, int objectId, NotificationType typeNotification, int isRead) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.dateCreation = dateCreation;
        this.objectId = objectId;
        this.typeNotification = typeNotification;
        this.isRead = isRead;
    }

    /**
     * Constructor
     * @param user Notification user
     * @param content Notification content
     * @param objectId Notification object ID
     * @param typeNotification Notification type
     */
    public Notification(User user, String content, int objectId, NotificationType typeNotification) {
        this(0, user, content, LocalDateTime.now(), objectId, typeNotification, 0);
    }

    /**
     * Get the notification ID
     * @return Notification ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the notification user
     * @return Notification user
     */
    public User getUser() {
        return user;
    }

    /**
     * Get the notification content
     * @return Notification content
     */
    public String getContent() {
        return content;
    }

    /**
     * Get the notification creation date
     * @return Notification creation date
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }


    /**
     * Get the notification object ID
     * @return Notification object ID
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * Get the notification type
     * @return Notification type
     */
    public NotificationType getTypeNotification() {
        return typeNotification;
    }

    /**
     * Get the notification state
     * @return Notification state
     */
    public int getIsRead() {
        return isRead;
    }

    /**
     * Change the notifications state to read
     */
    public void changeStatusToRead() {
        isRead = 1;
    }
}
