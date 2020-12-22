package cooptool.business.controllers.departmentManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;
import cooptool.utils.Components;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    TableView<Subject> listSubjects;

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
            subjects.clear();
            subjects.addAll(subjectFacade.getSubjectsByDepartment(department));
            updateButton.setVisible(true);
            deleteButton.setVisible(true);

            listSubjects.setItems(FXCollections.observableList(subjects));
            listSubjects.setVisible(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAllDepartments());

        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        listSubjects.setVisible(false);

        listSubjects.getColumns().clear();

        TableColumn<Subject, String> nameSubjectCol = new TableColumn<>("Nom");
        TableColumn<Subject, Integer> availableSubjectCol = new TableColumn<>("Disponibilité");

        nameSubjectCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameSubjectCol.setCellFactory(TextFieldTableCell.forTableColumn());

        availableSubjectCol.setCellValueFactory(new PropertyValueFactory<>("available"));

        availableSubjectCol.setCellFactory(param -> new TableCell<>() {

            private final Button availableButton = new Button();

            {
                availableButton.setOnAction(event -> {
                    updateAvailabilitySubject(event, param.getTableView().getItems().get(getIndex()));
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    if (item == 1) {
                        availableButton.setText("Disponible");
                        availableButton.setStyle("-fx-background-color: #51e056");
                    }
                    else {
                        availableButton.setText("Indisponible");
                        availableButton.setStyle("-fx-background-color: #f0524d");
                    }

                    setGraphic(availableButton);
                }
            }
        });

        listSubjects.getColumns().add(nameSubjectCol);
        listSubjects.getColumns().add(availableSubjectCol);
    }

    public void updateAvailabilityDepartment(ActionEvent event, Department department) {

    }

    public void updateAvailabilitySubject(ActionEvent event, Subject subject) {

    }
}
