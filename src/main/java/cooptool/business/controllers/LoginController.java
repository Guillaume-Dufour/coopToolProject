package cooptool.business.controllers;

import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.StudentRole;
import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginController {

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

    UserFacade userFacade = UserFacade.getInstance();

    /**
     * Method called by the loginButton
     * Checks the credentials inserted in the inputMail and inputPassword
     * If the system recognizes a student ->  redirects him to student home page
     * If the system recognizes a student ->  redirects him to admin home page
     * Else change the text of the errorLabel with an according error message
     */
    public void login(ActionEvent event) {
        System.out.println(event);
        loginButton.setDisable(true);
        String mail = inputMail.getText();
        String password = inputPassword.getText();
        try {
            userFacade.login(mail, password);
            ViewLoader.getInstance().load(ViewPath.HOME);
            /*if(userFacade.getCurrentUser().getRole() instanceof StudentRole){
                ViewLoader.getInstance().load(ViewPath.STUDENT_HOME);
            } else {
                ViewLoader.getInstance().load(ViewPath.ADMIN_HOME);
            }*/
        } catch(MailNotFound | UnmatchedPassword e) {
            loginButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }
        catch(IOException e){
            e.printStackTrace();
            loginButton.setDisable(false);
            errorLabel.setText("Internal Error, try again later");
        }
    }

    /**
     * Method called by the register button
     * Switch the user scene to the register page
     */
    public void goToRegisterPage(ActionEvent event) {
        System.out.println(event);
        registerButton.setDisable(true);
        try {
            ViewLoader.getInstance().load(ViewPath.REGISTER);
        }catch(IOException e){
            e.printStackTrace();
            registerButton.setDisable(false);
            errorLabel.setText("Internal Error, try again later");
        }
    }
}
