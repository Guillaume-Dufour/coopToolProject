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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {

    @FXML
    Text labelFirstName;
    @FXML
    Text labelLastName;
    @FXML
    Text labelMail;
    @FXML
    Text labelDescription;
    @FXML
    Text labelPromotion;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;

    UserFacade userFacade = UserFacade.getInstance();

    public void goToUpdatePage(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.UPDATE_STUDENT_ACCOUNT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToDeletePage(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.DELETE_ACCOUNT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = userFacade.getCurrentUser();
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
}
