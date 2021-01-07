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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController {

    @FXML
    Button mentoringDemandButton, quickHelpButton;

    /**
     * Switch to the mentoring demand home page view
     */
    public void displayMentoringDemand() {
        ViewLoader.getInstance().load(ViewPath.MENTORING_DEMAND_HOME_PAGE);
    }

    /**
     * Switch to the quick help post home page view
     */
    public void displayQuickHelpPost() {
        ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }

}
