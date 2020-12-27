package cooptool.models.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MentoringDemand extends Post{

    public static final int STUDENT = 0;
    public static final int TUTOR = 1;

    private ArrayList<Schedule> schedules;
    private ArrayList<Participation> participationArray = new ArrayList<>();

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public String schedulesToString(){
        String res = "";
        for(Schedule schedule : schedules){
            res += schedule.toString() + "\n";
        }
        return res;
    }


    public MentoringDemand(int id, Subject subject, String description, LocalDateTime creationDate, ArrayList<Schedule> schedules, User creator) {
        super(id, subject, description, creator, creationDate);
        this.schedules = schedules;
    }

    public MentoringDemand(Subject subject, String description, ArrayList<Schedule> schedules, User creator) {
        super(subject, description, creator);
        this.schedules = schedules;
    }

    public MentoringDemand(int id, Subject subject, String description, LocalDateTime time) {
        super(id, subject, description, null, time);
        this.schedules = null;
    }

    public void addSchedule(Schedule schedule){
        schedules.add(schedule);
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
