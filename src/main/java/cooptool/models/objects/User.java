package cooptool.models.objects;

import cooptool.utils.BCrypt;

import java.util.Objects;

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

    /**
     * Validate of the user
     */
    private int validate;

    public User(int id, String mail, String password, UserRole role, int validate) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.validate = validate;
    }

    public User(String mail, String password, UserRole role, int validate) {
        this(0,mail, password, role, validate);
    }

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

    public int getValidate() {
        return validate;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
