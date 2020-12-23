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
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CreateModifySubjectController implements Initializable {

    @FXML
    Text title;

    @FXML
    TextField inputName;

    @FXML
    ComboBox<Department> listDepartments;

    @FXML
    Button validateButton;

    @FXML
    Text errorLabel;

    SubjectFacade subjectFacade = SubjectFacade.getInstance();
    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    Subject subject;

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

    public void createSubject(ActionEvent event) {

        try {
            subjectFacade.create(inputName.getText(), listDepartments.getValue());
            ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);

        } catch (SubjectNotConformed subjectNotConformed) {
            errorLabel.setText(subjectNotConformed.getMessage());
        }
    }


    public void udpateSubject(ActionEvent event) {

        try {
            subjectFacade.update(subject, inputName.getText(), listDepartments.getValue());
            ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);

        } catch (SubjectNotConformed subjectNotConformed) {
            errorLabel.setText(subjectNotConformed.getMessage());
        }
    }
}
