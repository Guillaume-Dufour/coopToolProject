package cooptool.business.controllers.notificationManagement;

import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import cooptool.models.objects.NotificationType;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.*;

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
    ChoiceBox<NotificationType> notificationTypesBox;

    NotificationFacade notificationFacade = NotificationFacade.getInstance();

    public void deleteNotification(ActionEvent event, Notification notification) {
        notificationFacade.delete(notification);
    }

    public void deleteAllNotifications(ActionEvent event) {

        Optional<ButtonType> result = Components.createConfirmationAlert("Voulez-vous vraiment supprimer toutes vos notifications ?");

        if (result.isPresent() && result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
            notificationFacade.deleteAllNotifications(UserFacade.getInstance().getCurrentUser());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Notification> notifications = notificationFacade.getNotifications();

        FilteredList<Notification> notificationFilteredList = new FilteredList<>(notifications, p -> true);


        List<NotificationType> typesNotification = new ArrayList<>();
        typesNotification.add(null);
        typesNotification.addAll(Arrays.asList(NotificationType.sortedValues()));

        notificationTypesBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(NotificationType object) {
                return object == null ? "Tous" : object.getString();
            }

            @Override
            public NotificationType fromString(String string) {
                return null;
            }
        });

        notificationTypesBox.setItems(FXCollections.observableList(typesNotification));

        notificationTypesBox.setOnAction(event -> {
            notificationFilteredList.setPredicate(notification -> {
                NotificationType type = notificationTypesBox.getSelectionModel().getSelectedItem();

                if (type == null) {
                    return true;
                }

                return notification.getTypeNotification().equals(type);
            });
        });

        SortedList<Notification> sortedNotifications = new SortedList<>(notificationFilteredList);
        sortedNotifications.comparatorProperty().bind(notificationTableView.comparatorProperty());

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
                else if (item.getTypeNotification().getValue() == NotificationType.MENTORING_DEMAND.getValue()){
                    setStyle("-fx-background-color: #F9D4D4;");
                }
                else if (item.getTypeNotification().getValue() == NotificationType.QUICK_HELP_POST.getValue()){
                    setStyle("-fx-background-color: #D4DAF9;");
                }
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

        notificationTableView.setItems(sortedNotifications);

        notificationTableView.setOnMouseClicked(event -> {

            Notification selectedNotification = notificationTableView.getSelectionModel().getSelectedItem();

            boolean res = notificationFacade.changeStatusToRead(selectedNotification);

            if (res) {
                notificationTableView.refresh();
            }
        });
    }
}
