package cooptool;

import cooptool.models.objects.User;

/**
 * Singleton
 * The session stores the information of user
 */
public class Session {

    /**
     * cooptool.Session of the user
     */
    private static Session session = null;

    /**
     * User who is connected the session
     */
    private User user;

    private Session() {

    }

    /**
     * Get the instance of the session used.
     * @return Instance of the session
     */
    public static Session getInstance() {
        if(session == null) {
            session = new Session();
        }

        return session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
