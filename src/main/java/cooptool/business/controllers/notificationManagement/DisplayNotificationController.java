package cooptool.business.controllers.notificationManagement;

import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayNotificationController implements Initializable {

    @FXML
    TableView<Notification> notificationTableView;

    @FXML
    TableColumn<Notification, String> notificationCol;

    @FXML
    TableColumn<Notification, Void> deleteCol;

    @FXML
    Button deleteAllButton;

    NotificationFacade notificationFacade = NotificationFacade.getInstance();

    public void deleteNotification(ActionEvent event, Notification notification) {
        boolean res = notificationFacade.delete(notification);

        if (res) {
            notificationTableView.getItems().remove(notification);
            notificationTableView.refresh();
        }
    }

    public void deleteAllNotifications(ActionEvent event) {
        boolean res = notificationFacade.deleteAllNotifications(UserFacade.getInstance().getCurrentUser());

        if (res) {
            notificationTableView.getItems().clear();
            notificationTableView.refresh();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Notification> notifications = FXCollections.observableArrayList();

        for (String s : resources.keySet()) {
            notifications.add((Notification) resources.getObject(s));
        }

        notificationCol.setCellValueFactory(param -> {

            String res = param.getValue().getContent();

            return new SimpleObjectProperty<>(res);
        });

        notificationCol.setCellFactory(TextFieldTableCell.forTableColumn());

        deleteCol.setCellFactory(param -> new TableCell<>() {

            private final Button deleteButton = new Button();

            {
                deleteButton.setOnAction(event -> deleteNotification(event, param.getTableView().getItems().get(getIndex())));
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

        notificationTableView.setItems(notifications);

        notificationFacade.getNotifications().addListener((ListChangeListener<Notification>) c -> {
            notifications.setAll(c.getList());
            notificationTableView.refresh();
        });
    }
}
