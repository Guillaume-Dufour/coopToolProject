package cooptool.business.facades;

import cooptool.models.daos.SubjectDAO;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;

import java.util.Arrays;
import java.util.List;

public class SubjectFacade {

    private static final SubjectFacade INSTANCE;
    private final SubjectDAO subjectDAO = SubjectDAO.getInstance();

    static {
        INSTANCE = new SubjectFacade();
    }

    private SubjectFacade() {

    }

    public static SubjectFacade getInstance() {
        return INSTANCE;
    }

    public List<Subject> getSubjectsByDepartment(Department department) {
        return subjectDAO.getSubjectsByDepartment(department);
    }
}
