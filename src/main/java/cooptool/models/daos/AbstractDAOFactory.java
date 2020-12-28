package cooptool.models.daos;

public abstract class AbstractDAOFactory {

    private static class LazyHolder {
        static final AbstractDAOFactory INSTANCE = new MySQLDAOFactory();
    }

    public static AbstractDAOFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    public abstract UserDAO getUserDAO();

    public abstract DepartmentDAO getDepartmentDAO();

    public abstract SubjectDAO getSubjectDAO();

    public abstract PostDAO getPostDAO();

    public abstract MentoringDemandDAO getMentoringDemandDAO();

    public abstract QuickHelpPostDAO getQuickHelpPostDAO();

    public abstract NotificationDAO getNotificationDAO();


}
