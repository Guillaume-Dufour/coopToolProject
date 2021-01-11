package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * ValidateController class
 */
public class ValidateController {

    @FXML
    private TextField inputCode;

    @FXML
    private Button validateButton, cancelVerification;

    @FXML
    private Text errorLabel;

    /**
     * Attribute to access to the UserFacade method
     */
    private final UserFacade userFacade = UserFacade.getInstance();

    /**
     * Method called by the validateButton <br>
     * Check if the provided code corresponds to the user's validation code <br>
     * If it's ok, switch to the home view
     */
    public void handleValidationCode() {
        validateButton.setDisable(true);
        int testedCode = 0;
        try {
            testedCode = Integer.parseInt(inputCode.getText());
        } catch (final NumberFormatException e){
            errorLabel.setText("Veuillez entrer un code");
            validateButton.setDisable(false);
            return;
        }
        if (userFacade.checkValidationCode(testedCode)){
            userFacade.updateValidation();
            ViewLoader.getInstance().load(ViewPath.HOME);
        } else {
            errorLabel.setText("Mauvais code");
            validateButton.setDisable(false);
        }
    }

    /**
     * Method called by the cancelVerificationButton
     * Switch to the login view
     */
    public void cancelVerification() {
        userFacade.disconnect();
        ViewLoader.getInstance().load(ViewPath.LOGIN);
    }
}
