package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Department;
import cooptool.models.objects.User;

import java.util.List;

/**
 * UserDAO class
 */
public abstract class UserDAO {

    /**
     * Instance of UserDAO
     */
    public static final UserDAO INSTANCE = AbstractDAOFactory.getInstance().getUserDAO();

    protected UserDAO() {}

    /**
     * Get the UserDAO instance
     * @return UserDAO instance
     */
    public static UserDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Function called to get an user from the database using his mail
     * @param mail Mail of the user
     * @return User if there is a match for the mail, null otherwise
     */
    public abstract User findUserByMail(String mail);

    /**
     * Return the department users
     * @param department Department of the users
     * @return List of users of the department in parameter
     */
    public abstract List<User> findUserByDepartment(Department department);

    /**
     * Function called to create a new user in the database from the provided user
     * @param user User to be added in the database
     * @return True if the creation is a success, False otherwise
     */
    public abstract boolean create(User user);

    /**
     * Function called to update a user in the database from the provided user
     * @param user User to be updated in the database
     * @return True if the update is a success, False otherwise
     */
    public abstract boolean update(User user);

    /**
     * Function called to update the user's password in the database from the provided user
     * @param user User to be updated in the database
     * @return True if the update is a success, False otherwise
     */
    public abstract boolean updatePassword(User user);

    /**
     * Function called to update the user's validation state in the database from the provided student's id
     * @param id Id of the concerned user
     * @return True if the update is a success, False otherwise
     */
    public abstract boolean updateValidation(int id);

    /**
     * Function called to delete a user from the database
     * @param user User to be deleted from the database
     * @return True if the deletion is a success, False otherwise
     */
    public abstract boolean delete(User user);

    /**
     * Function called to create a user's validation code in the database with the provided information
     * @param userId Id of the concerned user
     * @param validationCode Validation code
     * @return True if the creation is a success, False otherwise
     */
    public abstract boolean createValidationCode(int userId, int validationCode);

    /**
     * Return the validation code of the corresponding user
     * @param id Id of the concerned user
     * @return the validation code if the corresponding user
     */
    public abstract int getCodeByUser(int id);

    /**
     * Function called to delete the validation code of the corresponding user
     * @param id Id of the concerned user
     * @return True if the deletion is a success, False otherwise
     */
    public abstract boolean deleteCodeByUser(int id);
}
