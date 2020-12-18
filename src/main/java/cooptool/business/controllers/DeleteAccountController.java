package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class DeleteAccountController {

    @FXML
    Button deleteButton;
    @FXML
    Button cancelDeleteButton;

    UserFacade userFacade = UserFacade.getInstance();

    public void deleteAccount(ActionEvent event) {
        deleteButton.setDisable(true);
        userFacade.deleteAccount();
        System.out.println("account deleted");
        try {
            ViewLoader.getInstance().load(ViewPath.LOGIN);
            System.out.println(userFacade.getCurrentUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelDelete(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.HOME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
