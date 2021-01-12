package cooptool.business.facades;

import cooptool.exceptions.SubjectNotConformed;
import cooptool.models.daos.persistent.SubjectDAO;
import cooptool.models.objects.*;

import java.util.List;

/**
 * SubjectFacade class
 */
public class SubjectFacade {

    /**
     * SubjectFacade instance
     */
    private static final SubjectFacade INSTANCE;

    /**
     * Attribute to access the subjectDAO methods
     */
    private final SubjectDAO subjectDAO = SubjectDAO.getInstance();

    /**
     * Current user of the application
     */
    private final User currentUser = UserFacade.getInstance().getCurrentUser();

    static {
        INSTANCE = new SubjectFacade();
    }

    private SubjectFacade() {}

    /**
     * Get the SubjectFacade instance
     * @return SubjectFacade instance
     */
    public static SubjectFacade getInstance() {
        return INSTANCE;
    }

    /**
     * Get the list of subjects of the department in parameter
     * @param department Department we want the subjects
     * @return List of subjects of the department
     */
    public List<Subject> getSubjectsByDepartment(Department department) {
        return subjectDAO.getSubjectsByDepartment(department);
    }

    /**
     * Get the list of available subjects of the department in parameter
     * @param department Department we want the available subjects
     * @return List of available subjects of the department
     */
    public List<Subject> getAvailableSubjectsByDepartment(Department department) {
        return subjectDAO.getAvailableSubjectsByDepartment(department);
    }

    /**
     * Create a new subject with the parameters
     * @param name Name of the new subject
     * @param department Department of the new subject
     * @throws SubjectNotConformed If the name of the department or the department are not conformed
     */
    public void create(String name, Department department) throws SubjectNotConformed {

        if (name.length() <= 50 && department != null) {
            Subject subject = new Subject(name, department);

            subjectDAO.create(subject);
        }
        else {
            throw new SubjectNotConformed();
        }
    }

    /**
     * Update the subject in parameter with the parameters
     * @param subject Subject we want to update
     * @param name New name of the subject
     * @param department New department of the subject
     * @throws SubjectNotConformed If the name of the department or the department are not conformed
     */
    public void update(Subject subject, String name, Department department) throws SubjectNotConformed {

        if (name.length() <= 50 && department != null) {
            subject.setName(name);
            subject.setDepartment(department);

            subjectDAO.update(subject);
        }
        else {
            throw new SubjectNotConformed();
        }
    }

    /**
     * Update the availability of the department in parameter
     * @param subject Department we want to update the availability
     */
    public void updateAvailability(Subject subject) {
        subjectDAO.update(subject);
    }

    /**
     * Returns the subjects of the users promotion
     * @return Either the subject of the user's promotion if he's student
     *         If he's not returns all the existing subjects
     */
    public List<Subject> getPromotionSubjects() {

        UserRole role = currentUser.getRole();

        if (role instanceof StudentRole) {
            return subjectDAO.getSubjectsByPromotion(((StudentRole) role).getDepartment().getAbbreviation());
        }
        else {
            return subjectDAO.getAllSubjects();
        }
    }
}
