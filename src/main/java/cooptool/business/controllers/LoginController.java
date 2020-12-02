package cooptool.business.controllers;

import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.StudentRole;
import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.models.objects.StudentRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginController {

    @FXML
    TextField inputMail;

    @FXML
    TextField inputPassword;

    @FXML
    Text errorLabel;

    @FXML
    Button loginButton;

    @FXML
    Button registerButton;

    UserFacade userFacade = UserFacade.getInstance();

    public void login(ActionEvent event) {
        loginButton.setDisable(true);
        String mail = inputMail.getText();
        String password = inputPassword.getText();
        try {
            userFacade.login(mail, password);
            if(userFacade.getCurrentUser().getRole() instanceof StudentRole){
                ViewLoader.getInstance().load(ViewPath.STUDENT_HOME);
            } else {
                ViewLoader.getInstance().load(ViewPath.ADMIN_HOME);
            }
        } catch(MailNotFound | UnmatchedPassword | IOException e) {
            loginButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
    }

    public void goToRegisterPage(ActionEvent event) {
        registerButton.setDisable(true);
        try {
            ViewLoader.getInstance().load(ViewPath.REGISTER);
        } catch (IOException e) {
            registerButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
    }

}
