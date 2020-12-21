package cooptool.business.controllers.userManagement;

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
        ViewLoader.getInstance().load(ViewPath.LOGIN);
    }

    public void cancelDelete(ActionEvent event) {
        ViewLoader.getInstance().load(ViewPath.HOME);
    }
}
