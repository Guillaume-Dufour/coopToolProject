package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.*;

import java.util.List;

/**
 * MentoringDemandDAO class
 */
public abstract class MentoringDemandDAO {

    /**
     * Instance of the MentoringDemandDAO
     */
    private static final MentoringDemandDAO INSTANCE = AbstractDAOFactory.getInstance().getMentoringDemandDAO();

    protected MentoringDemandDAO() {}

    /**
     * Get the MentoringDemandDAO instance
     * @return MentoringDemandDAO instance
     */
    public static MentoringDemandDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Inserts a new mentoring demand
     * @param mentoringDemand the corresponding demand
     */
    public abstract void create(MentoringDemand mentoringDemand);

    /**
     * Updates the description of a mentoring demand
     * @param mentoringDemand the corresponding demand
     */
    public abstract void updateDescription(MentoringDemand mentoringDemand);

    /**
     * Deletes a mentoring demand
     * @param mentoringDemand the corresponding demand
     */
    public abstract void delete(MentoringDemand mentoringDemand);

    /**
     * Returns a mentoring demand with the following id
     * @param id corresponding to the demand id
     * @return MentoringDemand object
     */
    public abstract MentoringDemand getMentoringDemand(int id);

    /**
     * Adds a participant to a demand
     * @param mentoringDemand the corresponding demand
     * @param participation Participation object representing participation of the user
     */
    public abstract void participate(MentoringDemand mentoringDemand, Participation participation);

    /**
     * Deletes an user participation from a demand
     * @param demand the corresponding demand
     * @param user User, the user which wants to delete his participation
     */
    public abstract void suppressParticipation(MentoringDemand demand, User user);

    /**
     * Removes an user participation at a schedule from a demand
     * @param demand MentoringDemand to remove the participation
     * @param user User who wants to remove one of his participation
     * @param schedule Schedule representing the schedule where the user wants to remove his participation
     */
    public abstract void quitSchedule(MentoringDemand demand, User user, Schedule schedule);

    /**
     * Creates a schedule for a demand
     * @param demand the corresponding demand
     * @param schedule the schedule to add
     */
    public abstract void addSchedule(MentoringDemand demand, Schedule schedule);

    /**
     * Removes a schedule for a demand
     * @param demand the corresponding demand
     * @param schedule the schedule to remove
     */
    public abstract void removeSchedule(MentoringDemand demand, Schedule schedule);

    /**
     * Returns the number of schedules of a mentoring demand
     * @param demand the corresponding demand
     * @return int, number of schedules
     */
    public abstract int getNumberOfSchedules(MentoringDemand demand);

    /**
     * Returns all the mentoring demands
     * @return List of all mentoring demands
     */
    public abstract List<MentoringDemand> getMentoringDemands();

    /**
     * Returns all the mentoring demands corresponding to a department
     * @param department Department object representing the department to search on
     * @return List of all mentoring demands for a department
     */
    public abstract List<MentoringDemand> getMentoringDemands(Department department);

}
