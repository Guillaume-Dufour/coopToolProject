package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.PostFacade;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HandleQuickHelpPostController implements Initializable {

    User user = UserFacade.getInstance().getCurrentUser();
    QuickHelpPostFacade qhpFacade = QuickHelpPostFacade.getInstance();

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
            // TODO : on ne créer par la quick help post dans le controller mais dans la facade
            // qhpFacade.create( avec les bons arguments );
            QuickHelpPost qhp =
                new QuickHelpPost(
                    -1,
                    subject.getValue(),
                    description.getText(),
                    user,
                    LocalDateTime.now()
                );
            ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
        }
    }

}
