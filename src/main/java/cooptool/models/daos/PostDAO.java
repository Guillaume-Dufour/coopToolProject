package cooptool.models.daos;

public abstract class PostDAO {

    public static final int MENTORING_DEMAND = 0;
    public static final int QUICK_HELP_POST = 1;
    private static final PostDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getPostDAO();
    }

    public static PostDAO getInstance() {
        return INSTANCE;
    }
}
