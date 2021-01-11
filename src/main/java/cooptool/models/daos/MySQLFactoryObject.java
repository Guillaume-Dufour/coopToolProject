package cooptool.models.daos;

import cooptool.models.enumDatabase.*;
import cooptool.models.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * MySQLFactoryObject class
 */
public abstract class MySQLFactoryObject {

    /**
     * User table constants
     */
    public static final int ADMIN_ROLE = 0;
    public static final int STUDENT_ROLE = 1;

    /**
     * Post table constants
     */
    public static final int MENTORING_DEMAND = 0;
    public static final int QUICK_HELP_POST = 1;

    /**
     * Create a user from the resultSet fields
     * @param rs Query ResultSet
     * @return User created from the ResultSet
     */
    public static User createUser(ResultSet rs) {

        int typeUser = getValue(rs, UserTable.TYPE_USER);

        UserRole userRole = null;

        switch (typeUser) {
            case STUDENT_ROLE:
                userRole = new StudentRole(
                        getValue(rs, UserTable.FIRST_NAME_USER, String.class),
                        getValue(rs, UserTable.LAST_NAME_USER, String.class),
                        getValue(rs, UserTable.DESCRIPTION_USER, String.class),
                        createDepartment(rs)
                );
                break;
            case ADMIN_ROLE:
                userRole = new AdminRole();
                break;
            default:
        }

        return new User(
                getValue(rs, UserTable.ID_USER),
                getValue(rs, UserTable.MAIL_USER, String.class),
                getValue(rs, UserTable.PASSWORD_USER, String.class),
                userRole,
                getValue(rs, UserTable.VALIDATE)
        );
    }

    /**
     * Create a subject from the resultSet fields
     * @param rs Query ResultSet
     * @return Subject created from the ResultSet
     */
    public static Subject createSubject(ResultSet rs) {
        return new Subject(
                getValue(rs, SubjectTable.ID_SUBJECT),
                getValue(rs, SubjectTable.NAME_SUBJECT, String.class),
                getValue(rs, SubjectTable.AVAILABLE),
                createDepartment(rs)
        );
    }

    /**
     * Create a department from the resultSet fields
     * @param rs Query ResultSet
     * @return Department created from the ResultSet
     */
    public static Department createDepartment(ResultSet rs) {
        return new Department(
                getValue(rs, DepartmentTable.ID_DEPARTMENT),
                getValue(rs, DepartmentTable.NAME_DEPARTMENT, String.class),
                getValue(rs, DepartmentTable.YEAR_DEPARTMENT),
                getValue(rs, DepartmentTable.ABBREVIATION_DEPARTMENT, String.class),
                getValue(rs, DepartmentTable.AVAILABLE)
        );
    }

    /**
     * Create a mentoring demand from the resultSet fields
     * @param rs Query ResultSet
     * @return Mentoring demand created from the ResultSet
     */
    public static MentoringDemand createMentoringDemand(ResultSet rs) {
        return new MentoringDemand(
                getValue(rs, PostTable.ID_POST),
                createSubject(rs),
                getValue(rs, PostTable.DESCRIPTION_POST, String.class),
                getValue(rs, PostTable.DATE_POST, LocalDateTime.class),
                new ArrayList<>(),
                createUser(rs)
        );
    }

    /**
     * Create a notification from the resultSet fields
     * @param rs Query ResultSet
     * @return Notification created from the ResultSet
     */
    public static Notification createNotification(ResultSet rs) {
        return new Notification(
                getValue(rs, NotificationTable.ID_NOTIFICATION),
                createUser(rs),
                getValue(rs, NotificationTable.CONTENT_NOTIFICATION, String.class),
                getValue(rs, NotificationTable.DATE_CREATION_NOTIFICATION, LocalDateTime.class),
                getValue(rs, NotificationTable.ID_OBJECT_NOTIFICATION),
                NotificationType.getType(getValue(rs, NotificationTable.TYPE_NOTIFICATION)),
                getValue(rs, NotificationTable.IS_READ)
        );
    }

    /**
     * Create a quick help post from the resultSet fields
     * @param rs Query ResultSet
     * @return Quick help post created from the ResultSet
     */
    public static QuickHelpPost createQuickHelpPost(ResultSet rs) {
        return new QuickHelpPost(
                getValue(rs, PostTable.ID_POST),
                createSubject(rs),
                getValue(rs, PostTable.DESCRIPTION_POST, String.class),
                createUser(rs),
                getValue(rs, PostTable.DATE_POST, LocalDateTime.class)
        );
    }

    /**
     * Create a participation from the resultSet fields
     * @param rs Query ResultSet
     * @return Participation created from the ResultSet
     */
    public static Participation createParticipation(ResultSet rs) {
        return new Participation(
                createUser(rs),
                getValue(rs, ParticipationTable.ROLE_USER),
                new ArrayList<>()
        );
    }

    /**
     * Create a schedule from the resultSet fields
     * @param rs Query ResultSet
     * @return Schedule created from the ResultSet
     */
    public static Schedule createSchedule(ResultSet rs) {
        return new Schedule(
                getValue(rs, ScheduleTable.DATE_POST_SESSION, LocalDateTime.class),
                createUser(rs)
        );
    }

    /**
     * Create a post from the resultSet fields
     * @param rs Query ResultSet
     * @return Post created from the ResultSet
     */
    public static Post createPost(ResultSet rs) {

        int type = getValue(rs, PostTable.TYPE_POST);

        switch (type) {
            case MENTORING_DEMAND:
                return createMentoringDemand(rs);
            case QUICK_HELP_POST:
                return createQuickHelpPost(rs);
            default:
                return null;
        }
    }

    /**
     * Create a comment from the resultSet fields
     * @param rs Query ResultSet
     * @return Comment created from the ResultSet
     */
    public static Comment createComment(ResultSet rs) {
        return new Comment(
                getValue(rs, CommentTable.ID_COMMENT),
                getValue(rs, CommentTable.CONTENT_COMMENT, String.class),
                getValue(rs, CommentTable.DATE_COMMENT, LocalDateTime.class),
                createUser(rs)
        );
    }

    private static <T> T getValue(ResultSet rs, TableInterface attribut, Class<T> type) {
        try {
            return rs.getObject(attribut.toString(), type);
        } catch (SQLException e) {
            return null;
        }
    }

    private static int getValue(ResultSet rs, TableInterface attribut) {
        try {
            int value = rs.getInt(attribut.toString());

            return rs.wasNull() ? -1 : value;
        } catch (SQLException e) {
            return -1;
        }
    }
}