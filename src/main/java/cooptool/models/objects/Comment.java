package cooptool.models.objects;

import java.time.LocalDateTime;

/**
 * Class representing post comments
 */
public class Comment {

    /**
     * Comment ID
     */
    private int id;

    /**
     * Comment content
     */
    private String content;

    /**
     * Comment creation date
     */
    private LocalDateTime creationDate;

    /**
     * Comment user creator
     */
    private User creator;

    /**
     * Constructor
     * @param id Comment ID
     * @param content Comment content
     * @param creationDate Comment creation date
     * @param creator Comment user creator
     */
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
