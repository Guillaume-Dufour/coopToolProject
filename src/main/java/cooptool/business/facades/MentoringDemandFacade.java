package cooptool.business.facades;

import cooptool.exceptions.ScheduleAlreadyExistsException;
import cooptool.exceptions.TooMuchSchedulesException;
import cooptool.models.daos.persistent.MentoringDemandDAO;
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

    private UserFacade userFacade = UserFacade.getInstance();
    
    private MentoringDemandFacade(){}

    /**
     * Returns the instance of the facade
     * @return MentoringDemandFacade Object
     */
    public static MentoringDemandFacade getInstance(){
        return INSTANCE;
    }

    /**
     * Creates a mentoring demand
     * @param subject Subject object of the demand
     * @param description String description
     * @param dateTime LocalDateTime corresponding to the creation date
     */
    public void create(Subject subject,String description,LocalDateTime dateTime){
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule(dateTime, userFacade.getCurrentUser()));
        System.out.println(userFacade.getCurrentUser());
        MentoringDemand demand =
                new MentoringDemand(-1,subject,description,LocalDateTime.now(),schedules,userFacade.getCurrentUser());
        mentoringDemandDAO.create(demand);
    }

    /**
     * Deletes a mentoring demand
     * @param mentoringDemand demand to delete
     */
    public void delete(MentoringDemand mentoringDemand){
        mentoringDemandDAO.delete(mentoringDemand);
    }

    /**
     * Returns a mentoring demand
     * @param id int the id of the demand
     * @return the mentoring demand object
     */
    public MentoringDemand getMentoringDemand(int id){
        MentoringDemand demand = mentoringDemandDAO.getMentoringDemand(id);
        postFacade.getComments(demand);
        return demand;
    }

    /**
     * Returns mentoring demands
     * @return either the demands corresponding to the promotion of the user if he's student
     *          else returns all the existing mentoring demands
     */
    public List<MentoringDemand> getMentoringDemands(){
        UserRole userRole = userFacade.getCurrentUser().getRole();
        if(userRole instanceof StudentRole){
            return mentoringDemandDAO.getMentoringDemands(((StudentRole) userRole).getDepartment());
        }
        else{
            return mentoringDemandDAO.getMentoringDemands();
        }
    }

    /**
     * Retrieves the participation of the current user in a mentoring demand
     * @param demand the corresponding demand
     * @return Either Participation object if he's participating else it returns null
     */
    public Participation getCurrentUserParticipation(MentoringDemand demand){
        int idUser = userFacade.getCurrentUser().getId();
        for(Participation participation : demand.getParticipationArray()){
            if(idUser == participation.getParticipant().getId()){
                return participation;
            }
        }
        return null;
    }

    /**
     * Suppresses the current user participation in the mentoring demand
     * @param demand the corresponding demand
     */
    public void suppressCurrentUserParticipation(MentoringDemand demand){
        mentoringDemandDAO.suppressParticipation(demand,userFacade.getCurrentUser());
    }

    /**
     * Adds the participation of the current user to a mentoring demand
     * @param demand the corresponding demand
     * @param participationType either student or tutor
     * @param schedules all the schedules selected
     */
    public void participate(MentoringDemand demand,int participationType,ArrayList<Schedule> schedules){
        mentoringDemandDAO.participate(
                demand,
                new Participation(userFacade.getCurrentUser(),participationType,schedules)
        );
    }

    /**
     * Function called when a user wants to participate to only one schedule in a mentoring demand
     * @param demand the corresponding demand
     * @param participationType either student or tutor
     * @param schedule the schedule picked by the user
     */
    public void participateToSchedule(MentoringDemand demand, int participationType, Schedule schedule){
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule);
        participate(demand,participationType,schedules);
    }

    /**
     * Quits a schedule for the current user from a mentoring demand
     * @param demand the corresponding demand
     * @param schedule the schedule to quit on
     */
    public void quitSchedule(MentoringDemand demand, Schedule schedule){
        mentoringDemandDAO.quitSchedule(
                demand,userFacade.getCurrentUser(),schedule
        );
    }

    /**
     * Add a new schedule to a mentoring demand
     * @param demand the corresponding demand
     * @param date the schedule to add
     * @throws TooMuchSchedulesException if there is already 10 schedules or more
     * @throws ScheduleAlreadyExistsException if the schedule already exists
     */
    public void addSchedule(MentoringDemand demand, LocalDateTime date) throws TooMuchSchedulesException, ScheduleAlreadyExistsException {
        if(mentoringDemandDAO.getNumberOfSchedules(demand) < 10){
            if(!scheduleAlreadyExistsInDemand(demand,date)){
                mentoringDemandDAO.addSchedule(
                        demand,new Schedule(date,userFacade.getCurrentUser())
                );
            }
            else{
                throw new ScheduleAlreadyExistsException();
            }
        }
        else{
            throw new TooMuchSchedulesException();
        }
    }

    private boolean scheduleAlreadyExistsInDemand(MentoringDemand demand,LocalDateTime date){
        ArrayList<Schedule> demandSchedules = demand.getSchedules();
        for(Schedule demandSchedule : demandSchedules){
            if(demandSchedule.getDateTime().equals(date)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the current user is the creator of the schedule
     * @param schedule the schedule to ask on
     * @return true if he is the creator else returns false
     */
    public boolean isCurrentUserCreatorOfSchedule(Schedule schedule){
        return userFacade.getCurrentUser().getId() == schedule.getCreator().getId();
    }

    /**
     * Deletes a schedule from a mentoring demand
     * @param demand the corresponding demand
     * @param schedule the schedule to delete
     */
    public void deleteSchedule(MentoringDemand demand,Schedule schedule){
        mentoringDemandDAO.removeSchedule(demand,schedule);
    }

    /**
     * Returns true if the current user is the creator of the mentoring demand
     * @param demand the corresponding demand
     * @return true if he is else returns false
     */
    public boolean isCurrentUserCreatorOfDemand(MentoringDemand demand){
        return userFacade.getCurrentUser().getId() == demand.getCreator().getId();
    }

    /**
     * Updates the description of the mentoring demand
     * @param demand the corresponding demand
     * @param updatedDesc the new description
     */
    public void updateDescription(MentoringDemand demand,String updatedDesc){
        demand.setDescription(updatedDesc);
        mentoringDemandDAO.updateDescription(demand);
    }

}
