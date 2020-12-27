package cooptool.models.daos;

import cooptool.models.objects.Post;
import cooptool.models.objects.User;
import javafx.geometry.Pos;

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
    public abstract List<Post> findPostByUser (User user);
    public abstract Post findPostById (int id);
    public abstract boolean update (Post post);
    public abstract boolean deleteOneFromBrowsingHistory (User user, Post post);
    public abstract boolean deleteAllFromBrowsingHistory (User user);
}
