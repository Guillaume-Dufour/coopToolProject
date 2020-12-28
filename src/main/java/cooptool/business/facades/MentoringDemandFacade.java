package cooptool.business.facades;

import cooptool.exceptions.TooMuchSchedules;
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
    
    private MentoringDemandDAO mentoringDemandDAO = MentoringDemandDAO.getInstance();

    private PostFacade postFacade = PostFacade.getInstance();

    private User currentUser = UserFacade.getInstance().getCurrentUser();
    
    private MentoringDemandFacade(){}

    public static MentoringDemandFacade getInstance(){
        return INSTANCE;
    }

    public void create(Subject subject,String description,LocalDateTime dateTime){
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule(dateTime, currentUser));
        MentoringDemand demand =
                new MentoringDemand(-1,subject,description,LocalDateTime.now(),schedules,currentUser);
        mentoringDemandDAO.create(demand);
    }

    public void delete(MentoringDemand mentoringDemand){
        mentoringDemandDAO.delete(mentoringDemand);
    }


    public MentoringDemand getMentoringDemand(int id){
        MentoringDemand demand = mentoringDemandDAO.getMentoringDemand(id);
        postFacade.getComments(demand);
        return demand;
    }

    public List<MentoringDemand> getMentoringDemands(){
        UserRole userRole = currentUser.getRole();
        if(userRole instanceof StudentRole){
            return mentoringDemandDAO.getPartialMentoringDemands(((StudentRole) userRole).getDepartment());
        }
        else{
            return mentoringDemandDAO.getPartialMentoringDemands();
        }
    }

    public Participation getCurrentUserParticipation(MentoringDemand demand){
        int idUser = currentUser.getId();
        for(Participation participation : demand.getParticipationArray()){
            if(idUser == participation.getParticipant().getId()){
                return participation;
            }
        }
        return null;
    }

    public void suppressCurrentUserParticipation(MentoringDemand demand){
        mentoringDemandDAO.suppressParticipation(demand,currentUser);
    }

    public void participate(MentoringDemand demand,int participationType,ArrayList<Schedule> schedules){
        mentoringDemandDAO.participate(
                demand,
                new Participation(currentUser,participationType,schedules)
        );
    }
    
    public void participateToSchedule(MentoringDemand demand, int participationType, Schedule schedule){
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule);
        participate(demand,participationType,schedules);
    }
    
    public void quitSchedule(MentoringDemand demand, Schedule schedule){
        mentoringDemandDAO.quitSchedule(
                demand,currentUser,schedule
        );
    }

    public void addSchedule(MentoringDemand demand, LocalDateTime date) throws TooMuchSchedules{
        System.out.println(mentoringDemandDAO.getNumberOfSchedules(demand));
        if(mentoringDemandDAO.getNumberOfSchedules(demand) < 10){
            mentoringDemandDAO.addSchedule(
                    demand,new Schedule(date,currentUser)
            );
        }
        else{
            throw new TooMuchSchedules();
        }
    }

    public boolean isCurrentUserCreatorOfSchedule(Schedule schedule){
        return currentUser.getId() == schedule.getCreator().getId();
    }

    public void deleteSchedule(MentoringDemand demand,Schedule schedule){
        mentoringDemandDAO.removeSchedule(demand,schedule);
        mentoringDemandDAO.removeParticipation(demand,schedule);
    }

    public boolean isCurrentUserCreatorOfDemand(MentoringDemand demand){
        return currentUser.getId() == demand.getCreator().getId();
    }

    public void updateDescription(MentoringDemand demand,String updatedDesc){
        demand.setDescription(updatedDesc);
        mentoringDemandDAO.updateDescription(demand);
    }

}
