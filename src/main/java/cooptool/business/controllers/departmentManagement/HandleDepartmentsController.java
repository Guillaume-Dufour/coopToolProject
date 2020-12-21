package cooptool.business.controllers.departmentManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;
import cooptool.utils.Components;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HandleDepartmentsController implements Initializable {

    @FXML
    ComboBox<Department> listDepartments;

    @FXML
    Text errorLabel;

    @FXML
    Text departmentInfos;

    @FXML
    Button updateButton;

    @FXML
    Button deleteButton;

    @FXML
    GridPane listSubjects;

    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();
    SubjectFacade subjectFacade = SubjectFacade.getInstance();

    Department department;
    List<Subject> subjects = new ArrayList<>();

    public void updateDepartment() {
        ViewLoader.getInstance().load(ViewPath.UPDATE_DEPARTMENT, department);
    }

    public void deleteDepartment() {
    }

    public void searchDepartment() {

        department = listDepartments.getValue();

        if (department == null) {
            errorLabel.setText("Veuillez sélectionner un département");
        }
        else {
            errorLabel.setText("");
            departmentInfos.setText(department.getSpeciality() + " " + department.getYear());
            subjects.addAll(subjectFacade.getSubjectsByDepartment(department));
            updateButton.setVisible(true);
            deleteButton.setVisible(true);

            for (Subject s: subjects) {

            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAllDepartments());

        updateButton.setVisible(false);
        deleteButton.setVisible(false);
    }
}
