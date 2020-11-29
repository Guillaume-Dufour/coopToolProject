import models.objects.User;

/**
 * Singleton
 * The session stores the information of user
 */
public class Session {

    /**
     * Session of the user
     */
    private static Session session = null;

    /**
     * User who is connected the session
     */
    private User user;

    private Session() {
        // TO DO
    }

    /**
     * Get the instance of the session used.
     * @return Instance of the session
     */
    public static Session getInstance() {

        // TO DO

        return session;
    }

    public User getUser() {
        return null;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
