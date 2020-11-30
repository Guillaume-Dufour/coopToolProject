package cooptool.business.facades;

import cooptool.models.AbstractDAOFactory;
import cooptool.models.FactoryType;
import cooptool.models.UserDAO;
import cooptool.models.objects.User;

public class UserFacade {

    private UserDAO userDAO;

    public UserFacade() {
        userDAO = AbstractDAOFactory.getFactory(FactoryType.SQL_Factory).getUserDAO();
    }

    public User login(String mail, String password) {
        User user = userDAO.findUserByMail(mail);
        if(user == null) {
            //TODO : gérer l erreur
        }
        else if(!user.checkPassword(password)){
            //TODO : gérer l erreur
        }
        else {

        }
        return user;
    }

}
