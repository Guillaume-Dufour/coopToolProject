package cooptool.models.daos;

import cooptool.models.daos.persistent.PostDAO;
import cooptool.models.objects.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySQLPostDAO extends PostDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLPostDAO() {
        super();
    }

    @Override
    public List<Post> findPostByUser(User user) {
        String statement = "SELECT * " +
                "FROM browsing_history b, post p, subject s " +
                "WHERE b.id_post = p.id_post " +
                "AND s.id_subject = p.id_subject " +
                "AND b.id_user = ?;";
        List<Post> result = new ArrayList<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int typePost = resultSet.getInt("type_post");
                Post post;
                Subject subject = new Subject(
                        resultSet.getInt("id_subject"),
                        resultSet.getString("name_subject"),
                        resultSet.getInt("available"),
                        null
                );
                if (typePost == MENTORING_DEMAND) {
                    post = new MentoringDemand(
                            resultSet.getInt("id_post"),
                            subject,
                            resultSet.getString("description_post"),
                            resultSet.getTimestamp("date_post").toLocalDateTime());
                } else {
                    post = new QuickHelpPost(
                            resultSet.getInt("id_post"),
                            subject,
                            resultSet.getString("description_post"),
                            resultSet.getTimestamp("date_post").toLocalDateTime()
                    );
                }
                result.add(post);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

        @Override
    public Post findPostById(int id) {
        return null;
    }

    @Override
    public boolean update(Post post) {
        return false;
    }

    @Override
    public boolean deleteOneFromBrowsingHistory(User user, Post post) {
        String statement =
                "DELETE FROM browsing_history WHERE id_user = ? AND id_post = ?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, post.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteAllFromBrowsingHistory(User user) {
        String statement =
                "DELETE FROM browsing_history WHERE id_user = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void comment(Comment comment, Post post) {
        String statement =
                "INSERT INTO comment (content_comment,date_comment,id_user_creator,id_post) " +
                "VALUES (?,?,?,?)";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(statement);
            insertStatement.setString(1, comment.getContent());
            insertStatement.setTimestamp(2, Timestamp.valueOf(comment.getCreationDate()));
            insertStatement.setInt(3,comment.getCreator().getId());
            insertStatement.setInt(4,post.getId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getComments(Post post) {
        String statement =
                "SELECT comment.id_comment,comment.content_comment,comment.date_comment," +
                        "user.id_user,user.first_name_user,user.last_name_user," +
                        "department.abbreviation_department,year_department " +
                        "FROM comment " +
                        "JOIN user ON comment.id_user_creator = user.id_user " +
                        "JOIN department ON user.id_department = department.id_department " +
                        "WHERE comment.id_post = ?";
        try {
            PreparedStatement selectStatement = connection.prepareStatement(statement);
            selectStatement.setInt(1,post.getId());
            ResultSet res = selectStatement.executeQuery();
            while(res.next()){
                int idComment = res.getInt(1);
                String content = res.getString(2);
                LocalDateTime creationDate = res.getTimestamp(3).toLocalDateTime();
                int idCreator = res.getInt(4);
                String firstNameCreator = res.getString(5);
                String lastNameCreator = res.getString(6);
                String abbreviationDptCreator = res.getString(7);
                int yearDptCreator = res.getInt(8);
                Department departmentCreator =
                        new Department(-1,null,yearDptCreator,abbreviationDptCreator,-1);
                StudentRole roleCreator =
                        new StudentRole(firstNameCreator,lastNameCreator,null,departmentCreator);
                User creator =
                        new User(idCreator,null,null, roleCreator,-1);
                post.addComment(new Comment(idComment,content,creationDate,creator));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
