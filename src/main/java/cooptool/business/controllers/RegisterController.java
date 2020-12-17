package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Department;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.naming.Name;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputMail;
    @FXML
    TextField inputPromotion;
    @FXML
    PasswordField inputPassword;
    @FXML
    PasswordField inputConfirmedPassword;
    @FXML
    Text errorLabel;
    @FXML
    Button buttonLogin;
    @FXML
    Button buttonRegister;

    UserFacade userFacade = UserFacade.getInstance();
    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    public void goToLoginPage(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(ActionEvent event) {
        System.out.println(event);
        buttonRegister.setDisable(true);
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String mail = inputMail.getText();
        String promotion = inputPromotion.getText();
        String password = inputPassword.getText();
        String confirmedPassword = inputConfirmedPassword.getText();
        try {
            Department department = departmentFacade.getDepartment();
            userFacade.register(firstName, lastName, mail, department, password, confirmedPassword);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputFirstName.setText("mathilde");
        inputLastName.setText("tribot");
        inputMail.setText("math.tribot@etu.umontpellier.fr");
        inputPromotion.setText("IG3");
        inputPassword.setText("guillaume");
        inputConfirmedPassword.setText("guillaume");
    }
}
