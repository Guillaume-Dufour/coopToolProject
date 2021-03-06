package cooptool.business.facades;

import cooptool.exceptions.*;
import cooptool.models.daos.persistent.UserDAO;
import cooptool.models.objects.Department;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.User;
import cooptool.utils.BCrypt;
import cooptool.utils.Mail;

import javax.naming.Name;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Singleton used to hide the implementation of the database calls to the controller <br>
 * The UserFacade is in charge of all tasks related to our user management
 */
public class UserFacade {

    private static class LazyHolder {
        static final UserFacade INSTANCE = new UserFacade();
    }

    /**
     * Attribute to stock the user logged in the application
     */
    private User currentUser;

    /**
     * Attribute to access to the UserDAO methods
     */
    private final UserDAO userDAO = UserDAO.getInstance();

    /**
     * Attribute to stock the pattern of a valid mail address
     */
    private static final Pattern patternMail = Pattern.compile("^[a-z]+-?[a-z]+\\.[a-z]+-?[a-z]+[0-9]{0,2}@etu\\.umontpellier\\.fr$");

    /**
     * Attribute to stock the pattern of a valid mail address
     */
    private static final Pattern patternName = Pattern.compile("^[a-z,A-Z]+$");

    /**
     * Attribute to stock the potential mail address
     */
    private static Matcher matcher;

    /**
     * Attribute used to create a new password randomly
     */
    private String patternMdp = "abcdefghijklmnopqrstuvwxyz1234567890";

    private UserFacade() {
    }

    /**
     * Get the UserFacade instance
     *
     * @return UserFacade instance
     */
    public static UserFacade getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Function used to login an user using his credentials <br>
     * It asks the database his information and then creates and set the current user
     *
     * @param mail     Mail of the user who wants to logged in
     * @param password Password entered
     * @throws MailNotFound      : if there is no match
     * @throws UnmatchedPassword : if the password does not corresponds to the mail
     */
    public void login(String mail, String password) throws MailNotFound, UnmatchedPassword {
        User user = userDAO.findUserByMail(mail);
        if (user == null) {
            throw new MailNotFound();
        }
        if (!checkPassword(user, password)) {
            throw new UnmatchedPassword();
        }
        currentUser = user;
    }

    /**
     * Function used to register an user using the provided information <br>
     * It verifies the format of the information and then creates and save a new user
     *
     * @param firstName         First name of the user
     * @param lastName          Last name of the user
     * @param mail              Mail of the user
     * @param department        Department of the user
     * @param password          Password of the user
     * @param confirmedPassword Confirmed password of the user
     * @throws MailAlreadyExists    : if the mail is already in the database
     * @throws MailNotConformed     : if the mail is not conformed with a university mail
     * @throws PasswordNotConformed : if the password is not conformed
     * @throws UnmatchedPassword    : if the password doesn't match with de confirmedPassword
     * @throws NameNotConformed     : if the first name or last name are not conformed
     */
    public void register(String firstName, String lastName, String mail,
                         Department department, String password, String confirmedPassword)
            throws MailAlreadyExists, MailNotConformed, PasswordNotConformed, UnmatchedPassword, NameNotConformed {

        checkUserInformation(firstName, lastName, mail);
        checkPasswordInformation(password, confirmedPassword);
        User user = userDAO.findUserByMail(mail);
        if (user != null) {
            throw new MailAlreadyExists();
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        user = new User(mail, hashedPassword,
                new StudentRole(firstName, lastName, "", department), 0);
        boolean res = userDAO.create(user);

    }

    private void checkUserInformation (String firstName, String lastName, String mail)
            throws MailNotConformed, NameNotConformed {
        matcher = patternMail.matcher(mail);
        if (!matcher.find()) {
            throw new MailNotConformed();
        }
        matcher = patternName.matcher(firstName);
        if (!matcher.find()){
            throw new NameNotConformed();
        }
        matcher = patternName.matcher(lastName);
        if (!matcher.find()){
            throw new NameNotConformed();
        }
    }

    private void checkPasswordInformation (String password, String confirmedPassword)
            throws PasswordNotConformed, UnmatchedPassword {
        if (!password.equals(confirmedPassword)) {
            throw new UnmatchedPassword();
        }
        if (password.length() < 8) {
            throw new PasswordNotConformed();
        }
    }

    /**
     * Ask the list of all the student belonging to the provided department
     *
     * @param department Department we want the users
     * @return a list of the students belonging to the provided department
     */
    public List<User> findStudentByDepartment(Department department) {
        return userDAO.findUserByDepartment(department);
    }

    /**
     * Create a new password for the user corresponding to the provided mail <br>
     * Send the new password by mail to the concerned user <br>
     * Save the new password
     *
     * @param mail Mail we want to send a mail
     */
    public void sendValidationCode(String mail) {
        User user = userDAO.findUserByMail(mail);
        Random r = new Random();
        int validationCode = r.nextInt(9999);
        Mail.sendMail("code de validation COOPTOOL",
                "voici votre code de validation pour votre compte CoopTool :" +
                        validationCode,
                user.getMail());
        userDAO.createValidationCode(user.getId(), validationCode);
    }

    /**
     * Check if the code entered by the user corresponds to the user's validation code
     *
     * @param testedCode Verification code
     * @return True if the testedCode is the user's validation code, False otherwise
     */
    public boolean checkValidationCode(int testedCode) {
        int code = userDAO.getCodeByUser(currentUser.getId());
        return code == testedCode;
    }

    /**
     * Delete the account of the user
     * Disconnect the user if he deleted his own account
     *
     * @param user User we want to delete the account
     */
    public void deleteAccount(User user) {
        userDAO.delete(user);
        if (user.equals(currentUser)) {
            currentUser = null;
        }
    }

    /**
     * Update a user's account with the provided information
     *
     * @param firstName   New first name of the student
     * @param lastName    New last name of the student
     * @param department  New department of the student
     * @param description New description of the student
     * @throws MailNotConformed if the mail is not conformed
     * @throws NameNotConformed if the first name or last name are not conformed
     */
    public void updateAccount(String firstName, String lastName, Department department, String description) throws MailNotConformed, NameNotConformed {
        checkUserInformation(firstName, lastName, currentUser.getMail());
        User user = new User(currentUser.getId(), currentUser.getMail(), currentUser.getPassword(), new StudentRole(
                firstName, lastName, description, department
        ), 1);
        userDAO.update(user);
        currentUser = user;
    }

    /**
     * Update the user's password with the provided password
     *
     * @param oldPassword          Old password of the user
     * @param newPassword          New password of the user
     * @param newConfirmedPassword New confirmed password of the user
     * @throws UnmatchedPassword    : if the old password doesn't match with the current password or if newPassword doesn't match with the newConfirmedPassword
     * @throws PasswordNotConformed : if the password is not confirmed
     */
    public void updatePassword(String oldPassword, String newPassword, String newConfirmedPassword) throws UnmatchedPassword, PasswordNotConformed {
        if (checkPassword(currentUser, oldPassword)) {
            checkPasswordInformation(newPassword, newConfirmedPassword);
            String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            User user = new User(currentUser.getId(), currentUser.getMail(), password, currentUser.getRole(), 1);
            userDAO.updatePassword(user);
            currentUser = user;
        } else {
            throw new UnmatchedPassword();
        }
    }

    /**
     * Create a new password for the user corresponding to the provided mail address
     * Update the password of the user with this new password
     * Send a mail to the user containing the new password
     *
     * @param mail Mail we want to send the new password
     * @throws MailNotFound : if the mail doesn't exist
     */
    public void forgotPassword(String mail) throws MailNotFound {
        User user = userDAO.findUserByMail(mail);
        if (user == null) {
            throw new MailNotFound();
        }
        Random r = new Random();
        StringBuilder password = new StringBuilder();
        while (!(password.length() > 8)) {
            int random = r.nextInt(37);
            password.append(patternMdp.charAt(random));
        }
        String hashedPassword = BCrypt.hashpw(password.toString(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userDAO.updatePassword(user);
        Mail.sendMail("nouveau mot de passe CoopTool",
                "voici votre nouveau mot de passe " + password,
                user.getMail());
    }

    /**
     * Update the validation state of a user <br>
     * Delete the used validation code
     */
    public void updateValidation() {
        userDAO.updateValidation(currentUser.getId());
        userDAO.deleteCodeByUser(currentUser.getId());
    }

    /**
     * Disconnect a user
     */
    public void disconnect() {
        currentUser = null;
    }

    /**
     * Get the current user of the application
     *
     * @return Current user of the application
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Check if the user logged is a student
     *
     * @return True if the user logged is a student, False otherwise
     */
    public boolean isCurrentUserStudent() {
        return currentUser.getRole() instanceof StudentRole;
    }

    /**
     * Check if the provided password correspond to the user password
     *
     * @param user     User of the database
     * @param password Password to check
     * @return True if the provided password correspond to the user password, False otherwise
     */
    public boolean checkPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPassword());
    }

}
