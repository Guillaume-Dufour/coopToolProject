package cooptool.business.controllers;

import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    TextField inputMail;
    @FXML
    PasswordField inputPassword;
    @FXML
    Text errorLabel;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    Button newPasswordButton;

    UserFacade userFacade = UserFacade.getInstance();

    /**
     * Method called by the loginButton
     * Checks the credentials inserted in the inputMail and inputPassword
     * If the system recognizes a student ->  redirects him to student home page
     * If the system recognizes a student ->  redirects him to admin home page
     * Else change the text of the errorLabel with an according error message
     */
    public void login() {
        loginButton.setDisable(true);
        String mail = inputMail.getText();
        String password = inputPassword.getText();
        try {
            userFacade.login(mail, password);
            if (userFacade.getCurrentUser().getValidate() == 0){
                ViewLoader.getInstance().load(ViewPath.VALIDATE);
            } else {
                ViewLoader.getInstance().load(ViewPath.HOME);
            }
        } catch(MailNotFound | UnmatchedPassword e) {
            loginButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
    }

    /**
     * Method called by the register button
     * Switch the user scene to the register page
     */
    public void goToRegisterPage(ActionEvent event) {
        System.out.println(event);
        registerButton.setDisable(true);
        ViewLoader.getInstance().load(ViewPath.REGISTER);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputMail.setText("mathilde.tribot@etu.umontpellier.fr");
        inputPassword.setText("guillaume");

        inputMail.setOnKeyPressed(this::onEnter);
        inputPassword.setOnKeyPressed(this::onEnter);
    }

    public void handleNewPassword() {
        ViewLoader.getInstance().load(ViewPath.FORGOT_PASSWORD);
    }

    private void onEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }
}
