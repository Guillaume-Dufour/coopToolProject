package models;

import models.objects.User;

public abstract class UserDAO extends DAO<User> {

    public abstract User findUserByMail(String mail);
}
