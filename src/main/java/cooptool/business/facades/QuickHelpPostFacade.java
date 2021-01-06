package cooptool.business.facades;

import cooptool.models.daos.persistent.QuickHelpPostDAO;
import cooptool.models.objects.*;

import java.time.LocalDateTime;
import java.util.List;


public class QuickHelpPostFacade {

    private static final QuickHelpPostFacade INSTANCE;
    private QuickHelpPostDAO quickHelpPostDAO = QuickHelpPostDAO.getInstance();
    private User currentUser = UserFacade.getInstance().getCurrentUser();
    private PostFacade postFacade = PostFacade.getInstance();

    static{
        INSTANCE = new QuickHelpPostFacade();
    }

    private QuickHelpPostFacade(){}

    public static QuickHelpPostFacade getInstance(){
        return INSTANCE;
    }

    public void create(String description, Subject subject, User user){
        QuickHelpPost quickHelpPost =
                new QuickHelpPost(
                        -1,
                        subject,
                        description,
                        user,
                        LocalDateTime.now()
                );
        quickHelpPostDAO.create(quickHelpPost);
    }

    public void delete(QuickHelpPost qhp){
        quickHelpPostDAO.delete(qhp);
    }

    public List<QuickHelpPost> getQuickHelpPosts(Department department){
        List<QuickHelpPost> list = null;
        UserRole userRole = currentUser.getRole();
        if(userRole instanceof StudentRole){
            list = quickHelpPostDAO.getPartialQHP(((StudentRole) userRole).getDepartment());
        }
        return list;
    }

    public List<QuickHelpPost> getMyQuickHelpPosts() {
        List<QuickHelpPost> list = null;
        UserRole userRole = currentUser.getRole();
        if(userRole instanceof StudentRole){
            list = quickHelpPostDAO.getMyQHP(currentUser, ((StudentRole) userRole).getDepartment());
        }
        return list;
    }

    public List<QuickHelpPost> getQHPByAbbreviation(String abbreviation, int year) {
        List<QuickHelpPost> list = null;
        UserRole userRole = currentUser.getRole();
        if(!(userRole instanceof StudentRole)){
            list = quickHelpPostDAO.getQHPByAbbreviation(abbreviation, year);
        }
        return list;
    }

    public QuickHelpPost getQuickHelpPost(int id) {
        QuickHelpPost qhp = quickHelpPostDAO.getQuickHelpPost(id);
        postFacade.getComments(qhp);
        return qhp;
    }

    public boolean isCurrentUserCreatorOfQHP(QuickHelpPost qhp) {
        return currentUser.getId() == qhp.getCreator().getId();
    }

    public void updateDescription(QuickHelpPost qhp,String updatedDesc){
        qhp.setDescription(updatedDesc);
        quickHelpPostDAO.updateDescription(qhp);
    }
}
