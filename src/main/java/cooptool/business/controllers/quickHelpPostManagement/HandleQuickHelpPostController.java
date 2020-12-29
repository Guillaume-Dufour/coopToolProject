package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HandleQuickHelpPostController implements Initializable {

    User user = UserFacade.getInstance().getCurrentUser();
    StudentRole student = (StudentRole) user.getRole();
    QuickHelpPostFacade qhpFacade = QuickHelpPostFacade.getInstance();


    @FXML
    ComboBox<Subject> subject;
    @FXML
    TextArea description;
    @FXML
    Label infoLabel,errorLabel;

    private final List<Subject> subjects = SubjectFacade.getInstance().getSubjectsByDepartment(student.getDepartment());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void create(ActionEvent actionEvent) {
        if(subject.getValue() == null) {
            errorLabel.setText("Please pick a subject");
        }
        else {
            QuickHelpPostFacade.getInstance().create(description.getText(), subject.getValue(), user);
            ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
        }
    }

}
