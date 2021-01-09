package cooptool.models.daos;

import cooptool.models.daos.persistent.*;

/**
 * AbstractDAOFactory singleton <br>
 * Create the factory that creates DAOs
 */
public abstract class AbstractDAOFactory {

    private static class LazyHolder {
        static final AbstractDAOFactory INSTANCE = new MySQLDAOFactory();
    }

    /**
     * Get the AbstractDAOFactory instance
     * @return AbstractDAOFactory of the application
     */
    public static AbstractDAOFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Get the UserDAO instance
     * @return UserDAO instance
     */
    public abstract UserDAO getUserDAO();

    /**
     * Get the DepartmentDAO instance
     * @return DepartmentDAO instance
     */
    public abstract DepartmentDAO getDepartmentDAO();

    /**
     * Get the SubjectDAO instance
     * @return SubjectDAO instance
     */
    public abstract SubjectDAO getSubjectDAO();

    /**
     * Get the PostDAO instance
     * @return PostDAO instance
     */
    public abstract PostDAO getPostDAO();

    /**
     * Get the MentoringDemandDAO instance
     * @return MentoringDemand instance
     */
    public abstract MentoringDemandDAO getMentoringDemandDAO();

    /**
     * Get the QuickHelpPostDAO instance
     * @return QuickHelpPostDAO instance
     */
    public abstract QuickHelpPostDAO getQuickHelpPostDAO();

    /**
     * Get the NotificationDAO instance
     * @return NotificationDAO instance
     */
    public abstract NotificationDAO getNotificationDAO();
}
