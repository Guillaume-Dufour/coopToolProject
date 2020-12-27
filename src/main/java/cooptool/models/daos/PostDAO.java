package cooptool.models.daos;

import cooptool.models.objects.Post;
import cooptool.models.objects.User;

import java.util.List;

public abstract class PostDAO {

    public static final int MENTORING_DEMAND = 0;
    public static final int QUICK_HELP_POST = 1;
    private static final PostDAO INSTANCE;

    static {
        INSTANCE = AbstractDAOFactory.getInstance().getPostDAO();
    }

    public static PostDAO getInstance() {
        return INSTANCE;
    }

    public abstract List<Post> getAllPostsByUser(User user);
}
