package cooptool.business.controllers.browsingHistoryManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.PostFacade;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.Post;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.User;
import cooptool.utils.Components;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DisplayHistoryController implements Initializable {

    @FXML
    Button deleteAllButton;
    @FXML
    TableView<Post> historyTableView;
    @FXML
    TableColumn<Post, String> titlePostCol;
    @FXML
    TableColumn<Post, Void> deletePostCol;

    @FXML
    ChoiceBox<String> postTypes;

    PostFacade postFacade = PostFacade.getInstance();
    User user;

    private ObservableList<Post> posts;

    public void deleteAll(ActionEvent event) {

        Optional<ButtonType> result = Components.createConfirmationAlert("Voulez-vous confirmer la suppression de votre historique ?");

        if (result.isPresent() && result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
            boolean res = postFacade.deleteAllBrowsingHistory(user);

            if (res) {
                posts.clear();
            }
        }
    }

    public void deletePostFromHistory(ActionEvent event, Post post) {
        boolean res = postFacade.deleteOneFromBrowsingHistory(user, post);

        if (res) {
            posts.remove(post);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        user = (User) resources.getObject("1");

        titlePostCol.setCellValueFactory(param -> {
            String description = param.getValue().getDescription();
            String nameSubject = param.getValue().getSubject().getName();

            //String date = param.getValue().getCreationDate().getDayOfWeek().toString();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
            String date = param.getValue().getCreationDate().format(dateTimeFormatter);

            String res = nameSubject + "\n" + description + "\n" + date;

            return new SimpleObjectProperty<>(res);
        });

        titlePostCol.setCellFactory(TextFieldTableCell.forTableColumn());

        deletePostCol.setCellFactory(param -> new TableCell<>() {

            private final Button deleteButton = new Button();

            {
                deleteButton.setOnAction(event -> deletePostFromHistory(event, param.getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    deleteButton.setText("Supprimer");
                    deleteButton.setStyle("-fx-background-color: #D4D8DA");

                    setGraphic(deleteButton);
                }
            }
        });

        historyTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                Post selected = historyTableView.getSelectionModel().getSelectedItem();
                ViewPath path;

                if (selected instanceof MentoringDemand) {
                    path = ViewPath.GET_MENTORING_DEMAND;
                }
                else {
                    path = ViewPath.HOME;
                }

                ViewLoader.getInstance().load(path, selected.getId());
            }
        });

        posts = FXCollections.observableList(postFacade.getBrowsingHistory(user));

        FilteredList<Post> filteredPosts = new FilteredList<>(posts);

        List<String> types = Arrays.asList("Tous", "Mentoring Demand", "Quick Help Post");

        postTypes.setItems(FXCollections.observableList(types));

        postTypes.setValue("Tous");

        postTypes.setOnAction(event -> {
            filteredPosts.setPredicate(post -> {

                String type = postTypes.getSelectionModel().getSelectedItem();

                switch (type) {
                    case "Mentoring Demand":
                        return post instanceof MentoringDemand;
                    case "Quick Help Post":
                        return post instanceof QuickHelpPost;
                    default:
                        return true;
                }
            });
        });

        SortedList<Post> sortedPosts = new SortedList<>(filteredPosts);
        sortedPosts.comparatorProperty().bind(historyTableView.comparatorProperty());

        historyTableView.setItems(sortedPosts);
    }
}
