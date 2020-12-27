package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.daos.QuickHelpPostDAO;
import cooptool.models.objects.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HandleQuickHelpPostController implements Initializable {

    User user = UserFacade.getInstance().getCurrentUser();
    StudentRole student = (StudentRole) user.getRole();
    @FXML
    ComboBox<Department> department;
    @FXML
    ComboBox<Subject> subject;
    @FXML
    TextArea description;
    @FXML
    Label infoLabel,errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void create(ActionEvent actionEvent) {
        if(department.getValue() == null) {
            errorLabel.setText("Please pick a department");
        }
        else if(subject.getValue() == null) {
            errorLabel.setText("Please pick a subject");
        }
        else {
            QuickHelpPost qhp =
                    new QuickHelpPost(
                        -1,
                        subject.getValue(),
                        description.getText(),
                        LocalDate.now(),
                        user
            );
            //QuickHelpPostFacade.getInstance().create(qhp);
            ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
        }
    }

}
