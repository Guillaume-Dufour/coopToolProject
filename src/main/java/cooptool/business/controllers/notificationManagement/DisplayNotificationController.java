package cooptool.business.controllers.notificationManagement;

import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import cooptool.models.objects.NotificationType;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayNotificationController implements Initializable {

    @FXML
    TableView<Notification> notificationTableView;

    @FXML
    TableColumn<Notification, Notification> notificationCol;

    @FXML
    TableColumn<Notification, Void> deleteCol;

    @FXML
    Button deleteAllButton;

    @FXML
    ComboBox<String> notificationTypesBox;

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


        notificationTypesBox.setItems(FXCollections.observableList(NotificationType.getStringValues()));

        FilteredList<Notification> filteredNotifications = new FilteredList<>(notifications);

        notificationTypesBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            /*filteredNotifications.setPredicate(notification -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                System.out.println(notification.getTypeNotification().getString().equalsIgnoreCase(newValue));

                return notification.getTypeNotification().getString().equalsIgnoreCase(newValue);
            });*/
        });

        for (String s : resources.keySet()) {
            notifications.add((Notification) resources.getObject(s));
        }

        notificationCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));

        notificationCol.setCellFactory(param -> new TableCell<>() {

            private final Label label = new Label();

            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    label.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                    label.setText(item.getContent());
                    setGraphic(label);
                }
            }
        });

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

        notificationTableView.setItems(filteredNotifications);

        notificationFacade.getNotifications().addListener((ListChangeListener<Notification>) c -> {
            notifications.setAll(c.getList());
            notificationTableView.refresh();
        });
    }
}
