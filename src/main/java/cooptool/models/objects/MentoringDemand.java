package cooptool.models.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class representing mentoring demands that are posts where users can participate as either student or tutor
 * When they participate they need to give a schedule
 */
public class MentoringDemand extends Post {

    public static final int STUDENT = 0;
    public static final int TUTOR = 1;

    private ArrayList<Schedule> schedules;
    private ArrayList<Participation> participationArray = new ArrayList<>();

    /**
     * Return the schedules available for the demand
     * @return the array containing Schedule objects
     */
    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    /**
     * Return a representation of all the schedules of the demand
     * @return a string representation of the schedules
     */
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

    /**
     * Add a schedule to the demand
     * @param schedule the schedule to add
     */
    public void addSchedule(Schedule schedule){
        schedules.add(schedule);
    }

    /**
     * Add a new participation to the demand
     * @param newParticipation the participation object to add to the demand
     */
    public void addParticipation(Participation newParticipation){
        participationArray.add(newParticipation);
    }

    /**
     * Returns all the participations of the demand
     * @return an arraylist of all the Participation objects of the demand
     */
    public ArrayList<Participation> getParticipationArray(){
        return participationArray;
    }


}
