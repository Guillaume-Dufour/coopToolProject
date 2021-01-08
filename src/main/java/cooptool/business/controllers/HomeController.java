package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {

    @FXML
    Button mentoringDemandButton;

    @FXML
    Button quickHelpPostButton;

    public void displayMentoringDemand() {
        ViewLoader.getInstance().load(ViewPath.MENTORING_DEMAND_HOME_PAGE);
    }

    public void displayQuickHelpPost() {
        ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }
}
