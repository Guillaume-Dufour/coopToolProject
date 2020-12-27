package cooptool.business.facades;

import cooptool.models.objects.Post;
import cooptool.models.objects.User;

import java.util.ArrayList;
import java.util.List;

public class PostFacade {
    private static PostFacade postFacade = null;

    public static PostFacade getInstance() {
        if(postFacade == null) {
            postFacade = new PostFacade();
        }
        return postFacade;
    }

    public List<Post> getBrowsingHistory (User user){
        return new ArrayList<Post>();
    }
}
