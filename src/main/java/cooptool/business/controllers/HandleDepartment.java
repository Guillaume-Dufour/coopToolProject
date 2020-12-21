package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.models.objects.Department;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HandleDepartment implements Initializable {

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


    public void updateDepartment() {
        ViewLoader.getInstance().load(ViewPath.UPDATE_DEPARTMENT);
    }

    public void deleteDepartment() {
        ViewLoader.getInstance().load(ViewPath.DELETE_DEPARTMENT);
    }

    public void searchDepartment() {

        Department department = listDepartments.getValue();

        if (department == null) {
            errorLabel.setText("Veuillez sélectionner un département");
        }
        else {
            errorLabel.setText("");
            departmentInfos.setText(department.getSpeciality() + " " + department.getYear());
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listDepartments.setItems(FXCollections.observableList(departmentFacade.getAvailableDepartments()));

        listDepartments.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department object) {
                return object != null ? object.getAbbreviation() + object.getYear() : "Choisir un departement";
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });

        updateButton.setVisible(false);
        deleteButton.setVisible(false);

    }
}
