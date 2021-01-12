package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotConformed;
import cooptool.exceptions.NameNotConformed;
import cooptool.exceptions.PasswordNotConformed;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.Department;
import cooptool.models.objects.StudentRole;
import cooptool.utils.Components;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * UpdateAccountController class
 */
public class UpdateAccountController implements Initializable {

    @FXML
    private TextField inputFirstName, inputLastName;

    @FXML
    private ComboBox<Department> listDepartments;

    @FXML
    private TextArea inputDescription;

    @FXML
    private PasswordField inputOldPassword, inputNewPassword, inputNewConfirmedPassword;

    @FXML
    private Text errorLabel;

    @FXML
    private Button updateButton, cancelUpdateButton;

    /**
     * Attribute to access to the UserFacade method
     */
    private final UserFacade userFacade = UserFacade.getInstance();

    /**
     * Attribute to access to the DepartmentFacade method
     */
    private final DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

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
            String description = inputDescription.getText();
            try {
                userFacade.updateAccount(firstName, lastName, department, description);
            } catch (MailNotConformed | NameNotConformed e) {
                errorLabel.setText(e.getMessage());
                updateButton.setDisable(false);
                return;
            }
        }
        String oldPassword = inputOldPassword.getText();
        String newPassword = inputNewPassword.getText();
        String newConfirmedPassword = inputNewConfirmedPassword.getText();
        if (!oldPassword.equals("")){
            try {
                userFacade.updatePassword(oldPassword, newPassword, newConfirmedPassword);
            } catch (UnmatchedPassword | PasswordNotConformed e) {
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
