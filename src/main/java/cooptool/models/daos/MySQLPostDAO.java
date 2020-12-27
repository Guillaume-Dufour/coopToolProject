package cooptool.models.daos;

import cooptool.models.objects.Post;
import cooptool.models.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MySQLPostDAO extends PostDAO {

    Connection connection = MySQLConnection.getInstance();

    protected MySQLPostDAO() {
        super();
    }

    @Override
    public List<Post> findPostByUser(User user) {
        return null;
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

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
}
