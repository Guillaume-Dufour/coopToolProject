package cooptool.models.objects;

import java.time.LocalDate;

public class Schedule {

    private LocalDate date;
    private User creator;

    public LocalDate getDate() {
        return date;
    }

    public User getCreator() {
        return creator;
    }

    public Schedule(LocalDate date, User creator){
        this.date = date;
        this.creator = creator;
    }

}
