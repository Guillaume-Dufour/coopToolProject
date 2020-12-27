package cooptool.models.daos;

public abstract class QuickHelpPostDAO {

    private static final QuickHelpPostDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getQuickHelpPostDAO();
    }

    public static QuickHelpPostDAO getInstance() {
        return INSTANCE;
    }

}
