package cooptool.business.controllers.departmentManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.exceptions.DepartmentNotConformed;
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

    @FXML
    Text errorLabel;

    /**
     * Attribute to access to the department facade
     */
    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    /**
     * Department
     */
    Department department;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            department = (Department) resources.getObject("1");

            title.setText("Modification de départment");
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

    /**
     * Create a department from the fields
     * @param event Action
     */
    public void createDepartment(ActionEvent event) {

        try {

            String name = inputName.getText();
            String abbreviation = inputAbbreviation.getText();
            int year = Integer.parseInt(inputYear.getText());
            departmentFacade.create(name, abbreviation, year);

            ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);

        } catch (NumberFormatException | DepartmentNotConformed e) {
            errorLabel.setText(e.getMessage());
        }
    }

    /**
     * Apdate a department from the fields
     * @param event Action
     */
    public void updateDepartment(ActionEvent event) {

        try {

            String name = inputName.getText();
            String abbreviation = inputAbbreviation.getText();
            int year = Integer.parseInt(inputYear.getText());
            departmentFacade.update(department, name, abbreviation, year);

            ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);

        } catch (NumberFormatException | DepartmentNotConformed e) {
            errorLabel.setText("Champ(s) non conforme(s)");
        }
    }
}
