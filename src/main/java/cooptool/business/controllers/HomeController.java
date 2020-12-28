package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import cooptool.models.objects.StudentRole;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    Pane header_student, header_admin;

    @FXML
    Label notificationNumber;

    @FXML
    Button notificationButton;

    private final NotificationFacade notificationFacade = NotificationFacade.getInstance();
    private final ObservableList<Notification> notifications = notificationFacade.getNotifications();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
        }

        notifications.addListener((ListChangeListener<Notification>) c -> {
            notificationNumber.setText(String.valueOf(c.getList().size()));
        });

        /*notificationFacade.getNbNotifications().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                notificationNumber.setText(newValue.toString());
            }
        });*/

        notificationButton.setOnAction(this::goToNotificationPage);
    }

    public void displayMentoringDemand() {
        ViewLoader.getInstance().load(ViewPath.MENTORING_DEMAND_HOME_PAGE);
    }

    public void displayQuickHelp() {

    }

    public void goToNotificationPage(ActionEvent event) {

        Object[] tabNotifications = notifications.toArray();

        System.out.println(Arrays.toString(tabNotifications));

        ViewLoader.getInstance().load(ViewPath.NOTIFICATIONS, tabNotifications);
    }
}
