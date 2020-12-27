package cooptool.business.controllers.browsingHistoryManagement;

import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.PostFacade;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.Post;
import cooptool.models.objects.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    }

    public void deletePostFromHistory(ActionEvent event, MentoringDemand mentoringDemand) {
        System.out.println(mentoringDemand.getId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        user = (User) resources.getObject("1");

        titlePostCol.setCellValueFactory(param -> {
            String description = param.getValue().getDescription();
            String nameSubject = param.getValue().getSubject().getName();
            String date = param.getValue().getCreationDate().toString();

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

        historyTableView.setItems(FXCollections.observableList(mentoringDemandFacade.getMentoringDemands()));
    }
}
