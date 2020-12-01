package cooptool.business.facades;

import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.AbstractDAOFactory;
import cooptool.models.FactoryType;
import cooptool.models.UserDAO;
import cooptool.models.objects.User;

public class UserFacade {

    private User currentUser;
    private UserDAO userDAO;
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

    public User login(String mail, String password) throws MailNotFound, UnmatchedPassword {
        User user = userDAO.findUserByMail(mail);
        if(user == null) {
            throw new MailNotFound();
        }
        else if(!user.checkPassword(password)){
            throw  new UnmatchedPassword();
        }
        else {
            return user;
        }
    }

    public UserDAO getUserDAO(){
        return userDAO;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
