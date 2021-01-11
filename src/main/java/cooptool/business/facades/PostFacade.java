package cooptool.business.facades;

import cooptool.exceptions.CommentFormatException;
import cooptool.models.daos.persistent.PostDAO;
import cooptool.models.objects.Comment;
import cooptool.models.objects.Post;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * PostFacade class
 */
public class PostFacade {

    /**
     * PostFacade instance
     */
    private static final PostFacade INSTANCE;

    /**
     * Attribute to access to the PostDAO methods
     */
    private final PostDAO postDAO = PostDAO.getInstance();

    /**
     * Current user of the application
     */
    private final User currentUser = UserFacade.getInstance().getCurrentUser();

    static {
        INSTANCE = new PostFacade();
    }

    /**
     * Get the PostFacade instance
     * @return PostFacade instance
     */
    public static PostFacade getInstance() {
        return INSTANCE;
    }

    /**
     * Get the list of posts of the browsing history of the user in parameter
     * @param user User we want the browsing history
     * @return List of posts of the browsing history
     */
    public List<Post> getBrowsingHistory(User user){
        return postDAO.findPostByUser(user);
    }

    /**
     * Delete the post in parameter from the user's browsing history
     * @param user User we want to delete the post in browsing history
     * @param post Post we want to delete in the browsing history
     * @return True is the deleting is successful, false otherwise
     */
    public boolean deleteOneFromBrowsingHistory(User user, Post post) {
        return postDAO.deleteOneFromBrowsingHistory(user, post);
    }

    /**
     * Delete of the browsing history of the user in parameter
     * @param user User we want to delete the browsing history
     * @return True is the deleting is successful, false otherwise
     */
    public boolean deleteAllBrowsingHistory(User user) {
        return postDAO.deleteAllFromBrowsingHistory(user);
    }

    /**
     * Add a comment to the post in parameter
     * @param content Content of the comment
     * @param post Post we want to add a comment
     * @throws CommentFormatException If the format of the comment is not conformed
     */
    public void comment(String content,Post post) throws CommentFormatException {
        if (content.length() < 5) {
            throw new CommentFormatException("Comment too short");
        }
        else if (content.length() > 50) {
            throw new CommentFormatException("Comment too long");
        }
        else {
            postDAO.comment(new Comment(-1,content, LocalDateTime.now(),currentUser),post);
        }
    }

    /**
     * Get comments of the post in parameter and add them in the post
     * @param post Post we want to add its comments
     */
    public void getComments(Post post){
        postDAO.getComments(post);
    }

    /**
     * Delete the comment in parameter
     * @param comment Comment we want to delete
     */
    public void deleteComment(Comment comment) {
        postDAO.deleteComment(comment);
    }

    /**
     * Filter the posts to get posts of current user
     * @param posts List of posts we want to filter
     * @param <T> Type of Post
     * @return List of posts of the current user
     */
    public <T extends Post> List<T> filterCurrentUserPosts(List<T> posts){
        List<T> filteredPosts = new LinkedList<>();
        for(T post : posts){
            if(post.getCreator().getId() == currentUser.getId()){
                filteredPosts.add(post);
            }
        }
        return filteredPosts;
    }

    /**
     * Filter the post to get posts of the subject in parameter
     * @param posts List of posts we want to filter
     * @param subject Subject given to filter
     * @param <T> Type of Post
     * @return List of the posts of the given subject
     */
    public <T extends Post> List<T> filterPostsPerSubject(List<T> posts, Subject subject){
        List<T> filteredPosts = new LinkedList<>();
        for(T post : posts){
            if(post.getSubject().getId() == subject.getId()){
                filteredPosts.add(post);
            }
        }
        return filteredPosts;
    }
}
