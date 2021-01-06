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

    /**
     * Attribute to access to the UserFacade method
     */
    UserFacade userFacade = UserFacade.getInstance();
    /**
     * Attribute to stock the concerned student
     */
    User user = null;

    /**
     * Method called by the deleteButton
     * Delete the user account
     * Switch to the login view or the home view, depending of the state of the current user
     */
    public void deleteAccount() {
        deleteButton.setDisable(true);
        userFacade.deleteAccount(user);
        if (userFacade.getCurrentUser() == null){
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } else {
            ViewLoader.getInstance().load(ViewPath.HOME);
        }
    }

    /**
     * Switch to the home view
     */
    public void cancelDelete() {
        ViewLoader.getInstance().load(ViewPath.HOME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (User) resources.getObject("1");
    }
}
