package cooptool.business.controllers;

import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import cooptool.models.objects.User;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    TextField inputMail;

    @FXML
    TextField inputPassword;

    @FXML
    Text errorLabel;

    @FXML
    Button loginButton;

    UserFacade userFacade = UserFacade.getInstance();

    public void login(ActionEvent event) {
        loginButton.setDisable(true);
        String mail = inputMail.getText();
        String password = inputPassword.getText();
        try {
            userFacade.login(mail, password);
            //TODO : comment changer de page
        } catch(MailNotFound | UnmatchedPassword e) {
            loginButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }

    }

}
