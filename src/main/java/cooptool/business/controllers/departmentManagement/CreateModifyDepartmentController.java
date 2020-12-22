package cooptool.business.controllers.departmentManagement;

import cooptool.models.objects.Department;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CreateModifyDepartmentController implements Initializable {

    @FXML
    Text title;

    @FXML
    TextField inputName;

    @FXML
    TextField inputAbbreviation;

    @FXML
    TextField inputYear;

    @FXML
    Button validateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Department department = (Department) resources.getObject("1");

            inputName.setText(department.getSpeciality());
            inputAbbreviation.setText(department.getAbbreviation());
            inputYear.setText(String.valueOf(department.getYear()));

            validateButton.setText("Modifier");
            validateButton.setOnAction(this::updateDepartment);
        } catch (MissingResourceException e) {
            title.setText("Création de département");
            validateButton.setText("Valider");
            validateButton.setOnAction(this::createDepartment);
        }
    }

    public void createDepartment(ActionEvent event) {
        System.out.println("create");

    }

    public void updateDepartment(ActionEvent event) {
        System.out.println("update");
    }
}
