package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class QuickHelpPostDisplay implements Initializable {

    @FXML
    Button displayQHPButton, displayMyQHPButton, creationQHPButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToQHPDisplayPage() {

    }

    public void goToQHPCreationPage() {
        ViewLoader.getInstance().load(ViewPath.CREATE_QUICK_HELP_POST);
    }

    public void goToMyQHPPage() {

    }
}
