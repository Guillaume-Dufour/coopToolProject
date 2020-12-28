package cooptool.models.daos;

public abstract class QuickHelpPostDAO {

    private static class LazyHolder {
        static final QuickHelpPostDAO INSTANCE = AbstractDAOFactory.getInstance().getQuickHelpPostDAO();
    }

    public static QuickHelpPostDAO getInstance() {
        return LazyHolder.INSTANCE;
    }

}
