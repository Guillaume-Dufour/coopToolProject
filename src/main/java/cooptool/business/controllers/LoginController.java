package cooptool.business.controllers;

import cooptool.Session;
import cooptool.business.facades.UserFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import cooptool.models.objects.User;

public class LoginController {

    UserFacade userFacade;

    @FXML
    TextField inputMail;

    @FXML
    TextField inputPassword;

    public LoginController() {
        this.userFacade = new UserFacade();
    }

    public void login(ActionEvent event) {
        String mail = inputMail.getText();
        String password = inputPassword.getText();
        try {
            User user = userFacade.login(mail, password);
            Session.getInstance().setUser(user);
        } catch(Exception e) {

        }

    }

}
