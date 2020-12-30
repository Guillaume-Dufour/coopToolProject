package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.QuickHelpPost;

import java.util.List;

public abstract class QuickHelpPostDAO {

    private static class LazyHolder {
        static final QuickHelpPostDAO INSTANCE = AbstractDAOFactory.getInstance().getQuickHelpPostDAO();
    }

    public static QuickHelpPostDAO getInstance() {
        return LazyHolder.INSTANCE;
    }

    public abstract boolean create(QuickHelpPost quickHelpPost);

    public abstract void delete(QuickHelpPost quickHelpPost);

    public abstract QuickHelpPost getQuickHelpPost(int id);
}
