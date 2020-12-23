package cooptool.models.daos;

import cooptool.models.objects.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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
        PreparedStatement insertPostStatement = null;
        PreparedStatement insertParticipationStatement = null;
        PreparedStatement insertScheduleStatement = null;
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
    public ArrayList<MentoringDemand> getMentoringDemands(User user) {
        return null;
    }

    @Override
    public ArrayList<MentoringDemand> getMentoringDemands(Department department) {
        return null;
    }

    @Override
    public ArrayList<MentoringDemand> getMentoringDemands(Department department, Subject subject, int numberOfDemands) {
        return null;
    }
}
