package cooptool.business.controllers;

import cooptool.views.ViewLoader;
import cooptool.views.ViewPath;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RegisterController {

    public void login(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
