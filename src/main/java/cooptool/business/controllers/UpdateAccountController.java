package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.StudentRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAccountController implements Initializable {

    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputPromotion;
    @FXML
    TextArea inputDescription;
    @FXML
    PasswordField inputOldPassword;
    @FXML
    PasswordField inputNewPassword;
    @FXML
    PasswordField inputNewConfirmedPassword;
    @FXML
    Button updateButton;
    @FXML
    Button cancelUpdateButton;


    UserFacade userFacade = UserFacade.getInstance();

    public void goToUpdatePage(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.HOME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelUpdate(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.HOME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAccount(ActionEvent event) {
        updateButton.setDisable(true);
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String promotion = inputPromotion.getText();
        String description = inputDescription.getText();
        String oldPassword = inputOldPassword.getText();
        String newPassword = inputNewPassword.getText();
        String newConfirmedPassword = inputNewConfirmedPassword.getText();
        if (oldPassword.equals("")){
            userFacade.updateAccount(firstName, lastName, null, description);
        } else {
            try {
                userFacade.updateAccount(firstName, lastName, null, description, oldPassword, newPassword, newConfirmedPassword);
            } catch (UnmatchedPassword unmatchedPassword) {
                unmatchedPassword.printStackTrace();
            }
        }
        try {
            ViewLoader.getInstance().load(ViewPath.HOME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputFirstName.setText(((StudentRole)userFacade.getCurrentUser().getRole()).getFirstName());
        inputLastName.setText(((StudentRole) userFacade.getCurrentUser().getRole()).getLastName());
        inputDescription.setText(((StudentRole) userFacade.getCurrentUser().getRole()).getDescription());
        inputPromotion.setText("on s'en balec");
    }

}
