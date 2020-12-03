package cooptool.business.facades;

import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.AbstractDAOFactory;
import cooptool.models.FactoryType;
import cooptool.models.UserDAO;
import cooptool.models.objects.User;

/**
 * Singleton used to hide the implementation of the database calls to the controller
 * The UserFacade is in charge of all tasks related to our user management
 */
public class UserFacade {

    private User currentUser;
    private final UserDAO userDAO;
    private static UserFacade userFacade = null;

    private UserFacade() {
        userDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_Factory).getUserDAO();
    }

    public static UserFacade getInstance() {
        if(userFacade == null) {
            userFacade = new UserFacade();
        }
        return userFacade;
    }

    /**
     * Function used to login an user using his credentials
     * It asks the database his information and then creates and set the current user
     * @param mail
     * @param password
     * @throws MailNotFound : if there is no match
     * @throws UnmatchedPassword : if the password does not corresponds to the mail
     */
    public void login(String mail, String password) throws MailNotFound, UnmatchedPassword {
        User user = userDAO.findUserByMail(mail);

        if(user == null) {
            throw new MailNotFound();
        }
        else if(!user.checkPassword(password)){
            throw new UnmatchedPassword();
        }
        else {
            currentUser = user;
        }
    }

    /**
     * @return the current User
     */
    public User getCurrentUser() {
        return currentUser;
    }

}
