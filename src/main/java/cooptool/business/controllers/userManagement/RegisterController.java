package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.*;
import cooptool.models.objects.Department;
import cooptool.utils.Components;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * RegisterController class
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField inputFirstName, inputLastName, inputMail;

    @FXML
    private PasswordField inputPassword, inputConfirmedPassword;

    @FXML
    private Text errorLabel;

    @FXML
    private Button buttonLogin, buttonRegister;

    @FXML
    private ComboBox<Department> listDepartments;

    /**
     * Attribute to access to the UserFacade method
     */
    private final UserFacade userFacade = UserFacade.getInstance();

    /**
     * Attribute to access to the DepartmentFacade method
     */
    private final DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    /**
     * Switch to the login view
     */
    public void goToLoginPage() {
        ViewLoader.getInstance().load(ViewPath.LOGIN);
    }

    /**
     * Method called by the buttonRegister <br>
     * Register a user with the provided information <br>
     * Send an email with a validation code <br>
     * Load the login view
     */
    public void register() {

        buttonRegister.setDisable(true);

        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String mail = inputMail.getText();
        Department department = listDepartments.getValue();
        String password = inputPassword.getText();
        String confirmedPassword = inputConfirmedPassword.getText();

        try {
            userFacade.register(firstName, lastName, mail, department, password, confirmedPassword);
            userFacade.sendValidationCode(mail);
            ViewLoader.getInstance().load(ViewPath.LOGIN);

        } catch (MailAlreadyExists | MailNotConformed | PasswordNotConformed | UnmatchedPassword | NameNotConformed e){
            buttonRegister.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAvailableDepartments());
    }

}
