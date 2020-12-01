package cooptool.business.controllers;

import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.views.ViewLoader;
import cooptool.views.ViewPath;
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
            ViewLoader.getInstance().load(ViewPath.HOME);
        } catch(MailNotFound | UnmatchedPassword | IOException e) {
            loginButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
    }

    public void register(ActionEvent event) {
        registerButton.setDisable(true);
        try {
            ViewLoader.getInstance().load(ViewPath.REGISTER);
        } catch (IOException e) {
            loginButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
    }

}
