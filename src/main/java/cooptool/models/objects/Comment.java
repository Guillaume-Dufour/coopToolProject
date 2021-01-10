package cooptool.models.objects;

import java.time.LocalDateTime;

/**
 * Class representing post comments
 */
public class Comment {
    private int id;
    private String content;
    private LocalDateTime creationDate;
    private User creator;

    public Comment(int id, String content, LocalDateTime creationDate, User creator){
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.creator = creator;
    }

    /**
     * Returns the comment id
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the comment content
     * @return String content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the comment creation date
     * @return LocalDateTime creationDate
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Returns the comment creator
     * @return User creator
     */
    public User getCreator() {
        return creator;
    }
}
