package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;

import java.util.List;

/**
 * SubjectDAO class
 */
public abstract class SubjectDAO {

    /**
     * Instance of SubjectDAO
     */
    private static final SubjectDAO INSTANCE = AbstractDAOFactory.getInstance().getSubjectDAO();

    protected SubjectDAO() {}

    /**
     * Get the SubjectDAO instance
     * @return SubjectDAO instance
     */
    public static SubjectDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Create the new subject in parameter in the database
     * @param subject Subject we want to insert
     * @return True if the insertion is successful, false otherwise
     */
    public abstract boolean create(Subject subject);

    /**
     * Update the subject in parameter in the database
     * @param subject Subject we want to upate
     * @return True if the update is successful, false otherwise
     */
    public abstract boolean update(Subject subject);

    /**
     * Return a list of subjects of the department in parameter
     * @param department Department of the subjects we want
     * @return List of subjects
     */
    public abstract List<Subject> getSubjectsByDepartment(Department department);

    /**
     * Return a list of subjects of the department abbreviation in parameter
     * @param abbreviation Department abbreviation of the subjects we want
     * @return List of subjects
     */
    public abstract List<Subject> getSubjectsByPromotion(String abbreviation);

    /**
     * Get all subjects from the database
     * @return List of subjects
     */
    public abstract List<Subject> getAllSubjects();

    /**
     * Return a list of available subjects of the department in parameter
     * @param department Department of the subjects we wanrt
     * @return List of available subjects
     */
    public abstract List<Subject> getAvailableSubjectsByDepartment(Department department);
}
