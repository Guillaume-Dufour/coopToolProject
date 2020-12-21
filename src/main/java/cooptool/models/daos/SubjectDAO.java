package cooptool.models.daos;

import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;

import java.util.List;

public abstract class SubjectDAO {

    private static final SubjectDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getSubjectDAO();
    }

    public static SubjectDAO getInstance() {
        return INSTANCE;
    }

    public abstract List<Subject> getSubjectsByDepartment(Department department);

    public abstract List<Subject> getAvailableSubjectsByDepartment(Department department);
}
