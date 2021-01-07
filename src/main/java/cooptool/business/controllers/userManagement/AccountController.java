package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    Text labelLastName;
    @FXML
    Text labelFirstName;
    @FXML
    Text labelMail;
    @FXML
    Text labelPromotion;
    @FXML
    Text labelDescription;

    @FXML
    Button deleteButton;
    @FXML
    Button updateButton;
    @FXML
    Button backButton;
    @FXML
    Button historyButton;

    /**
     * Attribute to access to the UserFacade method
     */
    UserFacade userFacade = UserFacade.getInstance();
    /**
     * Attribute to stock the concerned student
     */
    User user = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (User)resources.getObject("1");
        if (!user.equals(userFacade.getCurrentUser())){
            updateButton.setVisible(false);
            historyButton.setVisible(false);
            if (userFacade.getCurrentUser().getRole() instanceof StudentRole){
                deleteButton.setVisible(false);
            }
        }
        labelFirstName.setText(((StudentRole) user.getRole()).getFirstName());
        labelLastName.setText(((StudentRole) user.getRole()).getLastName());
        labelMail.setText(user.getMail());
        labelPromotion.setText(((StudentRole) user.getRole()).getDepartment().toString());
        String description = ((StudentRole) user.getRole()).getDescription();
        if (description == null){
            description = "je n'ai pas encore de description";
        }
        labelDescription.setText(description);
    }

    /**
     * Method called by the deleteButton <br>
     * Load the view to delete an account
     */
    public void deleteAccount() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous confirmer la suppression de votre compte ?");

        alert.initOwner(ViewLoader.getInstance().getStage());

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
            userFacade.deleteAccount(user);
            if (userFacade.getCurrentUser() == null){
                ViewLoader.getInstance().load(ViewPath.LOGIN);
            } else {
                ViewLoader.getInstance().load(ViewPath.HOME);
            }
        }
    }

    /**
     * Method called by the updateButton <br>
     * Load the view to update an account
     */
    public void goToUpdatePage() {
        ViewLoader.getInstance().load(ViewPath.UPDATE_STUDENT_ACCOUNT, user);
    }

    /**
     * Method called by the backButton <br>
     * Load the previous view
     */
    public void goBack() {
        ViewLoader.getInstance().load(ViewLoader.getInstance().getPreviousPath(), ((StudentRole)user.getRole()).getDepartment());
    }

    /**
     * Method called by the historyButton <br>
     * Load the view to display the student's history
     */
    public void displayHistory() {
        ViewLoader.getInstance().load(ViewPath.HISTORY_DISPLAY, user);
    }
}
