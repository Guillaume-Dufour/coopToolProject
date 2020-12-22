package cooptool.models.daos;

import cooptool.models.objects.Department;
import cooptool.models.objects.User;

import java.util.List;

public abstract class UserDAO {

    private static final UserDAO INSTANCE;



    static {
        INSTANCE = AbstractDAOFactory.getInstance().getUserDAO();
    }

    public static UserDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Function called to get an user from the database using his mail
     * @param mail
     * @return User if there is a match for the mail
     * @return null if there is no match for that mail
     */
    public abstract User findUserByMail(String mail);
    public abstract List<User> findUserByDepartment(Department department);
    public abstract User find(int id);
    public abstract boolean update(User user);
    public abstract boolean updatePassword(User user);
    public abstract boolean updateValidation(int id);
    public abstract boolean delete(User user);
    public abstract boolean create(User user);
    public abstract boolean createValidationCode(int userId, int validationCode);
    public abstract int getCodeByUser(int id);
    public abstract boolean deleteCodeByUser(int id);

}
