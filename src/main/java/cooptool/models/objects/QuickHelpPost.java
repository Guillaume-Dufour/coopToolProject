package cooptool.models.objects;

import java.time.LocalDate;
import java.util.ArrayList;

public class QuickHelpPost {

    private int id;
    private Department department;
    private Subject subject;
    private String description;
    private LocalDate creationDate;
    private User creator;

    public QuickHelpPost(int i, Department department, Subject subject, String description, LocalDate now, User user) {
        this.id = id;
        this.department = department;
        this.subject = subject;
        this.description = description;
        this.creator = creator;
    }

    public int getId(){
        return id;
    }

    public Department getDepartment() { return department;}

    public Subject getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }

    public User getCreator() {
        return creator;
    }
}
