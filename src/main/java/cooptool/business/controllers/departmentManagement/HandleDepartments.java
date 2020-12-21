package cooptool.business.controllers.departmentManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.controllers.userManagement.RegisterController;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;
import cooptool.utils.Components;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HandleDepartments implements Initializable {

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

    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();
    SubjectFacade subjectFacade = SubjectFacade.getInstance();

    Department department;
    List<Subject> listSubjects;

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
            listSubjects.addAll(subjectFacade.getSubjectsByDepartment(department));
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAllDepartments());

        updateButton.setVisible(false);
        deleteButton.setVisible(false);

    }
}
