package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.PasswordNotConformed;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.Department;
import cooptool.models.objects.StudentRole;
import cooptool.utils.Components;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAccountController implements Initializable {

    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputLastName;
    @FXML
    ComboBox<Department> listDepartments;
    @FXML
    TextArea inputDescription;
    @FXML
    PasswordField inputOldPassword;
    @FXML
    PasswordField inputNewPassword;
    @FXML
    PasswordField inputNewConfirmedPassword;
    @FXML
    Text errorLabel;
    @FXML
    Button updateButton;
    @FXML
    Button cancelUpdateButton;

    /**
     * Attribute to access to the UserFacade method
     */
    UserFacade userFacade = UserFacade.getInstance();
    /**
     * Attribute to access to the DepartmentFacade method
     */
    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    /**
     * Method called by the cancelUpdateButton <br>
     * Switch to the home view
     */
    public void cancelUpdate() {
        ViewLoader.getInstance().load(ViewPath.HOME);
    }

    /**
     * Method called by the updateButton <br>
     * Update the account of the user with the provided information <br>
     * Update the password of the user if a new conformed password is provided <br>
     * Load the home view
     */
    public void updateAccount() {
        updateButton.setDisable(true);
        if(userFacade.getCurrentUser().getRole() instanceof StudentRole){
            String firstName = inputFirstName.getText();
            String lastName = inputLastName.getText();
            Department department = listDepartments.getValue();
            System.out.println("je suis dans le controller");
            System.out.println(department);
            String description = inputDescription.getText();
            userFacade.updateAccount(firstName, lastName, department, description);
        }
        String oldPassword = inputOldPassword.getText();
        String newPassword = inputNewPassword.getText();
        String newConfirmedPassword = inputNewConfirmedPassword.getText();
        if (!oldPassword.equals("")){
            try {
                userFacade.updatePassword(oldPassword, newPassword, newConfirmedPassword);
            } catch (UnmatchedPassword | PasswordNotConformed e) {
                System.out.println("je suis dans le catch");
                System.out.println(e);
                errorLabel.setText(e.getMessage());
                updateButton.setDisable(false);
                return;
            }
        }
        ViewLoader.getInstance().load(ViewPath.HOME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.getCurrentUser().getRole() instanceof StudentRole){
            inputFirstName.setText(((StudentRole)userFacade.getCurrentUser().getRole()).getFirstName());
            inputLastName.setText(((StudentRole) userFacade.getCurrentUser().getRole()).getLastName());
            inputDescription.setText(((StudentRole) userFacade.getCurrentUser().getRole()).getDescription());
            Components.createDepartmentComboBox(listDepartments, departmentFacade.getAvailableDepartments());
            listDepartments.getSelectionModel().select(((StudentRole) userFacade.getCurrentUser().getRole()).getDepartment());
        }
    }

}
