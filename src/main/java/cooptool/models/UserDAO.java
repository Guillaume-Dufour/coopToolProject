package cooptool.models;

import cooptool.models.objects.User;

public abstract class UserDAO extends DAO<User> {
    /**
     * Function called to get an user from the database using his mail
     * @param mail
     * @return User if there is a match for the mail
     * @return null if there is no match for that mail
     */
    public abstract User findUserByMail(String mail);
}
