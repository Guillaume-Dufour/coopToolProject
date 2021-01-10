package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Comment;
import cooptool.models.objects.Post;
import cooptool.models.objects.User;

import java.util.List;

public abstract class PostDAO {

    public static final int MENTORING_DEMAND = 0;
    public static final int QUICK_HELP_POST = 1;

    public static final PostDAO INSTANCE = AbstractDAOFactory.getInstance().getPostDAO();

    protected PostDAO() {}

    public static PostDAO getInstance() {
        return INSTANCE;
    }
    public abstract List<Post> findPostByUser (User user);
    public abstract boolean deleteOneFromBrowsingHistory (User user, Post post);
    public abstract boolean deleteAllFromBrowsingHistory (User user);
    public abstract void comment(Comment comment, Post post);
    public abstract void getComments(Post post);
    public abstract boolean deleteComment(Comment comment);
}
