package cooptool.business.facades;

import cooptool.exceptions.CommentFormatException;
import cooptool.models.daos.persistent.PostDAO;
import cooptool.models.objects.Comment;
import cooptool.models.objects.Post;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;
import javafx.geometry.Pos;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class PostFacade {

    private static final PostFacade INSTANCE;
    private final PostDAO postDAO = PostDAO.getInstance();
    private final User currentUser = UserFacade.getInstance().getCurrentUser();

    static {
        INSTANCE = new PostFacade();
    }

    public static PostFacade getInstance() {
        return INSTANCE;
    }

    public List<Post> getBrowsingHistory(User user){
        return postDAO.findPostByUser(user);
    }

    public boolean deleteOneFromBrowsingHistory(User user, Post post) {
        return postDAO.deleteOneFromBrowsingHistory(user, post);
    }

    public boolean deleteAllBrowsingHistory(User user) {
        return postDAO.deleteAllFromBrowsingHistory(user);
    }

    public void comment(String content,Post post) throws CommentFormatException {
        if(content.length()<5){
            throw new CommentFormatException("Comment too short");
        }
        else if(content.length()>50){
            throw new CommentFormatException("Comment too long");
        }
        else{
            postDAO.comment(new Comment(-1,content, LocalDateTime.now(),currentUser),post);
        }
    }

    public void getComments(Post post){
        postDAO.getComments(post);
    }

    public void deleteComment(Comment comment) {
        postDAO.deleteComment(comment);
    }

    public <T extends Post> List<T> filterCurrentUserPosts(List<T> posts){
        List<T> filteredPosts = new LinkedList<>();
        for(T post : posts){
            if(post.getCreator().getId() == currentUser.getId()){
                filteredPosts.add(post);
            }
        }
        return filteredPosts;
    }

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
