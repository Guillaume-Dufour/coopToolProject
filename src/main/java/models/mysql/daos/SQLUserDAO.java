package models.mysql.daos;

import models.mysql.UserDAO;
import models.objects.User;

public class SQLUserDAO extends UserDAO {


    @Override
    public User findUserByMail(String mail) {
        return null;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void udpate(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
