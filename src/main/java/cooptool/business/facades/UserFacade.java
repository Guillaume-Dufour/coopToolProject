package cooptool.business.facades;

import cooptool.utils.BCrypt;
import cooptool.exceptions.*;
import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.daos.UserDAO;
import cooptool.models.objects.Department;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.User;
import cooptool.utils.Mail;

import java.util.List;
import java.util.Random;
import java.util.regex.*;

/**
 * Singleton used to hide the implementation of the database calls to the controller
 * The UserFacade is in charge of all tasks related to our user management
 */
public class UserFacade {

    private User currentUser;
    private final UserDAO userDAO;
    private static UserFacade userFacade = null;

    private static Pattern pattern = Pattern.compile("^[a-z]+-?[a-z]+\\.[a-z]+-?[a-z]+[0-9]{0,2}@etu\\.umontpellier\\.fr$");
    private static Matcher matcher;

    private UserFacade() {
        userDAO = AbstractDAOFactory.getInstance().getUserDAO();
    }

    public static UserFacade getInstance() {
        if(userFacade == null) {
            userFacade = new UserFacade();
        }
        return userFacade;
    }

    /**
     * Function used to login an user using his credentials
     * It asks the database his information and then creates and set the current user
     * @param mail
     * @param password
     * @throws MailNotFound : if there is no match
     * @throws UnmatchedPassword : if the password does not corresponds to the mail
     */
    public void login(String mail, String password) throws MailNotFound, UnmatchedPassword {
        User user = userDAO.findUserByMail(mail);
        if(user == null) {
            throw new MailNotFound();
        }
        if(!user.checkPassword(password)){
            throw new UnmatchedPassword();
        }
        currentUser = user;
    }

    public void register(String firstName, String lastName, String mail,
                         Department department, String password, String confirmedPassword)
    throws MailAlreadyExists, MailNotConformed, PasswordNotConformed, UnmatchedPassword {

        if (!password.equals(confirmedPassword)){ throw new UnmatchedPassword(); }
        matcher = pattern.matcher(mail);
        if (!matcher.find()){ throw new MailNotConformed(); }
        if (password.length() < 8){ throw new PasswordNotConformed(); }
        User user = userDAO.findUserByMail(mail);
        if (user != null){ throw new MailAlreadyExists(); }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        user = new User(mail, hashedPassword,
                new StudentRole(firstName, lastName, "", department), 0);
        boolean res = userDAO.create(user);

    }

    public List<User> findStudentByDepartment(Department department){
        return userDAO.findUserByDepartment(department);
    }

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

    public boolean checkValidationCode(int testedCode){
        System.out.println("je suis dans userFacade, tested code = " + testedCode);
        int code = userDAO.getCodeByUser(currentUser.getId());
        System.out.println("je suis dans userFacade, code = " + code);
        return code == testedCode;
    }

    public void deleteAccount(User user){
        userDAO.delete(user);
    }

    public void deleteAccount(){
        this.deleteAccount(getCurrentUser());
        currentUser = null;
    }

    public void updateAccount(String firstName, String lastName, Department department, String description){
        User user = new User(currentUser.getId(), currentUser.getMail(), currentUser.getPassword(), new StudentRole(
                firstName, lastName, description, department
        ), 1);
        System.out.println("je suis dans la facade");
        System.out.println(user);
        userDAO.update(user);
        currentUser = user;
    }

    public void updatePassword(String oldPassword, String newPassword, String newConfirmedPassword) throws UnmatchedPassword, PasswordNotConformed {
        if (newPassword.equals(newConfirmedPassword) && currentUser.checkPassword(oldPassword)){
            if (newPassword.length() >= 8){
                String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                User user = new User(currentUser.getId(), currentUser.getMail(), password , currentUser.getRole(), 1);
                userDAO.updatePassword(user);
                currentUser = user;
            } else {
                throw new PasswordNotConformed();
            }
        } else {
            throw new UnmatchedPassword();
        }
    }

    public void forgotPassword(String mail) throws MailNotFound {
        User user = userDAO.findUserByMail(mail);
        if(user == null) {
            throw new MailNotFound();
        }
        Random r = new Random();
        String password = "" + r.nextInt(99999999);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userDAO.updatePassword(user);
        Mail.sendMail("nouveau mot de passe CoopTool",
                "voici votre nouveau mot de passe " + password,
                user.getMail());
    }

    public void updateValidation() {
        userDAO.updateValidation(currentUser.getId());
        userDAO.deleteCodeByUser(currentUser.getId());
    }

    public void disconnect() {
        currentUser = null;
    }

    /**
     * @return the current User
     */
    public User getCurrentUser() {
        return currentUser;
    }


}
