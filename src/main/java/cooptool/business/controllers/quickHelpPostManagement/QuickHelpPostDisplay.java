package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.QuickHelpPost;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuickHelpPostDisplay implements Initializable {

    @FXML
    Pane header_admin,header_student;
    @FXML
    Button displayQHPButton, displayMyQHPButton, creationQHPButton;

    private final UserFacade userFacade = UserFacade.getInstance();
    private final ViewLoader viewLoader = ViewLoader.getInstance();
    List<QuickHelpPost> partialQuickHelpPosts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.isCurrentUserAdmin()){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
            disableStudentRights();
        }
        partialQuickHelpPosts = QuickHelpPostFacade.getInstance().getQuickHelpPosts();
        createNavigationButtons();
    }

    public void goToQHPDisplayPage() {

    }

    public void goToQHPCreationPage() {
        viewLoader.load(ViewPath.CREATE_QUICK_HELP_POST);
    }

    public void goToMyQHPPage() {

    }

    private void disableStudentRights(){
        displayQHPButton.setVisible(false);
        creationQHPButton.setVisible(false);
        displayMyQHPButton.setVisible(false);
    }

    private void createNavigationButtons() {
        int numberOfButtons = (partialQuickHelpPosts.size()-1)/6 +1;
        for(int j=1;j<=numberOfButtons;j++){

        }
    }
}
