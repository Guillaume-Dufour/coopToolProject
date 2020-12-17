package cooptool.models.daos;

public abstract class SubjectDAO {

    private static final SubjectDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getSubjectDAO();
    }
}
