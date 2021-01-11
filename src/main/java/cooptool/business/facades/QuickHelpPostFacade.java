package cooptool.business.facades;

import cooptool.models.daos.persistent.QuickHelpPostDAO;
import cooptool.models.objects.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * QuickHelpPostFacade class
 */
public class QuickHelpPostFacade {

    /**
     * Attribute to stock the QuickHelpPostFacade singleton
     */
    private static final QuickHelpPostFacade INSTANCE;

    /**
     * Attribute to access to the QuickHelpPostDAO methods
     */
    private final QuickHelpPostDAO quickHelpPostDAO = QuickHelpPostDAO.getInstance();

    /**
     * Attribute to stock the user logged in the application
     */
    private final User currentUser = UserFacade.getInstance().getCurrentUser();

    /**
     * Attribute to access to the postFacade methods
     */
    private final PostFacade postFacade = PostFacade.getInstance();


    static{
        INSTANCE = new QuickHelpPostFacade();
    }

    private QuickHelpPostFacade() {}

    /**
     * @return the unique instance of the QuickHelpPostFacade singleton
     */
    public static QuickHelpPostFacade getInstance(){
        return INSTANCE;
    }

    /**
     * Method quich creates and saves a new quick help post
     * @param description the description of the quick help post
     * @param subject the subject of the quick help post
     * @param user the creator of the quick help post
     */
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

    /**
     * Method called by deleteButton
     * Delete a quick help post
     * @param qhp the quick help post to delete
     */
    public void delete(QuickHelpPost qhp){
        quickHelpPostDAO.delete(qhp);
    }

    /**
     * Method which get the quick help posts of the department of the current student
     * @return the list of quick help posts
     */
    public List<QuickHelpPost> getQuickHelpPosts(){
        List<QuickHelpPost> list = null;
        UserRole userRole = currentUser.getRole();
        if(userRole instanceof StudentRole){
            list = quickHelpPostDAO.getPartialQHP(((StudentRole) userRole).getDepartment());
        }
        return list;
    }

    /**
     * Method which get the list of quick help posts according a department abbreviation and a year
     * @param abbreviation Abbreviation of the department we want the quickHelpPosts
     * @param year Year of the department we want the quickHelpPosts
     * @return the corresponding list of quick help posts
     */
    public List<QuickHelpPost> getQHPByAbbreviation(String abbreviation, int year) {
        List<QuickHelpPost> list = null;
        UserRole userRole = currentUser.getRole();
        if(!(userRole instanceof StudentRole)){
            list = quickHelpPostDAO.getQHPByAbbreviation(abbreviation, year);
        }
        return list;
    }

    /**
     * Method to get a quick help post according its id
     * @param id ID of the quickHelpPost we want to get
     * @return the corresponding quick help post
     */
    public QuickHelpPost getQuickHelpPost(int id) {
        QuickHelpPost qhp = quickHelpPostDAO.getQuickHelpPost(id);
        postFacade.getComments(qhp);
        return qhp;
    }

    /**
     * Method to verify if the current student is the creator of a quick help post
     * @param qhp QuickHelpPost we want to see if it's one of the current user
     * @return a boolean, if it's true the current user is the creator of the quick help post, otherwise it's not
     */
    public boolean isCurrentUserCreatorOfQHP(QuickHelpPost qhp) {
        return currentUser.getId() == qhp.getCreator().getId();
    }

    /**
     * Method to update the description of the quick help post in parameter
     * @param qhp QuickHelpPost we want to update
     * @param updatedDesc the text of the description
     */
    public void updateDescription(QuickHelpPost qhp,String updatedDesc){
        qhp.setDescription(updatedDesc);
        quickHelpPostDAO.updateDescription(qhp);
    }
}
