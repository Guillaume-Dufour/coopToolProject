package cooptool.models.daos;

public abstract class MentoringDemandDAO {

    private static final MentoringDemandDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getMentoringDemandDAO();
    }

    public static MentoringDemandDAO getInstance() {
        return INSTANCE;
    }


}
