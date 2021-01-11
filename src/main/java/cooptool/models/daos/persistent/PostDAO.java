package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Comment;
import cooptool.models.objects.Post;
import cooptool.models.objects.User;

import java.util.List;

/**
 * PostDAO class
 */
public abstract class PostDAO {

    public static final int MENTORING_DEMAND = 0;
    public static final int QUICK_HELP_POST = 1;

    /**
     * Instance of the PostDAO
     */
    public static final PostDAO INSTANCE = AbstractDAOFactory.getInstance().getPostDAO();

    protected PostDAO() {}

    /**
     * Get the PostDAO instance
     * @return PostDAO instance
     */
    public static PostDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Return a list of posts for the user in parameter
     * @param user User we want the posts
     * @return List of posts for a user
     */
    public abstract List<Post> findPostByUser(User user);

    /**
     * Delete the post in parameter from the browsing history of the user in parameter
     * @param user User we want to remove the post from the browsing history
     * @param post Post we want to remove from the browsing history
     * @return True if the deletion is successful, false otherwise
     */
    public abstract boolean deleteOneFromBrowsingHistory(User user, Post post);

    /**
     * Delete all the posts from the browsing history of the user in parameter
     * @param user User we want to delete the whole browsing history
     * @return True if the deletion is successful, false otherwise
     */
    public abstract boolean deleteAllFromBrowsingHistory(User user);

    /**
     * Create a new comment in the database
     * @param comment Comment we want to insert
     * @param post Post of the comment
     */
    public abstract void comment(Comment comment, Post post);

    /**
     * Get all commends for the post in parameter and set the comments in this post
     * @param post Post we want to get the comments
     */
    public abstract void getComments(Post post);

    /**
     * Delete the comment in parameter
     * @param comment Comment we want to delete
     * @return True if the deletion is successful, false otherwise
     */
    public abstract boolean deleteComment(Comment comment);
}
