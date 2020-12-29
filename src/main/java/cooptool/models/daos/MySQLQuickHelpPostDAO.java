package cooptool.models.daos;

import cooptool.models.daos.persistent.PostDAO;
import cooptool.models.daos.persistent.QuickHelpPostDAO;
import cooptool.models.objects.QuickHelpPost;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MySQLQuickHelpPostDAO extends QuickHelpPostDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLQuickHelpPostDAO() {
        super();
    }

    @Override
    public void create(QuickHelpPost quickHelpPost) {
        String statement =
                "INSERT INTO post (description_post,date_post,type_post,id_user_creator,id_subject) " +
                        "VALUES (?,?,?,?,?)";
        PreparedStatement insertPostStatement;
        try{
            insertPostStatement = connection.prepareStatement(statement);
            insertPostStatement.setString(1, quickHelpPost.getDescription());
            insertPostStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            insertPostStatement.setInt(3, PostDAO.QUICK_HELP_POST);
            insertPostStatement.setInt(4, quickHelpPost.getCreator().getId());
            insertPostStatement.setInt(5, quickHelpPost.getSubject().getId());
            insertPostStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //return false;
        }
        //return true;
    }

    @Override
    public void delete(QuickHelpPost quickHelpPost) {

    }

    @Override
    public QuickHelpPost getQuickHelpPost(int id) {
        return null;
    }
}
