package models.objects;

import models.AbstractDAOFactory;
import models.FactoryType;

public class User {

    /**
     * ID of the user
     */
    private int id;

    /**
     * Mail of the user
     */
    private String mail;

    /**
     * Role of the user
     */
    private UserRole role;

    public User() {
        // TO DO
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public UserRole getRole() {
        return role;
    }

    public void get() {
        AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getFactory(FactoryType.E).getUserDAO().find(id);
    }
}
