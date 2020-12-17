package cooptool.models.daos;

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
        return null;
    }

    @Override
    public PostDAO getPostDAO() {
        return null;
    }

    @Override
    public MentoringDemandDAO getMentoringDemandDAO() {
        return new MySQLMentoringDemandDAO();
    }

    @Override
    public QuickHelpPostDAO getQuickHelpPostDAO() {
        return null;
    }
}
