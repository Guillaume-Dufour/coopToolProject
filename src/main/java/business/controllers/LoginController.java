package business.controllers;

import business.facades.UserFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    UserFacade userFacade;

    @FXML
    TextField mailField;

    @FXML
    TextField passwordField;

    public void login(ActionEvent event) {

    }

}
