package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailAlreadyExists;
import cooptool.exceptions.MailNotConformed;
import cooptool.exceptions.PasswordNotConformed;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.Department;
import cooptool.utils.Components;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    TextField inputFirstName, inputLastName, inputMail;
    @FXML
    PasswordField inputPassword, inputConfirmedPassword;
    @FXML
    Text errorLabel;
    @FXML
    Button buttonLogin, buttonRegister;
    @FXML
    ComboBox<Department> listDepartments;

    UserFacade userFacade = UserFacade.getInstance();
    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    public void goToLoginPage() {
        ViewLoader.getInstance().load(ViewPath.LOGIN);
    }

    public void register(ActionEvent event) {
        System.out.println(event);
        buttonRegister.setDisable(true);
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String mail = inputMail.getText();
        Department department = listDepartments.getValue();
        System.out.println(department);
        String password = inputPassword.getText();
        String confirmedPassword = inputConfirmedPassword.getText();
        try {
            userFacade.register(firstName, lastName, mail, department, password, confirmedPassword);
            userFacade.sendValidationCode(mail);
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (MailAlreadyExists | MailNotConformed |PasswordNotConformed | UnmatchedPassword e){
            buttonRegister.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputFirstName.setText("mathilde");
        inputLastName.setText("tribot");
        inputMail.setText("mathilde.tribot@etu.umontpellier.fr");
        inputPassword.setText("guillaume");
        inputConfirmedPassword.setText("guillaume");

        Components.createDepartmentComboBox(listDepartments, departmentFacade.getAvailableDepartments());
    }

}
