package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;
import cooptool.utils.Components;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * QuickHelpPostCreator class
 */
public class QuickHelpPostCreator implements Initializable {

    @FXML
    private ComboBox<Subject> subject;

    @FXML
    private TextArea description;

    @FXML
    private Button creationButton, cancelButton;

    @FXML
    private Label errorLabel;

    /**
     * Attribute to access to the current user's methods
     */
    private final User user = UserFacade.getInstance().getCurrentUser();

    /**
     * Attribute to access to the QuickHelpPostFacade methods
     */
    private final QuickHelpPostFacade qhpFacade = QuickHelpPostFacade.getInstance();

    /**
     * Attribute to get the lists of subjects of the current user's department
     */
    private final List<Subject> subjects = SubjectFacade.getInstance().getAvailableSubjectsByDepartment(((StudentRole) user.getRole()).getDepartment());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSubjectBox();
    }

    /**
     * Method called by creationButton <br>
     * Verify if the subject and/or the description is empty and if not, create the quickHelpPost then reload the view
     */
    public void create() {
        if (subject.getValue() == null) {
            errorLabel.setText("Veuillez choisir une matière");
        }
        else if (description.getText().equals("")) {
            errorLabel.setText("Veuillez écrire une description");
        }
        else {
            qhpFacade.create(description.getText(), subject.getValue(), user);
            ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
        }
    }

    /**
     * Method called by initialize method <br>
     * Display a box which contains the list of subjects (attribute)
     */
    private void initializeSubjectBox() {
        Components.initializeSubjectBox(subjects, subject);
    }

    /**
     * Method called by cancelButton <br>
     * Cancel the creation action and come back to the home page of quick help posts
     */
    public void cancelCreation() {
        ViewLoader.getInstance().load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }
}
