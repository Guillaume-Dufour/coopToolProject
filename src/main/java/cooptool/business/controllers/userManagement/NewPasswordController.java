package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailNotFound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class NewPasswordController {

    @FXML
    TextField inputMail;
    @FXML
    Button confirmedButton;
    @FXML
    Button cancelButton;
    @FXML
    Text resultLabel;
    @FXML
    Text errorLabel;

    /**
     * Attribute to access to the UserFacade method
     */
    UserFacade userFacade = UserFacade.getInstance();

    /**
     * Method called by the confirmedButton <br>
     * Change the password of the user corresponding to the selected mail <br>
     * Switch to the login view
     */
    public void handleNewPassword() {
        confirmedButton.setDisable(true);
        String mail = inputMail.getText();
        try {
            userFacade.forgotPassword(mail);
            confirmedButton.setVisible(false);
            resultLabel.setText("vous avez recu un nouveau mot de passe par mail");
        } catch (MailNotFound mailNotFound) {
            errorLabel.setText(mailNotFound.getMessage());
            confirmedButton.setDisable(false);
        }
    }

    /**
     * Switch to the login view
     */
    public void goToLogin() {
        ViewLoader.getInstance().load(ViewPath.LOGIN);
    }
}
