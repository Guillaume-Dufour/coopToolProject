package cooptool.business.facades;

import cooptool.models.daos.MentoringDemandDAO;
import cooptool.models.objects.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MentoringDemandFacade {

    private static final MentoringDemandFacade INSTANCE;

    static{
        INSTANCE = new MentoringDemandFacade();
    }

    private MentoringDemandFacade(){}

    public static MentoringDemandFacade getInstance(){
        return INSTANCE;
    }

    public void create(MentoringDemand mentoringDemand){
        MentoringDemandDAO.getInstance().create(mentoringDemand);
    }

    public void delete(MentoringDemand mentoringDemand){

    }

    public void update(MentoringDemand mentoringDemand){

    }

    public MentoringDemand getMentoringDemand(int id){
        return MentoringDemandDAO.getInstance().getMentoringDemand(id);
    }

    public List<MentoringDemand> getMentoringDemands(){
        UserRole userRole = UserFacade.getInstance().getCurrentUser().getRole();
        if(userRole instanceof StudentRole){
            return MentoringDemandDAO.getInstance().getPartialMentoringDemands(((StudentRole) userRole).getDepartment());
        }
        else{
            return MentoringDemandDAO.getInstance().getPartialMentoringDemands();
        }
    }

    public Participation getCurrentUserParticipation(MentoringDemand demand){
        int idUser = UserFacade.getInstance().getCurrentUser().getId();
        for(Participation participation : demand.getParticipationArray()){
            if(idUser == participation.getParticipant().getId()){
                return participation;
            }
        }
        return null;
    }

    public void suppressCurrentUserParticipation(MentoringDemand demand){
        MentoringDemandDAO.getInstance().suppressParticipation(demand,UserFacade.getInstance().getCurrentUser());
    }

    public void participate(MentoringDemand demand,int participationType,ArrayList<Schedule> schedules){
        MentoringDemandDAO.getInstance().participate(
                demand,
                new Participation(UserFacade.getInstance().getCurrentUser(),participationType,schedules)
        );
    }
    
    public void participateToSchedule(MentoringDemand demand, int participationType, Schedule schedule){
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule);
        participate(demand,participationType,schedules);
    }
    
    public void quitSchedule(MentoringDemand demand, Schedule schedule){
        MentoringDemandDAO.getInstance().quitSchedule(
                demand,UserFacade.getInstance().getCurrentUser(),schedule
        );
    }

    public void addSchedule(MentoringDemand demand, LocalDateTime date){
        MentoringDemandDAO.getInstance().addSchedule(
                demand,new Schedule(date,UserFacade.getInstance().getCurrentUser())
        );
    }

    public boolean isCurrentUserCreatorOfSchedule(Schedule schedule){
        return UserFacade.getInstance().getCurrentUser().getId() == schedule.getCreator().getId();
    }

    public void deleteSchedule(MentoringDemand demand,Schedule schedule){
        MentoringDemandDAO.getInstance().removeSchedule(demand,schedule);
    }
}
