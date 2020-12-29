package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class QuickHelpPostController implements Initializable {

    @FXML
    Pane header_admin,header_student;
    @FXML
    Button displayButton, creationButton, displayMineButton;

    private final UserFacade userFacade = UserFacade.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.isCurrentUserAdmin()){
            header_admin.setVisible(false);

        } else {
            header_student.setVisible(false);
            disableStudentRights();
        }
        /*try {

        }catch(Exception e) {
            e.printStackTrace();
        }*/
        // displayQHP
    }

    public void chooseMenu(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        if(sourceButton == displayButton) {

        }
        else if(sourceButton == creationButton) {
            ViewLoader.getInstance().load(ViewPath.CREATE_QUICK_HELP_POST);
        }
    }

    private void disableStudentRights(){
        displayButton.setVisible(false);
        creationButton.setVisible(false);
        displayMineButton.setVisible(false);
    }
}
