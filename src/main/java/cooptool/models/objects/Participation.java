package cooptool.models.objects;

import java.util.ArrayList;

public class Participation {
    private User participant;
    private int participationType;
    private ArrayList<Schedule> participationSchedules;

    public User getParticipant() {
        return participant;
    }

    public int getParticipationType() {
        return participationType;
    }

    public ArrayList<Schedule> getParticipationSchedules() {
        return participationSchedules;
    }

    public Participation(User participant, int participationType, ArrayList<Schedule> participationSchedules){
        this.participant = participant;
        this.participationType = participationType;
        this.participationSchedules = participationSchedules;
    }
}
