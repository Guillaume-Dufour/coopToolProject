package business.controllers;

import business.facades.UserFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    UserFacade userFacade;

    @FXML
    TextField inputMail;

    @FXML
    TextField inputPassword;

    public void login(ActionEvent event) {

    }

}
