package cooptool.business.controllers;

import cooptool.business.facades.UserFacade;
import cooptool.models.objects.StudentRole;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    @FXML
    Pane header_student, header_admin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
        }
    }

    public void displayMentoringDemand() {

    }

    public void displayQuickHelp() {

    }
}
