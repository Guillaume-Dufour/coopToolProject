package cooptool.models.daos;

import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.Subject;

public abstract class QuickHelpPostDAO {

    private static final QuickHelpPostDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getQuickHelpPostDAO();
    }

    public static QuickHelpPostDAO getInstance() {
        return INSTANCE;
    }

    public abstract boolean create(QuickHelpPost quickHelpPost);

    public abstract void delete(QuickHelpPost quickHelpPost);

    public abstract QuickHelpPost getQuickHelpPost(int id);
}
