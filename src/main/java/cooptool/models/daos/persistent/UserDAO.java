package cooptool.models.daos.persistent;

import cooptool.models.daos.AbstractDAOFactory;
import cooptool.models.objects.Department;
import cooptool.models.objects.User;

import java.util.List;

/**
 * UserDAO class
 */
public abstract class UserDAO {

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
     *
     * @param user
     * @return
     */
    public abstract boolean create(User user);

    /**
     *
     * @param user
     * @return
     */
    public abstract boolean update(User user);

    /**
     *
     * @param user
     * @return
     */
    public abstract boolean updatePassword(User user);

    /**
     *
     * @param id
     * @return
     */
    public abstract boolean updateValidation(int id);

    /**
     *
     * @param user
     * @return
     */
    public abstract boolean delete(User user);

    /**
     *
     * @param userId
     * @param validationCode
     * @return
     */
    public abstract boolean createValidationCode(int userId, int validationCode);

    /**
     *
     * @param id
     * @return
     */
    public abstract int getCodeByUser(int id);

    /**
     *
     * @param id
     * @return
     */
    public abstract boolean deleteCodeByUser(int id);
}
