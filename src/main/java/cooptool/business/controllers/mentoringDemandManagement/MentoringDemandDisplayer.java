package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.StudentRole;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MentoringDemandDisplayer implements Initializable {
    @FXML
    Pane header_admin,header_student;
    @FXML
    Button creationButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
            creationButton.setVisible(false);
        }
    }

    public void goToCreationPage(){
        ViewLoader.getInstance().load(ViewPath.CREATE_MENTORING_DEMAND);
    }
}
