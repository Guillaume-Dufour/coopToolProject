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

    /**
     * return the Id of the user
     * @return Id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * return the mail of the user
     * @return mail of the user
     */
    public String getMail() {
        return mail;
    }

    /**
     * return the password of the user
     * @return password of the user
     */
    public String getPassword(){ return password; }

    /**
     * return the role of the user
     * @return role of the user
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * return the validate state of the user
     * @return validate state of the user
     */
    public int getValidate() {
        return validate;
    }

    /**
     * check if the provided password correspond to the user password
     * @param password
     * @return True if the provided password correspond to the user password, False otherwise
     */
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

    /**
     * set a new id to the user
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * set a new mail to the user
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * set a new password to the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * set a new role to the user
     * @param role
     */
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
