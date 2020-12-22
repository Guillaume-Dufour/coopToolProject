package cooptool.business.controllers.subjectManagement;

import cooptool.business.facades.DepartmentFacade;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;
import cooptool.utils.Components;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateModifySubjectController implements Initializable {

    @FXML
    Text title;

    @FXML
    TextField inputName;

    @FXML
    ComboBox<Department> listDepartments;

    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Subject subject = (Subject) resources.getObject("1");

        inputName.setText(subject.getName());

        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAllDepartments());

        listDepartments.getSelectionModel().select(subject.getDepartment());
    }

    public void updateDepartment(ActionEvent event) {
    }
}
