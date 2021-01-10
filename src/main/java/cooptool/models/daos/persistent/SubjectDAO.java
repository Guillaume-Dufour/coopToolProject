package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;

import java.util.List;

public abstract class SubjectDAO {

    private static final SubjectDAO INSTANCE = AbstractDAOFactory.getInstance().getSubjectDAO();

    public static SubjectDAO getInstance() {
        return INSTANCE;
    }

    public abstract boolean create(Subject subject);

    public abstract boolean update(Subject subject);

    public abstract List<Subject> getSubjectsByDepartment(Department department);

    public abstract List<Subject> getSubjectsByPromotion(String abbreviation);

    public abstract List<Subject> getAllSubjects();

    public abstract List<Subject> getAvailableSubjectsByDepartment(Department department);
}
