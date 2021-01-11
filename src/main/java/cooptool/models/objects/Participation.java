package cooptool.models.objects;

import java.util.ArrayList;

/**
 * Participation class
 */
public class Participation {

    /**
     * Participation user
     */
    private User participant;

    /**
     * Participation type
     */
    private int participationType;

    /**
     * Participation schedules
     */
    private ArrayList<Schedule> participationSchedules;

    /**
     * Constructor
     * @param participant Participant user
     * @param participationType Participant type
     * @param participationSchedules Participation schedules
     */
    public Participation(User participant, int participationType, ArrayList<Schedule> participationSchedules){
        this.participant = participant;
        this.participationType = participationType;
        this.participationSchedules = participationSchedules;
    }

    /**
     * Get the participant user
     * @return Participant user
     */
    public User getParticipant() {
        return participant;
    }

    /**
     * Get the participation type
     * @return Participation type
     */
    public int getParticipationType() {
        return participationType;
    }

    /**
     * Get the participation schedules
     * @return Participation schedules
     */
    public ArrayList<Schedule> getParticipationSchedules() {
        return participationSchedules;
    }

    public void addSchedule(Schedule schedule){
        participationSchedules.add(schedule);
    }
}
