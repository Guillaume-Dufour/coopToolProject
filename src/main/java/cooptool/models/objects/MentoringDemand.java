package cooptool.models.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MentoringDemand {

    public static final int STUDENT = 0;
    public static final int TUTOR = 1;

    private int id;
    private Subject subject;
    private String description;
    private LocalDateTime creationDate;
    private ArrayList<Schedule> schedules;
    private User creator;
    private ArrayList<Participation> participationArray = new ArrayList<Participation>();

    public int getId(){
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public String schedulesToString(){
        String res = "";
        for(Schedule schedule : schedules){
            res += schedule.getDate().toString() + "\n";
        }
        return res;
    }

    public User getCreator() {
        return creator;
    }

    public MentoringDemand(int id, Subject subject, String description, LocalDateTime creationDate, ArrayList<Schedule> schedules, User creator){
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.schedules = schedules;
        this.creator = creator;
    }

    public void addSchedule(Schedule schedule){

    }

    public void removeSchedule(Schedule schedule){

    }

    public void addParticipation(Participation newParticipation){
        participationArray.add(newParticipation);
    }

    public ArrayList<Participation> getParticipationArray(){
        return participationArray;
    }
}
