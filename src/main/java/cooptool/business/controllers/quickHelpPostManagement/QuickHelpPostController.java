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

    private final UserFacade userFacade = UserFacade.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.isCurrentUserAdmin()){
            header_admin.setVisible(false);

        } else {
            header_student.setVisible(false);
        }
        /*try {

        }catch(Exception e) {
            e.printStackTrace();
        }*/
        // displayQHP
    }


}
