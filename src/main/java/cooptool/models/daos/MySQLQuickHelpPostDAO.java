package cooptool.models.daos;

import cooptool.models.daos.persistent.PostDAO;
import cooptool.models.daos.persistent.QuickHelpPostDAO;
import cooptool.models.objects.Department;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            insertPostStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void delete(QuickHelpPost quickHelpPost) {
        String statement =
                "DELETE FROM post " +
                        "WHERE id_post = ?";
        try {
            PreparedStatement deletionStatement = connection.prepareStatement(statement);
            deletionStatement.setInt(1,quickHelpPost.getId());
            deletionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public QuickHelpPost getQuickHelpPost(int id) {
        String statement =
                "SELECT * " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "JOIN user creator ON post.id_user_creator = creator.id_user " +
                        "JOIN department ON creator.id_department = department.id_department "+
                        "WHERE post.id_post = ? AND type_post = 1";
        QuickHelpPost qhp = null;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()){
                int idPost = res.getInt(1);
                qhp = MySQLFactoryObject.createQuickHelpPost(res);
            }
            System.out.println("ooooooooooo " + qhp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qhp;
    }

    @Override
    public List<QuickHelpPost> getPartialQHP(Department department) {
        String statement =
                "SELECT * " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "JOIN user AS u ON post.id_user_creator = u.id_user " +
                        "JOIN department ON u.id_department = department.id_department "+
                        "WHERE department.abbreviation_department = ? AND type_post = 1 ORDER BY date_post,post.id_post";
        List<QuickHelpPost> partialQHP = new ArrayList<>();
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,department.getAbbreviation());

            ResultSet res = preparedStatement.executeQuery();
            int previousIdPost = -1;

            while(res.next()){
                int idPost = res.getInt(1);
                if(idPost != previousIdPost){
                    partialQHP.add(MySQLFactoryObject.createQuickHelpPost(res));
                    previousIdPost = idPost;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partialQHP;
    }

    @Override
    public List<QuickHelpPost> getPartialQHP(User user, Department department) {
        String statement =
                "SELECT * " +
                        "FROM post " +
                        "NATURAL JOIN subject " +
                        "JOIN user AS u ON post.id_user_creator = u.id_user " +
                        "JOIN department ON u.id_department = department.id_department "+
                        "WHERE u.id_user = ? AND department.abbreviation_department = ? AND type_post = 1 ORDER BY date_post,post.id_post";
        List<QuickHelpPost> partialQHP = new ArrayList<>();
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2,department.getAbbreviation());
            ResultSet res = preparedStatement.executeQuery();
            int previousIdPost = -1;

            while(res.next()){
                int idPost = res.getInt(1);
                if(idPost != previousIdPost){
                    partialQHP.add(MySQLFactoryObject.createQuickHelpPost(res));
                    previousIdPost = idPost;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partialQHP;
    }


    @Override
    public List<QuickHelpPost> getPartialQHP() {
        return null;
    }

    @Override
    public void updateDescription(QuickHelpPost qhp) {
        String statement =
                "UPDATE post " +
                        "SET description_post = ? " +
                        "WHERE id_post = ?";
        try {
            PreparedStatement updateStatement = connection.prepareStatement(statement);
            updateStatement.setString(1, qhp.getDescription());
            updateStatement.setInt(2, qhp.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
