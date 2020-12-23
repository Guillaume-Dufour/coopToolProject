package cooptool.models.daos;

import cooptool.models.objects.Department;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;

import java.util.ArrayList;

public abstract class MentoringDemandDAO {

    public static final int STUDENT = 0;
    public static final int TUTOR = 1;

    private static final MentoringDemandDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getMentoringDemandDAO();
    }

    public static MentoringDemandDAO getInstance() {
        return INSTANCE;
    }

    public abstract void create(MentoringDemand mentoringDemand);

    public abstract void update(MentoringDemand mentoringDemand);

    public abstract void delete(MentoringDemand mentoringDemand);

    public abstract MentoringDemand getMentoringDemand(int id);

    public abstract ArrayList<MentoringDemand> getMentoringDemands(User user);

    public abstract ArrayList<MentoringDemand> getMentoringDemands(Department department);

    public abstract ArrayList<MentoringDemand> getMentoringDemands(Department department, Subject subject, int numberOfDemands);


}
