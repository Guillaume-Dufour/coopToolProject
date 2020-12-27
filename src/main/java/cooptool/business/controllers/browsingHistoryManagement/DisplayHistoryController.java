package cooptool.business.controllers.browsingHistoryManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.PostFacade;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.Post;
import cooptool.models.objects.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class DisplayHistoryController implements Initializable {

    @FXML
    Button deleteAllButton;
    @FXML
    TableView<Post> historyTableView;
    @FXML
    TableColumn<Post, String> titlePostCol;
    @FXML
    TableColumn<Post, Void> deletePostCol;

    PostFacade postFacade = PostFacade.getInstance();
    User user;

    public void deleteAll(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous confirmer la suppression de votre historique ?");

        alert.initOwner(ViewLoader.getInstance().getStage());

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
            boolean res = postFacade.deleteAllBrowsingHistory(user);

            if (res) {
                historyTableView.getItems().clear();
                historyTableView.refresh();
            }
        }
    }

    public void deletePostFromHistory(ActionEvent event, Post post) {
        boolean res = postFacade.deleteOneFromBrowsingHistory(user, post);

        if (res) {
            historyTableView.getItems().remove(post);
            historyTableView.refresh();
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

        historyTableView.setItems(FXCollections.observableList(postFacade.getBrowsingHistory(user)));
    }
}
