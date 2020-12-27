package cooptool.business.controllers.browsingHistoryManagement;

import cooptool.business.facades.PostFacade;
import cooptool.models.objects.Post;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class displayHistoryController implements Initializable {

    @FXML
    Button deleteAllButton, deleteButton;
    @FXML
    TableView<Post> historyTableView;
    @FXML
    TableColumn<Post, String> titlePostCol;
    @FXML
    TableColumn<Post, Integer> supPostCol;

    PostFacade postFacade = PostFacade.getInstance();
    User user;
    List<Post> listPost = new ArrayList<>();

    public void deleteAll(ActionEvent event) {
    }

    private void displayEveryPost() {
        listPost.addAll(postFacade.getBrowsingHistory(user));
        deleteButton.setText("supprimer");
        deleteButton.setStyle("-fx-background-color: #D4D8DA");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (User) resources.getObject("1");
        displayEveryPost();
    }
}
