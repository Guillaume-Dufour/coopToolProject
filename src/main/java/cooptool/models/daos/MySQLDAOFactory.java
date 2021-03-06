package cooptool.models.daos;

import cooptool.models.daos.persistent.*;

/**
 * Factory that creates the MySQL DAOs
 */
public class MySQLDAOFactory extends AbstractDAOFactory {

    protected MySQLDAOFactory() {
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }

    @Override
    public DepartmentDAO getDepartmentDAO() {
        return new MySQLDepartmentDAO();
    }

    @Override
    public SubjectDAO getSubjectDAO() {
        return new MySQLSubjectDAO();
    }

    @Override
    public PostDAO getPostDAO() {
        return new MySQLPostDAO();
    }

    @Override
    public MentoringDemandDAO getMentoringDemandDAO() {
        return new MySQLMentoringDemandDAO();
    }

    @Override
    public QuickHelpPostDAO getQuickHelpPostDAO() {
        return new MySQLQuickHelpPostDAO();
    }

    @Override
    public NotificationDAO getNotificationDAO() {
        return new MySQLNotificationDAO();
    }
}
