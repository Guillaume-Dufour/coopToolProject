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
    Button createDepartmentButton;

    @FXML
    Button createSubjectButton;

    @FXML
    ComboBox<Department> listDepartments;

    @FXML
    Text errorLabel;

    @FXML
    Text departmentInfos;

    @FXML
    Button updateButton;

    @FXML
    Button availableDepartmentButton;

    @FXML
    TableView<Subject> listSubjects;

    @FXML
    TableColumn<Subject, String> nameSubjectCol;

    @FXML
    TableColumn<Subject, Integer> availableSubjectCol;

    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();
    SubjectFacade subjectFacade = SubjectFacade.getInstance();

    Department department;
    List<Subject> subjects = new ArrayList<>();

    public void updateDepartment() {
        ViewLoader.getInstance().load(ViewPath.CREATE_MODIFY_DEPARTMENT, department);
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
            Components.createAvailabilityButton(availableDepartmentButton, department.getAvailable());
            availableDepartmentButton.setOnAction(event -> updateAvailabilityDepartment(event, department));
            availableDepartmentButton.setVisible(true);

            listSubjects.setItems(FXCollections.observableList(subjects));
            listSubjects.setVisible(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAllDepartments());

        updateButton.setVisible(false);
        availableDepartmentButton.setVisible(false);
        listSubjects.setVisible(false);

        nameSubjectCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameSubjectCol.setCellFactory(TextFieldTableCell.forTableColumn());

        availableSubjectCol.setCellValueFactory(new PropertyValueFactory<>("available"));

        availableSubjectCol.setCellFactory(param -> new TableCell<>() {

            private final Button availableButton = new Button();

            {
                availableButton.setOnAction(event -> updateAvailabilitySubject(event, param.getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    Components.createAvailabilityButton(availableButton, item);

                    setGraphic(availableButton);
                }
            }
        });

        listSubjects.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ViewLoader.getInstance().load(ViewPath.CREATE_MODIFY_SUBJECT, listSubjects.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void updateAvailabilityDepartment(ActionEvent event, Department department) {

        int available = department.changeAvailability();
        departmentFacade.updateAvailability(department);

        Components.createAvailabilityButton(((Button)event.getSource()), available);
    }

    public void updateAvailabilitySubject(ActionEvent event, Subject subject) {

        int available = subject.changeAvailability();
        subjectFacade.updateAvailability(subject);

        Components.createAvailabilityButton(((Button)event.getSource()), available);
    }

    public void goToCreateDepartmentPage() {
        ViewLoader.getInstance().load(ViewPath.CREATE_MODIFY_DEPARTMENT);
    }

    public void goToCreateSubjectPage() {
        ViewLoader.getInstance().load(ViewPath.CREATE_MODIFY_SUBJECT);
    }
}
