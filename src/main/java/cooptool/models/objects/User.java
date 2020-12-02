package cooptool.models.objects;

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

    public User(int id, String mail, String password, UserRole role) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
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
        return this.password.equals(password);
    }
}
