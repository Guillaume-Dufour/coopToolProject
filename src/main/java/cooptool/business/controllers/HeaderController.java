package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Notification;
import cooptool.models.objects.StudentRole;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

    @FXML
    ImageView parameterButton, notificationButton;
    @FXML
    Text notificationNumber;

    UserFacade userFacade = UserFacade.getInstance();
    private final NotificationFacade notificationFacade = NotificationFacade.getInstance();
    private final ObservableList<Notification> notifications = notificationFacade.getNotifications();


    public void goToHome() {
        ViewLoader.getInstance().load(ViewPath.HOME);
    }

    public void searchStudent() {ViewLoader.getInstance().load(ViewPath.SEARCH_STUDENT);}

    public void manageDepartment() {
        ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);
    }

    public void goToProfil() {
        if (userFacade.getCurrentUser().getRole() instanceof StudentRole){
            ViewLoader.getInstance().load(ViewPath.STUDENT_PROFIL, userFacade.getCurrentUser());
        } else {
            ViewLoader.getInstance().load(ViewPath.UPDATE_ADMIN_ACCOUNT);
        }
    }

    public void disconnect() {
        userFacade.disconnect();
        NotificationFacade.getInstance().stopTimer();
        ViewLoader.getInstance().load(ViewPath.LOGIN);
    }

    public void goToNotificationPage() {
        Object[] tabNotifications = notifications.toArray();
        System.out.println(Arrays.toString(tabNotifications));
        ViewLoader.getInstance().load(ViewPath.NOTIFICATIONS, tabNotifications);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.getCurrentUser().getRole() instanceof StudentRole){
            parameterButton.setVisible(false);
        } else {
            notificationButton.setVisible(false);
            notificationNumber.setVisible(false);
            notificationNumber.setDisable(true);
        }
    }
}
