package cooptool.models.objects;

import java.util.ArrayList;

public class MentoringDemand {

    private Department department;
    private Subject subject;
    private String description;
    private ArrayList<Schedule> schedules;
    private User creator;

    public Department getDepartment() {
        return department;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public User getCreator() {
        return creator;
    }

    public MentoringDemand(Department department, Subject subject, String description, ArrayList<Schedule> schedules, User creator){
        this.department = department;
        this.subject = subject;
        this.description = description;
        this.schedules = schedules;
        this.creator = creator;
    }

    public void addSchedule(Schedule schedule){

    }

    public void removeSchedule(Schedule schedule){

    }
}
