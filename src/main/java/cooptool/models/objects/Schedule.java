package cooptool.models.objects;

import cooptool.utils.TimeUtils;

import java.time.LocalDateTime;

public class Schedule {

    private LocalDateTime dateTime;
    private User creator;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public User getCreator() {
        return creator;
    }

    public Schedule(LocalDateTime date, User creator){
        this.dateTime = date;
        this.creator = creator;
    }

    public String toString(){
        return String.format(
                "%s/%s/%d %sh%s",
                TimeUtils.format(dateTime.getDayOfMonth()),
                TimeUtils.format(dateTime.getMonthValue()),
                dateTime.getYear(),
                TimeUtils.format(dateTime.getHour()),
                TimeUtils.format(dateTime.getMinute())
        );
    }

}
