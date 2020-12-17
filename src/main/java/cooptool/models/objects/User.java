package cooptool.models.objects;

import cooptool.BCrypt.BCrypt;

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

    public User(int id, String mail, String password, UserRole role) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public User(String mail, String password, UserRole role) {
        this(0,mail, password, role);
    }

    /**
     * Role of the user
     */
    private UserRole role;

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword(){ return password; }

    public UserRole getRole() {
        return role;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
