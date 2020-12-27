package cooptool.business.controllers.browsingHistoryManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.PostFacade;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayHistoryController implements Initializable {

    @FXML
    Button deleteAllButton;
    @FXML
    TableView<MentoringDemand> historyTableView;
    @FXML
    TableColumn<MentoringDemand, String> titlePostCol;
    @FXML
    TableColumn<MentoringDemand, Void> deletePostCol;

    PostFacade postFacade = PostFacade.getInstance();
    MentoringDemandFacade mentoringDemandFacade = MentoringDemandFacade.getInstance();
    User user;

    public void deleteAll(ActionEvent event) {
        postFacade.deleteAllBrowsingHistory(user);
    }

    public void deletePostFromHistory(ActionEvent event, MentoringDemand mentoringDemand) {
        postFacade.deleteOneFromBrowsingHistory(user, mentoringDemand);
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
                ViewLoader.getInstance().load(ViewPath.GET_MENTORING_DEMAND, historyTableView.getSelectionModel().getSelectedItem().getId());
            }
        });

        historyTableView.setItems(FXCollections.observableList(mentoringDemandFacade.getMentoringDemands()));
    }
}
