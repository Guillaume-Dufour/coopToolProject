package cooptool.business.controllers.notificationManagement;

import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import cooptool.models.objects.NotificationType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.List;
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
    ChoiceBox<String> notificationTypesBox;

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

        //FilteredList<Notification> notifications = new FilteredList<>(FXCollections.observableArrayList());

        ObservableList<Notification> notifications = FXCollections.observableArrayList();

        List<String> typesNotification = NotificationType.getStringValues();

        notificationTypesBox.setItems(FXCollections.observableList(typesNotification));

        notificationTypesBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(typesNotification.get(newValue.intValue()));
        });

        /*FilteredList<Notification> filteredNotifications = new FilteredList<>(notifications);

        notificationTypesBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            *//*filteredNotifications.setPredicate(notification -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                System.out.println(notification.getTypeNotification().getString().equalsIgnoreCase(newValue));

                return notification.getTypeNotification().getString().equalsIgnoreCase(newValue);
            });*//*
        });*/

        for (String s : resources.keySet()) {
            notifications.add((Notification) resources.getObject(s));
        }

        /*SortedList<Notification> sortedNotifications = new SortedList<>(notifications);
        sortedNotifications.comparatorProperty().bind(notificationTableView.comparatorProperty());*/

        notificationCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()));

        notificationCol.setCellFactory(param -> new TableCell<>() {

            private final Label label = new Label();

            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else if (item.getIsRead() == 0) {
                    label.setFont(Font.font("System", FontWeight.BOLD, 12));
                    label.setText(item.getContent());
                    setGraphic(label);
                }
                else {
                    label.setText(item.getContent());
                    label.setStyle(null);
                    label.setFont(Font.font("System", FontWeight.NORMAL, 12));
                    setGraphic(label);
                }
            }
        });

        notificationTableView.setRowFactory(tr -> new TableRow<>() {

            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                }
               /* else if (item.getTypeNotification().getValue() == NotificationType.MENTORING_DEMAND.getValue()){
                    setStyle("-fx-background-color: #F9D4D4;");
                }
                else if (item.getTypeNotification().getValue() == NotificationType.QUICK_HELP_POST.getValue()){
                    setStyle("-fx-background-color: #D4DAF9;");
                }*/
                else {
                    setStyle("");
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
                    deleteButton.setStyle("-fx-background-color: #d4d8da");

                    setGraphic(deleteButton);
                }
            }
        });

        //notificationTableView.setItems(filteredNotifications);

        notificationTableView.setItems(notifications);

        notificationTableView.setOnMouseClicked(event -> {

            Notification selectedNotification = notificationTableView.getSelectionModel().getSelectedItem();

            boolean res = notificationFacade.changeStatusToRead(selectedNotification);

            if (res) {
                notificationTableView.refresh();
            }
        });

        notificationFacade.getNotifications().addListener((ListChangeListener<Notification>) c -> {
            notifications.setAll(c.getList());
            notificationTableView.refresh();
        });
    }
}
