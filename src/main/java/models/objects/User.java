package models.objects;

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
     * Password of the user
     */
    private String password;

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

    public boolean checkPassword(String password) {
        return false;
    }
}
