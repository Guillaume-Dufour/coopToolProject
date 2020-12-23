package cooptool.models.daos;

import cooptool.models.objects.*;

import java.sql.*;
import java.time.LocalDate;
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
            insertPostStatement.setDate(2,Date.valueOf(LocalDate.now()));
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
            insertParticipationStatement.setDate(3,Date.valueOf(LocalDate.now()));
            insertParticipationStatement.setInt(4,MentoringDemandDAO.STUDENT);

            insertParticipationStatement.executeUpdate();

            insertScheduleStatement = connection.prepareStatement(statement3);
            insertScheduleStatement.setInt(1,postId);
            insertScheduleStatement.setDate(2,Date.valueOf(initialSchedule.getDate()));
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
        return null;
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
            LocalDate creationDate = null;
            String subjectName = null;
            String abbreviationDepartment = null;
            int yearDepartment = -1;
            int idUser = -1;
            String firstNameUser = null;
            String lastNameUser = null;
            LocalDate scheduleDate;
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
                    creationDate = res.getDate(3).toLocalDate();
                    subjectName = res.getString(4);
                    abbreviationDepartment = res.getString(5);
                    yearDepartment = res.getInt(6);
                    idUser = res.getInt(7);
                    firstNameUser = res.getString(8);
                    lastNameUser = res.getString(9);
                }
                scheduleDate = res.getDate(10).toLocalDate();
                schedules.add(new Schedule(scheduleDate,null));
            }
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
