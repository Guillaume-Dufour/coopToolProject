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
    Button deleteAccountButton;
    @FXML
    Pane header_student, header_admin;

    UserFacade userFacade = UserFacade.getInstance();
    User user = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
            deleteAccountButton.setVisible(false);
        } else {
            header_student.setVisible(false);
        }
        user = (User)resources.getObject("1");
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
        deleteAccountButton.setDisable(true);
        userFacade.deleteAccount(user);
        ViewLoader.getInstance().load(ViewPath.SEARCH_STUDENT, ((StudentRole)user.getRole()).getDepartment());
    }


    public void goBack(ActionEvent event) {
        ViewLoader.getInstance().load(ViewPath.SEARCH_STUDENT, ((StudentRole)user.getRole()).getDepartment());
    }
}
