package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class ValidateController {

    @FXML
    TextField inputCode;
    @FXML
    Button validateButton;
    @FXML
    Text errorLabel;
    @FXML
    Button cancelVerification;

    UserFacade userFacade = UserFacade.getInstance();

    public void handleValidationCode(ActionEvent event) {
        validateButton.setDisable(true);
        int testedCode = 0;
        try {
            testedCode = Integer.parseInt(inputCode.getText());
        } catch (final NumberFormatException e){
            errorLabel.setText("veuillez entrer un code");
            validateButton.setDisable(false);
            return;
        }
        if (userFacade.checkValidationCode(testedCode)){
            try {
                userFacade.updateValidation();
                ViewLoader.getInstance().load(ViewPath.HOME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("mauvais code");
            validateButton.setDisable(false);
        }
    }

    public void cancelVerification(ActionEvent event) {
        userFacade.disconnect();
        try {
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(userFacade.getCurrentUser());
    }
}