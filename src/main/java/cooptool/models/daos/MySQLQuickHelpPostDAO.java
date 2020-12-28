package cooptool.models.daos;

import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.Subject;

import java.sql.*;
import java.time.LocalDateTime;

public class MySQLQuickHelpPostDAO extends QuickHelpPostDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLQuickHelpPostDAO() {
        super();
    }


    @Override
    public boolean create(QuickHelpPost quickHelpPost) {
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
            insertPostStatement.executeUpdate(statement,Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void delete(QuickHelpPost quickHelpPost) {

    }

    @Override
    public QuickHelpPost getQuickHelpPost(int id) {
        return null;
    }
}
