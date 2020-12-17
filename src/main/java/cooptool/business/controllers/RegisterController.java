package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.naming.Name;
import java.io.IOException;

public class RegisterController {

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

    }
}
