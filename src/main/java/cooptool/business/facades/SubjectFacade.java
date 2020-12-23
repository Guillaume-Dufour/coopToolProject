package cooptool.business.facades;

import cooptool.exceptions.SubjectNotConformed;
import cooptool.models.daos.SubjectDAO;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;

import java.util.List;

public class SubjectFacade {

    private static final SubjectFacade INSTANCE;
    private final SubjectDAO subjectDAO = SubjectDAO.getInstance();

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
}
