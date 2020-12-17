package cooptool.models.daos;

public abstract class PostDAO {

    private static final PostDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getPostDAO();
    }

    public static PostDAO getInstance() {
        return INSTANCE;
    }
}
