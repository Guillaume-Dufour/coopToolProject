package cooptool.models.daos;

import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.Post;
import cooptool.models.objects.User;

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
    public List<Post> getAllPostsByUser(User user) {

        List<Post> posts = new ArrayList<>();

        String requete = "SELECT * " +
                "FROM post p " +
                "JOIN subject s on p.id_subject = s.id_subject " +
                "WHERE id_user_creator = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(requete);

            preparedStatement.setInt(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return posts;
    }
}
