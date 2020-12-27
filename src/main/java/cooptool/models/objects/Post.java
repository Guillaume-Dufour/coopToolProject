package cooptool.models.objects;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private Subject subject;
    private String description;
    private User creator;
    private LocalDateTime creationDate;

    public Post(int id, Subject subject, String description, User creator, LocalDateTime creationDate) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.creator = creator;
        this.creationDate = creationDate;
    }

    public Post(Subject subject, String description, User creator) {
        this(1, subject, description, creator, LocalDateTime.now());
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
}
