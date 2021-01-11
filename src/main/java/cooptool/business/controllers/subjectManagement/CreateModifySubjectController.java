package cooptool.business.controllers.subjectManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.exceptions.SubjectNotConformed;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;
import cooptool.utils.Components;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

/**
 * CreateModifySubjectController class
 */
public class CreateModifySubjectController implements Initializable {

    @FXML
    private Text title, errorLabel;

    @FXML
    private TextField inputName;

    @FXML
    private ComboBox<Department> listDepartments;

    @FXML
    private Button validateButton;

    /**
     * Attribute to access to the SubjectFacade
     */
    private final SubjectFacade subjectFacade = SubjectFacade.getInstance();

    /**
     * Attribute to access to the DepartmentFacade
     */
    private final DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    /**
     * Subject
     */
    private Subject subject;

    /**
     * Create a subject from the fields
     * @param event Action event
     */
    public void createSubject(ActionEvent event) {

        try {
            subjectFacade.create(inputName.getText(), listDepartments.getValue());
            ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);

        } catch (SubjectNotConformed subjectNotConformed) {
            errorLabel.setText(subjectNotConformed.getMessage());
        }
    }


    /**
     * Update the subject from the fields
     * @param event Action event
     */
    public void udpateSubject(ActionEvent event) {

        try {
            subjectFacade.update(subject, inputName.getText(), listDepartments.getValue());
            ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);

        } catch (SubjectNotConformed subjectNotConformed) {
            errorLabel.setText(subjectNotConformed.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAllDepartments());

        try {
            subject = (Subject) resources.getObject("1");

            title.setText("Modification de matière");
            inputName.setText(subject.getName());
            listDepartments.getSelectionModel().select(subject.getDepartment());
            validateButton.setText("Modifier");
            validateButton.setOnAction(this::udpateSubject);

        } catch (MissingResourceException e) {

            title.setText("Création de matière");
            validateButton.setText("Valider");
            validateButton.setOnAction(this::createSubject);
        }
    }
}
