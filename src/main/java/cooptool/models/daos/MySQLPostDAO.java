package cooptool.models.daos;

import cooptool.models.objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
}
