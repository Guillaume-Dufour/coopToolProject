package cooptool.business.controllers;

import cooptool.Session;
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

    UserFacade userFacade;

    @FXML
    TextField inputMail;

    @FXML
    TextField inputPassword;

    @FXML
    Text errorLabel;

    @FXML
    Button loginButton;

    public LoginController() {
        this.userFacade = new UserFacade();
    }

    public void login(ActionEvent event) {
        loginButton.setDisable(true);
        String mail = inputMail.getText();
        String password = inputPassword.getText();
        try {
            User user = userFacade.login(mail, password);
            Session.getInstance().setUser(user);
            //TODO : comment changer de page
        } catch(MailNotFound | UnmatchedPassword e) {
            loginButton.setDisable(false);
            errorLabel.setText(e.getMessage());
        }

    }

}
