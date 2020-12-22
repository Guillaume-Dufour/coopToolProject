package cooptool.business.controllers.userManagement.searchOtherStudent;

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
    Button deleteButton, updateButton, retourButton;
    @FXML
    Pane header_student, header_admin;

    UserFacade userFacade = UserFacade.getInstance();
    User user = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = (User)resources.getObject("1");
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
        }
        if (!user.equals(userFacade.getCurrentUser())){
            updateButton.setVisible(false);
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

    public void deleteAccount(ActionEvent event) {
        ViewLoader.getInstance().load(ViewPath.DELETE_ACCOUNT, user);
    }

    public void goToUpdatePage(ActionEvent event) {
        ViewLoader.getInstance().load(ViewPath.UPDATE_STUDENT_ACCOUNT, user);
    }

    public void goBack(ActionEvent event) {
        ViewLoader.getInstance().load(ViewLoader.getInstance().getPreviousPath(), ((StudentRole)user.getRole()).getDepartment());
    }
}
