package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Department;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.User;

import java.util.List;

public abstract class QuickHelpPostDAO {

    public static final QuickHelpPostDAO INSTANCE = AbstractDAOFactory.getInstance().getQuickHelpPostDAO();

    protected QuickHelpPostDAO() {}

    /**
     * @return the instance of the QuickHelpPostDAO singleton
     */
    public static QuickHelpPostDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Create the quickHelpPost in the database
     * @param quickHelpPost the quick help post we want to create in the database
     * @return true if the creation succeed, false otherwise
     */
    public abstract boolean create(QuickHelpPost quickHelpPost);

    /**
     * Delete the quickHelpPost in the database
     * @param quickHelpPost the quickHelpPost to delete
     */
    public abstract void delete(QuickHelpPost quickHelpPost);

    /**
     * Method which get the quickHelpPost according the id in parameter
     * @param id of the quickHelpPost that we want get
     * @return the corresponding quickHelpPost
     */
    public abstract QuickHelpPost getQuickHelpPost(int id);

    /**
     * Method which get the list of quickHelpPosts according the department in parameter
     * @param department of quickHelpPosts we want to get
     * @return list of quickHelpPosts
     */
    public abstract List<QuickHelpPost> getPartialQHP(Department department);

    /**
     * Get a list of quickHelpPosts according the abbreviation and the year of promotion <br>
     * Method used in the admin side
     * @param abbreviation the string of the abbreviation name of a department
     * @param year integer which represents the year of a promotion
     * @return list of QuickHelpPosts
     */
    public abstract List<QuickHelpPost> getQHPByAbbreviation(String abbreviation, int year);

    /**
     * Method with request to update the description of a quickHelpPost
     * @param qhp the quickHelpPost whose description we want to update
     */
    public abstract void updateDescription(QuickHelpPost qhp);
}
