package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuickHelpPostCreator implements Initializable {

    User user = UserFacade.getInstance().getCurrentUser();
    StudentRole student = (StudentRole) user.getRole();
    QuickHelpPostFacade qhpFacade = QuickHelpPostFacade.getInstance();

    @FXML
    ComboBox<Subject> subject;
    @FXML
    TextArea description;
    @FXML
    Button creationButton;
    @FXML
    Label infoLabel,errorLabel;

    private final List<Subject> subjects = SubjectFacade.getInstance().getSubjectsByDepartment(student.getDepartment());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSubjectBox();
    }

    public void create(ActionEvent actionEvent) {
        if(subject.getValue() == null) {
            errorLabel.setText("Veuillez choisir une matière.");
        }
        else {
            QuickHelpPostFacade.getInstance().create(description.getText(), subject.getValue(), user);
            ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
        }
    }

    private void initializeSubjectBox(){
        subject.setItems(FXCollections.observableList(subjects));
        subject.setConverter(new StringConverter<>() {

            @Override
            public String toString(Subject object) {
                return object != null ? object.getName() : "Choisir une matière...";
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }

        });
    }

}
