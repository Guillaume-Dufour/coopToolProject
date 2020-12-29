package cooptool.models.objects;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private String content;
    private LocalDateTime creationDate;
    private User creator;

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public User getCreator() {
        return creator;
    }

    public Comment(int id, String content, LocalDateTime creationDate, User creator){
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.creator = creator;
    }
}
