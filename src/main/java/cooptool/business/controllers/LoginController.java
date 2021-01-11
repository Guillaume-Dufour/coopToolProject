package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.NotificationFacade;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.StudentRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * LoginController class
 */
public class LoginController implements Initializable{

    @FXML
    private TextField inputMail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Text errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button newPasswordButton;

    /**
     * Attribute to access to the UserFacade methods
     */
    UserFacade userFacade = UserFacade.getInstance();
    /**
     * Attribute to access to the notificationFacade methods
     */
    NotificationFacade notificationFacade = NotificationFacade.getInstance();

    /**
     * Method called by the loginButton
     * Checks the credentials inserted in the inputMail and inputPassword
     * If the system recognizes a student, it redirects him to student home page
     * If the system recognizes a student, it redirects him to admin home page
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
                if (userFacade.getCurrentUser().getRole() instanceof StudentRole) {
                    notificationFacade.searchNotifications(userFacade.getCurrentUser());
                }

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

    /**
     * Method called by the newPasswordButton
     * Switch to the forgot password view
     */
    public void handleNewPassword() {
        ViewLoader.getInstance().load(ViewPath.FORGOT_PASSWORD);
    }

    private void onEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }
}
