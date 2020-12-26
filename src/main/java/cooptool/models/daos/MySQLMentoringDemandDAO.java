package cooptool.models.daos;

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
            insertScheduleStatement.setTimestamp(2,Timestamp.valueOf(initialSchedule.getDate()));
            insertScheduleStatement.setInt(3,initialSchedule.getCreator().getId());

            insertScheduleStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(MentoringDemand mentoringDemand) {

    }

    @Override
    public void delete(MentoringDemand mentoringDemand) {

    }

    @Override
    public MentoringDemand getMentoringDemand(int id) {
        String statement =
                "SELECT id_post,description_post,date_post," +
                        "name_subject," +
                        "creator.id_user,creator.first_name_user,creator.last_name_user," +
                        "abbreviation_department,year_department," +
                        "date_post_session," +
                        "scheduleCreator.id_user,scheduleCreator.first_name_user,scheduleCreator.last_name_user " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "NATURAL JOIN schedule " +
                        "JOIN user creator ON post.id_user_creator = creator.id_user " +
                        "JOIN user scheduleCreator ON schedule.creator_id = scheduleCreator.id_user " +
                        "JOIN department ON creator.id_department = department.id_department "+
                        "WHERE id_post = ? AND type_post = 0 ORDER BY date_post";
        MentoringDemand result = null;
        PreparedStatement createDemandStatement;
        try{
            createDemandStatement = connection.prepareStatement(statement);
            createDemandStatement.setInt(1,id);
            ResultSet res = createDemandStatement.executeQuery();

            ArrayList<Schedule> schedules = new ArrayList<>();
            int idPost = -1;
            String description = null;
            LocalDateTime creationDate = null;
            String subjectName = null;
            String abbreviationDepartment = null;
            int yearDepartment = -1;
            int creatorId = -1;
            String creatorFirstName = null;
            String creatorLastName = null;
            LocalDateTime scheduleDate = null;
            int scheduleCreatorId = -1;
            String scheduleCreatorFirstName = null;
            String scheduleCreatorLastName = null;
            int i = 0;
            while(res.next()){
                if(i==0) {
                    idPost = res.getInt(1);
                    description = res.getString(2);
                    creationDate = res.getTimestamp(3).toLocalDateTime();
                    subjectName = res.getString(4);
                    creatorId = res.getInt(5);
                    creatorFirstName = res.getString(6);
                    creatorLastName = res.getString(7);
                    abbreviationDepartment = res.getString(8);
                    yearDepartment = res.getInt(9);
                    i++;
                }
                scheduleDate = res.getTimestamp(10).toLocalDateTime();
                scheduleCreatorId = res.getInt(11);
                scheduleCreatorFirstName = res.getString(12);
                scheduleCreatorLastName = res.getString(13);
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
                schedules.add(schedule);
            }
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
            addParticipationToMentoringDemand(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void addParticipationToMentoringDemand(MentoringDemand demand){
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
            int userId = -1;
            String firstNameUser = null;
            String lastNameUser = null;
            LocalDateTime participationDate = null;
            int participationType = -1;
            int previousUserId = -1;
            ArrayList<Schedule> selectedSchedules = new ArrayList<>();
            while(res.next()){
                userId = res.getInt(1);
                if(userId != previousUserId){
                    if(previousUserId != -1){
                        StudentRole role = new StudentRole(firstNameUser,lastNameUser,null,null);
                        demand.addParticipation(
                                new Participation(
                                        new User(userId,null,null,role,-1),
                                        participationType,
                                        selectedSchedules
                                )
                        );
                    }
                    previousUserId = userId;
                    firstNameUser = res.getString(2);
                    lastNameUser = res.getString(3);
                    participationType = res.getInt(5);
                }
                participationDate = res.getTimestamp(4).toLocalDateTime();
                selectedSchedules.add(new Schedule(participationDate,null));
            }
            if(previousUserId != -1){
                StudentRole role = new StudentRole(firstNameUser,lastNameUser,null,null);
                demand.addParticipation(
                        new Participation(
                                new User(userId,null,null,role,-1),
                                participationType,
                                selectedSchedules
                        )
                );
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
                PreparedStatement insertStatement = null;
                insertStatement = connection.prepareStatement(statement);
                insertStatement.setInt(1,participation.getParticipant().getId());
                insertStatement.setInt(2,mentoringDemand.getId());
                insertStatement.setTimestamp(3,Timestamp.valueOf(schedule.getDate()));
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
                        "SELECT id_post,description_post,date_post,name_subject,abbreviation_department,year_department,id_user,first_name_user,last_name_user,date_post_session " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "NATURAL JOIN schedule " +
                        "JOIN user u1 ON post.id_user_creator = u1.id_user " +
                        "JOIN department ON u1.id_department = department.id_department "+
                        "WHERE department.abbreviation_department = ? AND type_post = 0 ORDER BY date_post";
        List<MentoringDemand> result = new ArrayList<>();
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,department.getAbbreviation());

            ResultSet res = preparedStatement.executeQuery();

            ArrayList<Schedule> schedules = new ArrayList<>();
            String description = null;
            LocalDateTime creationDate = null;
            String subjectName = null;
            String abbreviationDepartment = null;
            int yearDepartment = -1;
            int idUser = -1;
            String firstNameUser = null;
            String lastNameUser = null;
            LocalDateTime scheduleDate;
            int previousIdPost = -1;
            int idPost = -1;
            while(res.next()){
                idPost = res.getInt(1);
                if(idPost != previousIdPost){
                    if(previousIdPost != -1){
                        result.add(
                                new MentoringDemand(
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
                                           idUser,
                                           null,
                                           null,
                                           new StudentRole(
                                                   firstNameUser,
                                                   lastNameUser,
                                                   null,
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
                                )
                        );
                        schedules = new ArrayList<>();
                    }
                    previousIdPost = idPost;
                    description = res.getString(2);
                    creationDate = res.getTimestamp(3).toLocalDateTime();
                    subjectName = res.getString(4);
                    abbreviationDepartment = res.getString(5);
                    yearDepartment = res.getInt(6);
                    idUser = res.getInt(7);
                    firstNameUser = res.getString(8);
                    lastNameUser = res.getString(9);
                }
                scheduleDate = res.getTimestamp(10).toLocalDateTime();
                schedules.add(new Schedule(scheduleDate,null));
            }
            if(previousIdPost != -1){
                result.add(
                        new MentoringDemand(
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
                                        idUser,
                                        null,
                                        null,
                                        new StudentRole(
                                                firstNameUser,
                                                lastNameUser,
                                                null,
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
                        )
                );
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
