package cooptool.models.objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class QuickHelpPost extends Post{

    public QuickHelpPost(int id, Subject subject, String description, User creator, LocalDateTime creationDate) {
        super(id, subject, description, creator, creationDate);
    }

    public QuickHelpPost(Subject subject, String description, User creator) {
        this(1, subject, description, creator, LocalDateTime.now());
    }
}
