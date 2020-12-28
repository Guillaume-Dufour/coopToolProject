package cooptool.models.daos;

import cooptool.models.objects.*;

import java.util.ArrayList;
import java.util.List;

public abstract class MentoringDemandDAO {

    private static final MentoringDemandDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getMentoringDemandDAO();
    }

    public static MentoringDemandDAO getInstance() {
        return INSTANCE;
    }

    public abstract void create(MentoringDemand mentoringDemand);

    public abstract void updateDescription(MentoringDemand mentoringDemand);

    public abstract void delete(MentoringDemand mentoringDemand);

    public abstract MentoringDemand getMentoringDemand(int id);

    public abstract void participate(MentoringDemand mentoringDemand, Participation participation);

    public abstract void suppressParticipation(MentoringDemand demand, User user);

    public abstract void quitSchedule(MentoringDemand demand, User user, Schedule schedule);

    public abstract void addSchedule(MentoringDemand demand, Schedule schedule);

    public abstract void removeSchedule(MentoringDemand demand, Schedule schedule);

    public abstract List<MentoringDemand> getPartialMentoringDemands();

    public abstract List<MentoringDemand> getPartialMentoringDemands(User user);

    public abstract List<MentoringDemand> getPartialMentoringDemands(Department department);

    public abstract List<MentoringDemand> getPartialMentoringDemands(Subject subject);


}
