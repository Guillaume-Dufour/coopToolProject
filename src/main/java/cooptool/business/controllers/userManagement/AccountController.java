package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    Text labelLastName, labelFirstName, labelMail, labelPromotion, labelDescription;
    @FXML
    Button deleteButton, updateButton, retourButton, historyButton;

    UserFacade userFacade = UserFacade.getInstance();
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
     * load la page
     */
    public void deleteAccount() {
        ViewLoader.getInstance().load(ViewPath.DELETE_ACCOUNT, user);
    }

    public void goToUpdatePage() {
        ViewLoader.getInstance().load(ViewPath.UPDATE_STUDENT_ACCOUNT, user);
    }

    public void goBack() {
        ViewLoader.getInstance().load(ViewLoader.getInstance().getPreviousPath(), ((StudentRole)user.getRole()).getDepartment());
    }

    public void displayHistory() {
        ViewLoader.getInstance().load(ViewPath.HISTORY_DISPLAY, user);
    }
}
