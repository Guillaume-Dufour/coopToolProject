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

public class newPasswordController {

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

    UserFacade userFacade = UserFacade.getInstance();

    public void handleNewPassword(ActionEvent event) {
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

    public void goToLogin(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
