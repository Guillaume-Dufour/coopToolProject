package cooptool.business.facades;

import cooptool.models.daos.PostDAO;
import cooptool.models.objects.Post;
import cooptool.models.objects.User;

import java.util.ArrayList;
import java.util.List;

public class PostFacade {

    private static final PostFacade INSTANCE;
    private final PostDAO postDAO = PostDAO.getInstance();

    static {
        INSTANCE = new PostFacade();
    }

    public static PostFacade getInstance() {
        return INSTANCE;
    }

    public List<Post> getBrowsingHistory (User user){
        return new ArrayList<>();
    }

    public boolean deleteOneFromBrowsingHistory(User user, Post post) {
        return postDAO.deleteOneFromBrowsingHistory(user, post);
    }

    public boolean deleteAllBrowsingHistory(User user) {
        return postDAO.deleteAllFromBrowsingHistory(user);
    }
}
