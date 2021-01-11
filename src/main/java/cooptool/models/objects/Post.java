package cooptool.models.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class representing a post
 */
public abstract class Post {

    /**
     * Post ID
     */
    private int id;

    /**
     * Post subject
     */
    private Subject subject;

    /**
     * Post description
     */
    private String description;

    /**
     * Post user creator
     */
    private User creator;

    /**
     * Post creation date
     */
    private LocalDateTime creationDate;

    /**
     * Post comments
     */
    private ArrayList<Comment> comments;

    /**
     * Constructor
     * @param id Post ID
     * @param subject Post subject
     * @param description Post description
     * @param creator Post user creator
     * @param creationDate Post creation date
     */
    public Post(int id, Subject subject, String description, User creator, LocalDateTime creationDate) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.creator = creator;
        this.creationDate = creationDate;
        this.comments = new ArrayList<>();
    }

    /**
     * Constructor
     * @param subject Post subject
     * @param description Post description
     * @param creator Post user creator
     */
    public Post(Subject subject, String description, User creator) {
        this(0, subject, description, creator, LocalDateTime.now());
    }

    /**
     * Constructor
     * @param id Post ID
     * @param subject Post subject
     * @param description Post description
     * @param creationDate Post creation date
     */
    public Post(int id, Subject subject, String description, LocalDateTime creationDate) {
        this(id, subject, description, null, creationDate);
    }

    /**
     * Returns the id of the post
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the subject of the post
     * @return an Subject object
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Returns the description of the post
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the creator of the post
     * @return an User object
     */
    public User getCreator() {
        return creator;
    }

    /**
     * Returns the creation date of the post
     * @return LocalDateTime object
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Returns a representation of the post
     * @return String object which is the representation of the post
     */
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", subject=" + subject +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", creationDate=" + creationDate +
                ", comments=" + comments +
                '}';
    }

    /**
     * Changes the description of the post
     * @param newDesc String new description
     */
    public void setDescription(String newDesc){
        this.description = newDesc;
    }

    /**
     * Adds a comment to the post
     * @param newComment Comment object
     */
    public void addComment(Comment newComment){
        comments.add(newComment);
    }

    /**
     * Returns all the comments of the post
     * @return an arrayList containing Comment objects
     */
    public ArrayList<Comment> getComments(){
        return comments;
    }
}
