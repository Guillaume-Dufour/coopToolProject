package cooptool.models.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Post {
    private int id;
    private Subject subject;
    private String description;
    private User creator;
    private LocalDateTime creationDate;
    private ArrayList<Comment> comments;

    public Post(int id, Subject subject, String description, User creator, LocalDateTime creationDate) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.creator = creator;
        this.creationDate = creationDate;
        this.comments = new ArrayList<>();
    }

    public Post(Subject subject, String description, User creator) {
        this(0, subject, description, creator, LocalDateTime.now());
    }

    public Post(int id, Subject subject, String description, LocalDateTime dateTime) {
        this(id, subject, description, null, dateTime);
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public User getCreator() {
        return creator;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public void setDescription(String newDesc){
        this.description = newDesc;
    }

    public void addComment(Comment newComment){
        comments.add(newComment);
    }

    public ArrayList<Comment> getComments(){
        return comments;
    }
}
