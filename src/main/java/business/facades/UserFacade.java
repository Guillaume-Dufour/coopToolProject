package business.facades;

import models.AbstractDAOFactory;
import models.FactoryType;
import models.UserDAO;
import models.objects.User;

public class UserFacade {

    private UserDAO userDAO;

    public UserFacade() {
        userDAO = AbstractDAOFactory.getFactory(FactoryType.SQL_Factory).getUserDAO();
    }

    public User checkCredentials(String mail, String password) {
        return null;
    }

}
