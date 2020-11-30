package cooptool.business.facades;

import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.AbstractDAOFactory;
import cooptool.models.FactoryType;
import cooptool.models.UserDAO;
import cooptool.models.objects.User;

public class UserFacade {

    private UserDAO userDAO;

    public UserFacade() {
        userDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_Factory).getUserDAO();
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

}
