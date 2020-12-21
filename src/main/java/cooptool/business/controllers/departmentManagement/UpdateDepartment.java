package cooptool.business.controllers.departmentManagement;

import cooptool.models.objects.Department;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateDepartment implements Initializable {


    @FXML
    TextField inputName;

    @FXML
    TextField inputAbbreviation;

    @FXML
    TextField inputYear;

    @FXML
    Button updateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Department department = (Department) resources.getObject("1");

        inputName.setText(department.getSpeciality());
        inputAbbreviation.setText(department.getAbbreviation());
        inputYear.setText(String.valueOf(department.getYear()));
    }

    public void updateDepartment(ActionEvent event) {
    }
}
