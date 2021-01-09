package cooptool.business.facades;

import cooptool.exceptions.SubjectNotConformed;
import cooptool.models.daos.persistent.SubjectDAO;
import cooptool.models.objects.*;

import java.util.List;

public class SubjectFacade {

    private static final SubjectFacade INSTANCE;
    private final SubjectDAO subjectDAO = SubjectDAO.getInstance();
    private final User currentUser = UserFacade.getInstance().getCurrentUser();

    static {
        INSTANCE = new SubjectFacade();
    }

    private SubjectFacade() {}

    public static SubjectFacade getInstance() {
        return INSTANCE;
    }

    public List<Subject> getSubjectsByDepartment(Department department) {
        return subjectDAO.getSubjectsByDepartment(department);
    }

    public void create(String name, Department department) throws SubjectNotConformed {

        if (name.length() <= 50 && department != null) {
            Subject subject = new Subject(name, department);

            subjectDAO.create(subject);
        }
        else {
            throw new SubjectNotConformed();
        }
    }

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

    public void updateAvailability(Subject subject) {
        subjectDAO.update(subject);
    }

    /**
     * Returns the subjects of the users promotion
     * @return Either the subject of the user's promotion if he's student
     *         If he's not returns all the existing subjects
     */
    public List<Subject> getPromotionSubjects(){
        UserRole role = currentUser.getRole();
        if(role instanceof StudentRole){
            return subjectDAO.getSubjectsByPromotion(((StudentRole) role).getDepartment().getAbbreviation());
        }
        else{
            return subjectDAO.getAllSubjects();
        }
    }
}
