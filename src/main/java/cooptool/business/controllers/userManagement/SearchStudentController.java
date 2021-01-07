package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Department;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.User;
import cooptool.utils.Components;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchStudentController implements Initializable {

    @FXML
    ComboBox<Department> listDepartments;
    @FXML
    Text errorLabel;
    @FXML
    Button validateButton;
    @FXML
    ListView<User> studentListView;

    /**
     * Attribute to access to the DepartmentFacade method
     */
    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();
    /**
     * Attribute to access to the UserFacade method
     */
    UserFacade userFacade = UserFacade.getInstance();

    /**
     * Attribute to stock the selected department
     */
    Department department;

    /**
     * Method called by the validateButton <br>
     * If a department is chosen, we retrieve all the student of the department and we display them <br>
     * You can click on a student to see his profile
     */
    public void searchStudent() {
        department = listDepartments.getValue();
        if (department == null) {
            errorLabel.setText("Veuillez sélectionner un département");
        }
        else {
            errorLabel.setText("");
            ObservableList<User> items = FXCollections.observableList(userFacade.findStudentByDepartment(department));
            studentListView.setItems(items);
            studentListView.setCellFactory(lv -> new ListCell<User>() {
                        @Override
                        public void updateItem(User row, boolean empty) {
                            super.updateItem(row, empty) ;
                            setText(empty ? null : ((StudentRole)row.getRole()).getFirstName() + " " +((StudentRole) row.getRole()).getLastName());
                        }
                    });
            studentListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends User> ov, User old_val, User new_val) -> {
                User selectedItem = studentListView.getSelectionModel().getSelectedItem();
                ViewLoader.getInstance().load(ViewPath.STUDENT_PROFIL, selectedItem);
            });
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAllDepartments());
        if (resources.containsKey("1")) {
            listDepartments.getSelectionModel().select((Department) resources.getObject("1"));
            this.searchStudent();
        }
    }
}
