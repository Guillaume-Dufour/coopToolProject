package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import cooptool.models.objects.StudentRole;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    Label notificationNumber;


    private final NotificationFacade notificationFacade = NotificationFacade.getInstance();
    private final ObservableList<Notification> notifications = notificationFacade.getNotifications();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        notifications.addListener((ListChangeListener<Notification>) c -> {
            int nbNotifications = c.getList().size();
            notificationNumber.setText(nbNotifications != 0 ? String.valueOf(nbNotifications) : "");
        });

        /*notificationFacade.getNbNotifications().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                notificationNumber.setText(newValue.toString());
            }
        });*/
    }

    public void displayMentoringDemand() {
        ViewLoader.getInstance().load(ViewPath.MENTORING_DEMAND_HOME_PAGE);
    }

    public void displayQuickHelp() {

    }

    public void goToNotificationPage(MouseEvent mouseEvent) {

        Object[] tabNotifications = notifications.toArray();

        System.out.println(Arrays.toString(tabNotifications));

        ViewLoader.getInstance().load(ViewPath.NOTIFICATIONS, tabNotifications);
    }
}
