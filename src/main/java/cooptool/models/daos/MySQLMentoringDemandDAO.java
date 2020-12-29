package cooptool.models.daos;

import cooptool.models.enumDatabase.ScheduleTable;
import cooptool.models.objects.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySQLMentoringDemandDAO extends MentoringDemandDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLMentoringDemandDAO() {
        super();
    }

    @Override
    public void create(MentoringDemand mentoringDemand) {
        String statement =
                "INSERT INTO post (description_post,date_post,type_post,id_user_creator,id_subject) " +
                        "VALUES (?,?,?,?,?)";
        String statement2 =
                "INSERT INTO participation (id_user,id_post,date_post_session,role_user) " +
                    "VALUES (?,?,?,?)";
        String statement3 =
                "INSERT INTO schedule (id_post,date_post_session,creator_id) " +
                    "VALUES (?,?,?)";
        PreparedStatement insertPostStatement;
        PreparedStatement insertParticipationStatement;
        PreparedStatement insertScheduleStatement;
        try{
            connection.setAutoCommit(false);
            insertPostStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            insertPostStatement.setString(1,mentoringDemand.getDescription());
            insertPostStatement.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            insertPostStatement.setInt(3,PostDAO.MENTORING_DEMAND);
            insertPostStatement.setInt(4,mentoringDemand.getCreator().getId());
            insertPostStatement.setInt(5,mentoringDemand.getSubject().getId());
            insertPostStatement.executeUpdate();

            ResultSet res = insertPostStatement.getGeneratedKeys();
            int postId = -1;
            if(res.next()){
                postId = res.getInt(Statement.RETURN_GENERATED_KEYS);
            }
            Schedule initialSchedule = mentoringDemand.getSchedules().get(0);

            insertParticipationStatement = connection.prepareStatement(statement2);
            insertParticipationStatement.setInt(1,mentoringDemand.getCreator().getId());
            insertParticipationStatement.setInt(2,postId);
            insertParticipationStatement.setTimestamp(3,Timestamp.valueOf(LocalDateTime.now()));
            insertParticipationStatement.setInt(4,MentoringDemand.STUDENT);

            insertParticipationStatement.executeUpdate();

            insertScheduleStatement = connection.prepareStatement(statement3);
            insertScheduleStatement.setInt(1,postId);
            insertScheduleStatement.setTimestamp(2,Timestamp.valueOf(initialSchedule.getDateTime()));
            insertScheduleStatement.setInt(3,initialSchedule.getCreator().getId());

            insertScheduleStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateDescription(MentoringDemand mentoringDemand) {
        String statement =
                "UPDATE post " +
                        "SET description_post = ? " +
                        "WHERE id_post = ?";
        try {
            PreparedStatement updateStatement = connection.prepareStatement(statement);
            updateStatement.setString(1, mentoringDemand.getDescription());
            updateStatement.setInt(2, mentoringDemand.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(MentoringDemand mentoringDemand) {
        String statement =
                "DELETE FROM post " +
                "WHERE id_post = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(statement);
            deletionStatement.setInt(1,mentoringDemand.getId());
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MentoringDemand getMentoringDemand(int id) {
        String statement =
                        "SELECT post.id_post,description_post,date_post," +
                        "name_subject," +
                        "creator.id_user,creator.first_name_user,creator.last_name_user," +
                        "abbreviation_department,year_department," +
                        "date_post_session," +
                        "scheduleCreator.id_user,scheduleCreator.first_name_user,scheduleCreator.last_name_user " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "LEFT JOIN schedule on post.id_post = schedule.id_post " +
                        "JOIN user creator ON post.id_user_creator = creator.id_user " +
                        "LEFT JOIN user scheduleCreator ON schedule.creator_id = scheduleCreator.id_user " +
                        "JOIN department ON creator.id_department = department.id_department "+
                        "WHERE post.id_post = ? AND type_post = 0 ORDER BY date_post";
        MentoringDemand result = null;
        try{
            PreparedStatement getDemandStatement = connection.prepareStatement(statement);
            getDemandStatement.setInt(1,id);
            ResultSet res = getDemandStatement.executeQuery();

            boolean firstLine = true;
            while(res.next()){
                if(firstLine) {
                    int idPost = res.getInt(1);
                    String description = res.getString(2);
                    LocalDateTime creationDate = res.getTimestamp(3).toLocalDateTime();
                    String subjectName = res.getString(4);
                    int creatorId = res.getInt(5);
                    String creatorFirstName = res.getString(6);
                    String creatorLastName = res.getString(7);
                    String abbreviationDepartment = res.getString(8);
                    int yearDepartment = res.getInt(9);
                    ArrayList<Schedule> schedules = new ArrayList<>();
                    result = new MentoringDemand(
                            idPost,
                            new Subject(
                                    -1,
                                    subjectName,
                                    -1,
                                    null
                            ),
                            description,
                            creationDate,
                            schedules,
                            new User(
                                    creatorId,
                                    null,
                                    null,
                                    new StudentRole(
                                            creatorFirstName,
                                            creatorLastName,
                                            description,
                                            new Department(
                                                    -1,
                                                    null,
                                                    yearDepartment,
                                                    abbreviationDepartment,
                                                    -1
                                            )
                                    ),
                                    -1
                            )
                    );
                    firstLine = false;
                }
                Timestamp scheduleTs = res.getTimestamp(10);
                //Case where the post has no schedules
                if(scheduleTs != null){
                    LocalDateTime scheduleDate = scheduleTs.toLocalDateTime();
                    int scheduleCreatorId = res.getInt(11);
                    String scheduleCreatorFirstName = res.getString(12);
                    String scheduleCreatorLastName = res.getString(13);
                    StudentRole scheduleCreatorStudentRole = new StudentRole(
                            scheduleCreatorFirstName,
                            scheduleCreatorLastName,
                            null,
                            null
                    );
                    Schedule schedule = new Schedule(
                            scheduleDate,
                            new User(scheduleCreatorId,null,null,scheduleCreatorStudentRole,-1)
                    );
                    result.addSchedule(schedule);
                }
            }
            assert result != null;
            addParticipationsToMentoringDemand(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void addParticipationsToMentoringDemand(MentoringDemand demand){
        String statement =
                "SELECT id_user,first_name_user,last_name_user,date_post_session,role_user " +
                        "FROM participation NATURAL JOIN user " +
                        "WHERE id_post = ? " +
                        "ORDER BY id_user";
        PreparedStatement getParticipationStatement;
        try{
            getParticipationStatement = connection.prepareStatement(statement);
            getParticipationStatement.setInt(1,demand.getId());
            ResultSet res = getParticipationStatement.executeQuery();
            int previousUserId = -1;
            while(res.next()){
                int userId = res.getInt(1);
                if(userId != previousUserId){
                    String firstNameUser = res.getString(2);
                    String lastNameUser = res.getString(3);
                    int participationType = res.getInt(5);
                    ArrayList<Schedule> selectedSchedules = new ArrayList<>();
                    StudentRole role = new StudentRole(firstNameUser,lastNameUser,null,null);
                    demand.addParticipation(
                                new Participation(
                                        new User(userId,null,null,role,-1),
                                        participationType,
                                        selectedSchedules
                                )
                        );
                    previousUserId = userId;
                }
                LocalDateTime participationDate = res.getTimestamp(4).toLocalDateTime();
                demand.getParticipationArray().get(demand.getParticipationArray().size()-1).addSchedule(new Schedule(participationDate,null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void participate(MentoringDemand mentoringDemand, Participation participation) {
        String statement = "INSERT INTO participation (id_user,id_post,date_post_session,role_user) VALUES (?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            for(Schedule schedule : participation.getParticipationSchedules()){
                PreparedStatement insertStatement;
                insertStatement = connection.prepareStatement(statement);
                insertStatement.setInt(1,participation.getParticipant().getId());
                insertStatement.setInt(2,mentoringDemand.getId());
                insertStatement.setTimestamp(3,Timestamp.valueOf(schedule.getDateTime()));
                insertStatement.setInt(4,participation.getParticipationType());
                insertStatement.executeUpdate();
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void suppressParticipation(MentoringDemand demand, User user) {
        String statement =
                "DELETE FROM participation " +
                "WHERE id_post = ? AND id_user = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(statement);
            deletionStatement.setInt(1,demand.getId());
            deletionStatement.setInt(2,user.getId());
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeParticipation(MentoringDemand demand, Schedule schedule) {
        String statement =
                        "DELETE FROM participation " +
                        "WHERE id_post = ? AND date_post_session = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(statement);
            deletionStatement.setInt(1,demand.getId());
            deletionStatement.setTimestamp(2,Timestamp.valueOf(schedule.getDateTime()));
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void quitSchedule(MentoringDemand demand, User user, Schedule schedule) {
        String statement =
                "DELETE FROM participation " +
                "WHERE id_post = ? AND id_user = ? AND date_post_session = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(statement);
            deletionStatement.setInt(1,demand.getId());
            deletionStatement.setInt(2,user.getId());
            deletionStatement.setTimestamp(3,Timestamp.valueOf(schedule.getDateTime()));
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSchedule(MentoringDemand demand, Schedule schedule) {
        String statement =
                "INSERT INTO schedule (id_post,date_post_session,creator_id) " +
                "VALUES (?,?,?)";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(statement);
            insertStatement.setInt(1,demand.getId());
            insertStatement.setTimestamp(2,Timestamp.valueOf(schedule.getDateTime()));
            insertStatement.setInt(3,schedule.getCreator().getId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSchedule(MentoringDemand demand, Schedule schedule) {
        String statement =
                "DELETE FROM schedule WHERE id_post = ? AND creator_id = ? AND date_post_session = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(statement);
            deletionStatement.setInt(1,demand.getId());
            deletionStatement.setInt(2,schedule.getCreator().getId());
            deletionStatement.setTimestamp(3,Timestamp.valueOf(schedule.getDateTime()));
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfSchedules(MentoringDemand demand) {
        String statement =
                        "SELECT * " +
                        "FROM schedule " +
                        "WHERE id_post = ?";
        int result = 0;
        try {
            PreparedStatement selectStatement = connection.prepareStatement(statement);
            selectStatement.setInt(1,demand.getId());
            ResultSet res = selectStatement.executeQuery();
            while (res.next()){
                result += 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<MentoringDemand> getPartialMentoringDemands() {
        return null;
    }

    @Override
    public List<MentoringDemand> getPartialMentoringDemands(User user) {
        return null;
    }

    @Override
    public List<MentoringDemand> getPartialMentoringDemands(Department department) {
        String statement =
                        "SELECT * " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "LEFT JOIN schedule ON post.id_post = schedule.id_post " +
                        "JOIN user u1 ON post.id_user_creator = u1.id_user " +
                        "JOIN department ON u1.id_department = department.id_department "+
                        "WHERE department.abbreviation_department = ? AND type_post = 0 ORDER BY date_post,post.id_post";
        List<MentoringDemand> result = new ArrayList<>();
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,department.getAbbreviation());

            ResultSet res = preparedStatement.executeQuery();
            int previousIdPost = -1;

            while(res.next()){
                int idPost = res.getInt(1);
                if(idPost != previousIdPost){
                    result.add(MySQLFactoryObject.createMentoringDemand(res));
                    previousIdPost = idPost;
                }
                Timestamp scheduleTs = res.getTimestamp(String.valueOf(ScheduleTable.DATE_POST_SESSION));
                if(scheduleTs != null){
                    LocalDateTime scheduleDate = scheduleTs.toLocalDateTime();
                    result.get(result.size()-1).addSchedule(new Schedule(scheduleDate,null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public List<MentoringDemand> getPartialMentoringDemands(Subject subject) {
        return null;
    }
}
