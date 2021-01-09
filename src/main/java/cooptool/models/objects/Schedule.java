package cooptool.models.objects;

import cooptool.utils.TimeUtils;

import java.time.LocalDateTime;

/**
 * Class representing schedule with a couple of a time and an User which is the creator
 */
public class Schedule {

    private LocalDateTime dateTime;
    private User creator;

    /**
     * Returns the date time of the schedule
     * @return LocalDateTime object
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the creator of the schedule
     * @return User object
     */
    public User getCreator() {
        return creator;
    }

    public Schedule(LocalDateTime date, User creator){
        this.dateTime = date;
        this.creator = creator;
    }

    /**
     * Returns a representation of the schedule
     * @return String which is the representation
     */
    @Override
    public String toString(){
        return String.format(
                "%s/%s/%d %s:%s",
                TimeUtils.format(dateTime.getDayOfMonth()),
                TimeUtils.format(dateTime.getMonthValue()),
                dateTime.getYear(),
                TimeUtils.format(dateTime.getHour()),
                TimeUtils.format(dateTime.getMinute())
        );
    }
}
