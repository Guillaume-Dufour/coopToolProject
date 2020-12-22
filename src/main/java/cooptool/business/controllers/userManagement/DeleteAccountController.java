package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccountController implements Initializable {

    @FXML
    Button deleteButton;
    @FXML
    Button cancelDeleteButton;

    UserFacade userFacade = UserFacade.getInstance();
    User user = null;

    public void deleteAccount(ActionEvent event) {
        deleteButton.setDisable(true);
        userFacade.deleteAccount(user);
        if (userFacade.getCurrentUser() == null){
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } else {
            ViewLoader.getInstance().load(ViewPath.HOME);
        }
    }

    public void cancelDelete(ActionEvent event) {
        ViewLoader.getInstance().load(ViewPath.HOME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (User) resources.getObject("1");
    }
}
