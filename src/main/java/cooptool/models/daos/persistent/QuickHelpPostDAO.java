package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Department;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.User;

import java.util.List;

public abstract class QuickHelpPostDAO {

    public static final QuickHelpPostDAO INSTANCE = AbstractDAOFactory.getInstance().getQuickHelpPostDAO();

    protected QuickHelpPostDAO() {}

    public static QuickHelpPostDAO getInstance() {
        return INSTANCE;
    }

    public abstract boolean create(QuickHelpPost quickHelpPost);

    public abstract void delete(QuickHelpPost quickHelpPost);

    public abstract QuickHelpPost getQuickHelpPost(int id);

    public abstract List<QuickHelpPost> getPartialQHP(Department department);

    public abstract List<QuickHelpPost> getMyQHP(User user, Department department);

    public abstract List<QuickHelpPost> getQHPByAbbreviation(String abbreviation, int year);

    public abstract void updateDescription(QuickHelpPost qhp);
}
