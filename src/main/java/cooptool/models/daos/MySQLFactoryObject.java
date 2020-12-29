package cooptool.models.daos;

import cooptool.models.enumDatabase.*;
import cooptool.models.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class MySQLFactoryObject {

    private static final int ADMIN_ROLE = 0;
    private static final int STUDENT_ROLE = 1;

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

    public static Subject createSubject(ResultSet rs) {
        return new Subject(
                getValue(rs, SubjectTable.ID_SUBJECT),
                getValue(rs, SubjectTable.NAME_SUBJECT, String.class),
                getValue(rs, SubjectTable.AVAILABLE),
                createDepartment(rs)
        );
    }

    public static Department createDepartment(ResultSet rs) {
        return new Department(
                getValue(rs, DepartmentTable.ID_DEPARTMENT),
                getValue(rs, DepartmentTable.NAME_DEPARTMENT, String.class),
                getValue(rs, DepartmentTable.YEAR_DEPARTMENT),
                getValue(rs, DepartmentTable.ABBREVIATION_DEPARTMENT, String.class),
                getValue(rs, DepartmentTable.AVAILABLE)
        );
    }

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

    public static Notification createNotification(ResultSet rs) {
        return new Notification(
                getValue(rs, NotificationTable.ID_NOTIFICATION),
                createUser(rs),
                getValue(rs, NotificationTable.CONTENT_NOTIFICATION, String.class),
                getValue(rs, NotificationTable.DATE_CREATION_NOTIFICATION, LocalDateTime.class),
                NotificationType.getType(getValue(rs, NotificationTable.TYPE_NOTIFICATION))
        );
    }

    public static QuickHelpPost createQuickHelpPost(ResultSet rs) {
        return null;
    }

    public static Participation createParticipation(ResultSet rs) {
        return new Participation(
                createUser(rs),
                getValue(rs, ParticipationTable.ROLE_USER),
                new ArrayList<>()
        );
    }

    public static Schedule createSchedule(ResultSet rs) {
        return new Schedule(
                getValue(rs, ScheduleTable.DATE_POST_SESSION, LocalDateTime.class),
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
