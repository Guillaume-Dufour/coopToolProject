package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RegisterController {


    public void goToLoginPage(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
