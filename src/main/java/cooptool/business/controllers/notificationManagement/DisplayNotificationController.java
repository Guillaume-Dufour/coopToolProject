package cooptool.business.controllers.notificationManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.PostFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import cooptool.models.objects.NotificationType;
import cooptool.models.objects.Post;
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

/**
 * DisplayNotificationController class
 */
public class DisplayNotificationController implements Initializable {

    @FXML
    private TableView<Notification> notificationTableView;

    @FXML
    private TableColumn<Notification, Notification> notificationCol;

    @FXML
    private TableColumn<Notification, Void> deleteCol;

    @FXML
    private Button deleteAllButton;

    @FXML
    private ChoiceBox<NotificationType> notificationTypesBox;

    /**
     * Attribute to access to the NotificationFacade
     */
    private final NotificationFacade notificationFacade = NotificationFacade.getInstance();

    /**
     * Attribute to access to the NotificationFacade
     */
    private final PostFacade postFacade = PostFacade.getInstance();

    /**
     * Delete the notification in parameter
     * @param event Action event
     * @param notification Notification to delete
     */
    public void deleteNotification(ActionEvent event, Notification notification) {
        notificationFacade.delete(notification);
    }

    /**
     * Delete all the notifications of the user
     * @param event Action event
     */
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

            if(event.getClickCount() == 2){
                if (selectedNotification.getTypeNotification() == NotificationType.MENTORING_DEMAND){
                    ViewLoader.getInstance().load(ViewPath.GET_MENTORING_DEMAND, selectedNotification.getObjectId());
                } else {
                    ViewLoader.getInstance().load(ViewPath.GET_QUICK_HELP_POST, selectedNotification.getObjectId());
                }
            } else {
                boolean res = notificationFacade.changeStatusToRead(selectedNotification);

                if (res) {
                    notificationTableView.refresh();
                }
            }
        });
    }
}
