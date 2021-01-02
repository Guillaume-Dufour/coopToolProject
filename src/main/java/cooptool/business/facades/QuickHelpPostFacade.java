package cooptool.business.facades;

import cooptool.models.daos.persistent.QuickHelpPostDAO;
import cooptool.models.objects.*;

import java.time.LocalDateTime;
import java.util.List;


public class QuickHelpPostFacade {

    private static final QuickHelpPostFacade INSTANCE;
    private QuickHelpPostDAO quickHelpPostDAO = QuickHelpPostDAO.getInstance();
    private User currentUser = UserFacade.getInstance().getCurrentUser();

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

    public List<QuickHelpPost> getQuickHelpPosts(){
        UserRole userRole = currentUser.getRole();
        if(userRole instanceof StudentRole){
            return quickHelpPostDAO.getPartialQHP(((StudentRole) userRole).getDepartment());
        }
        else{
            return quickHelpPostDAO.getPartialQHP();
        }
    }

    public List<QuickHelpPost> getMyQuickHelpPosts() {
        UserRole userRole = currentUser.getRole();
        if(userRole instanceof StudentRole){
            return quickHelpPostDAO.getPartialQHP(currentUser, ((StudentRole) userRole).getDepartment());
        }
        else{
            return quickHelpPostDAO.getPartialQHP();
        }
    }

    public QuickHelpPost getQuickHelpPost(int id) {
        return quickHelpPostDAO.getQuickHelpPost(id);
    }

    public boolean isCurrentUserCreatorOfQHP(QuickHelpPost qhp) {
        return currentUser.getId() == qhp.getCreator().getId();
    }

    public void updateDescription(QuickHelpPost qhp,String updatedDesc){
        qhp.setDescription(updatedDesc);
        quickHelpPostDAO.updateDescription(qhp);
    }
}
