package cooptool.models.objects;

import java.time.LocalDateTime;

public class Schedule {

    private LocalDateTime date;
    private User creator;

    public LocalDateTime getDate() {
        return date;
    }

    public User getCreator() {
        return creator;
    }

    public Schedule(LocalDateTime date, User creator){
        this.date = date;
        this.creator = creator;
    }

}
