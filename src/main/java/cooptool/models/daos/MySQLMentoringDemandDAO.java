package cooptool.models.daos;

import cooptool.models.daos.persistent.MentoringDemandDAO;
import cooptool.models.daos.persistent.PostDAO;
import cooptool.models.enumDatabase.ParticipationTable;
import cooptool.models.enumDatabase.ScheduleTable;
import cooptool.models.objects.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MySQLMentoringDemandDAO class
 */
public class MySQLMentoringDemandDAO extends MentoringDemandDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLMentoringDemandDAO() {
        super();
    }

    @Override
    public void create(MentoringDemand mentoringDemand) {
        String query1 =
                "INSERT INTO post (description_post,date_post,type_post,id_user_creator,id_subject) " +
                        "VALUES (?,?,?,?,?)";
        String query2 =
                "INSERT INTO participation (id_user,id_post,date_post_session,role_user) " +
                        "VALUES (?,?,?,?)";
        String query3 =
                "INSERT INTO schedule (id_post,date_post_session,id_creator) " +
                        "VALUES (?,?,?)";

        PreparedStatement insertPostStatement;
        PreparedStatement insertParticipationStatement;
        PreparedStatement insertScheduleStatement;

        try {
            connection.setAutoCommit(false);
            insertPostStatement = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            insertPostStatement.setString(1, mentoringDemand.getDescription());
            insertPostStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            insertPostStatement.setInt(3, PostDAO.MENTORING_DEMAND);
            insertPostStatement.setInt(4, mentoringDemand.getCreator().getId());
            insertPostStatement.setInt(5, mentoringDemand.getSubject().getId());
            insertPostStatement.executeUpdate();

            ResultSet res = insertPostStatement.getGeneratedKeys();

            int postId = -1;

            if (res.next()) {
                postId = res.getInt(Statement.RETURN_GENERATED_KEYS);
            }

            Schedule initialSchedule = mentoringDemand.getSchedules().get(0);

            insertParticipationStatement = connection.prepareStatement(query2);
            insertParticipationStatement.setInt(1, mentoringDemand.getCreator().getId());
            insertParticipationStatement.setInt(2, postId);
            insertParticipationStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            insertParticipationStatement.setInt(4, MentoringDemand.STUDENT);

            insertParticipationStatement.executeUpdate();
            insertScheduleStatement = connection.prepareStatement(query3);
            insertScheduleStatement.setInt(1, postId);
            insertScheduleStatement.setTimestamp(2, Timestamp.valueOf(initialSchedule.getDateTime()));
            insertScheduleStatement.setInt(3, initialSchedule.getCreator().getId());

            insertScheduleStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateDescription(MentoringDemand mentoringDemand) {
        String query =
                "UPDATE post " +
                        "SET description_post = ? " +
                        "WHERE id_post = ?";
        try {
            PreparedStatement updateStatement = connection.prepareStatement(query);
            updateStatement.setString(1, mentoringDemand.getDescription());
            updateStatement.setInt(2, mentoringDemand.getId());
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(MentoringDemand mentoringDemand) {
        String query =
                "DELETE FROM post " +
                        "WHERE id_post = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(query);
            deletionStatement.setInt(1, mentoringDemand.getId());
            deletionStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public MentoringDemand getMentoringDemand(int id) {
        String query =
                "SELECT * " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "LEFT JOIN schedule on post.id_post = schedule.id_post " +
                        "JOIN user creator ON post.id_user_creator = creator.id_user " +
                        "LEFT JOIN user scheduleCreator ON schedule.id_creator = scheduleCreator.id_user " +
                        "JOIN department ON creator.id_department = department.id_department " +
                        "WHERE post.id_post = ? AND type_post = 0 ORDER BY date_post";

        MentoringDemand result = null;
        try {
            PreparedStatement getDemandStatement = connection.prepareStatement(query);
            getDemandStatement.setInt(1, id);
            ResultSet res = getDemandStatement.executeQuery();

            boolean firstLine = true;
            while (res.next()) {
                if (firstLine) {
                    result = MySQLFactoryObject.createMentoringDemand(res);
                    firstLine = false;
                }

                Timestamp scheduleTs = res.getTimestamp(ScheduleTable.DATE_POST_SESSION.toString());

                //Case where the post has no schedules
                if (scheduleTs != null) {
                    result.addSchedule(MySQLFactoryObject.createSchedule(res));
                }
            }

            addParticipationsToMentoringDemand(Objects.requireNonNull(result));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    private void addParticipationsToMentoringDemand(MentoringDemand demand) {
        String query =
                "SELECT * " +
                        "FROM participation " +
                        "NATURAL JOIN `user` " +
                        "WHERE id_post = ? " +
                        "ORDER BY id_user";
        PreparedStatement getParticipationStatement;
        try {
            getParticipationStatement = connection.prepareStatement(query);
            getParticipationStatement.setInt(1, demand.getId());
            ResultSet res = getParticipationStatement.executeQuery();

            int previousUserId = -1;

            while (res.next()) {

                int userId = res.getInt(ParticipationTable.ID_USER.toString());

                if (userId != previousUserId) {
                    demand.addParticipation(MySQLFactoryObject.createParticipation(res));
                    previousUserId = userId;
                }
                demand.getParticipationArray().get(demand.getParticipationArray().size() - 1).addSchedule(MySQLFactoryObject.createSchedule(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void participate(MentoringDemand mentoringDemand, Participation participation) {
        String query = "INSERT INTO participation (id_user,id_post,date_post_session,role_user) VALUES (?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            for (Schedule schedule : participation.getParticipationSchedules()) {
                PreparedStatement insertStatement;
                insertStatement = connection.prepareStatement(query);
                insertStatement.setInt(1, participation.getParticipant().getId());
                insertStatement.setInt(2, mentoringDemand.getId());
                insertStatement.setTimestamp(3, Timestamp.valueOf(schedule.getDateTime()));
                insertStatement.setInt(4, participation.getParticipationType());
                insertStatement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void suppressParticipation(MentoringDemand demand, User user) {
        String query =
                "DELETE FROM participation " +
                        "WHERE id_post = ? AND id_user = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(query);
            deletionStatement.setInt(1, demand.getId());
            deletionStatement.setInt(2, user.getId());
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeParticipation(MentoringDemand demand, Schedule schedule) {
        String query =
                "DELETE FROM participation " +
                        "WHERE id_post = ? AND date_post_session = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(query);
            deletionStatement.setInt(1, demand.getId());
            deletionStatement.setTimestamp(2, Timestamp.valueOf(schedule.getDateTime()));
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void quitSchedule(MentoringDemand demand, User user, Schedule schedule) {
        String query =
                "DELETE FROM participation " +
                        "WHERE id_post = ? AND id_user = ? AND date_post_session = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(query);
            deletionStatement.setInt(1, demand.getId());
            deletionStatement.setInt(2, user.getId());
            deletionStatement.setTimestamp(3, Timestamp.valueOf(schedule.getDateTime()));
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSchedule(MentoringDemand demand, Schedule schedule) {
        String query =
                "INSERT INTO schedule (id_post,date_post_session,id_creator) " +
                        "VALUES (?,?,?)";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(query);
            insertStatement.setInt(1, demand.getId());
            insertStatement.setTimestamp(2, Timestamp.valueOf(schedule.getDateTime()));
            insertStatement.setInt(3, schedule.getCreator().getId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSchedule(MentoringDemand demand, Schedule schedule) {
        String query =
                "DELETE FROM schedule WHERE id_post = ? AND id_creator = ? AND date_post_session = ?";
        try {
            removeParticipation(demand,schedule);
            removeParticipation(demand, schedule);
            PreparedStatement deletionStatement = connection.prepareStatement(query);
            deletionStatement.setInt(1, demand.getId());
            deletionStatement.setInt(2, schedule.getCreator().getId());
            deletionStatement.setTimestamp(3, Timestamp.valueOf(schedule.getDateTime()));
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfSchedules(MentoringDemand demand) {
        String query =
                "SELECT * " +
                        "FROM schedule " +
                        "WHERE id_post = ?";
        int result = 0;
        try {
            PreparedStatement selectStatement = connection.prepareStatement(query);
            selectStatement.setInt(1, demand.getId());
            ResultSet res = selectStatement.executeQuery();
            while (res.next()) {
                result += 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<MentoringDemand> getMentoringDemands() {
        String query =
                "SELECT * " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "LEFT JOIN schedule ON post.id_post = schedule.id_post " +
                        "JOIN user u1 ON post.id_user_creator = u1.id_user " +
                        "WHERE type_post = 0 ORDER BY date_post,post.id_post";
        List<MentoringDemand> result = new ArrayList<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet res = preparedStatement.executeQuery();
            int previousIdPost = -1;
            while (res.next()) {
                int idPost = res.getInt(1);
                if (idPost != previousIdPost) {
                    result.add(MySQLFactoryObject.createMentoringDemand(res));
                    previousIdPost = idPost;
                }
                Timestamp scheduleTs = res.getTimestamp(String.valueOf(ScheduleTable.DATE_POST_SESSION));
                if (scheduleTs != null) {
                    result.get(result.size() - 1).addSchedule(MySQLFactoryObject.createSchedule(res));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<MentoringDemand> getMentoringDemands(Department department) {
        String query =
                "SELECT * " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "LEFT JOIN schedule ON post.id_post = schedule.id_post " +
                        "JOIN user u1 ON post.id_user_creator = u1.id_user " +
                        "JOIN department ON u1.id_department = department.id_department " +
                        "WHERE department.abbreviation_department = ? AND type_post = 0 ORDER BY date_post,post.id_post";
        List<MentoringDemand> result = new ArrayList<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, department.getAbbreviation());

            ResultSet res = preparedStatement.executeQuery();
            int previousIdPost = -1;

            while (res.next()) {
                int idPost = res.getInt(1);
                if (idPost != previousIdPost) {
                    result.add(MySQLFactoryObject.createMentoringDemand(res));
                    previousIdPost = idPost;
                }
                Timestamp scheduleTs = res.getTimestamp(String.valueOf(ScheduleTable.DATE_POST_SESSION));
                if (scheduleTs != null) {
                    result.get(result.size() - 1).addSchedule(MySQLFactoryObject.createSchedule(res));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }
}
