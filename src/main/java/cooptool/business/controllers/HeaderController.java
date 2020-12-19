package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.StudentRole;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HeaderController {

    UserFacade userFacade = UserFacade.getInstance();

    public void goToHome() {
        try {
            ViewLoader.getInstance().load(ViewPath.HOME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchStudent() {

    }

    public void manageDepartment() {

    }

    public void goToProfil() {
        if (userFacade.getCurrentUser().getRole() instanceof StudentRole){
            try {
                ViewLoader.getInstance().load(ViewPath.STUDENT_PROFIL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ViewLoader.getInstance().load(ViewPath.UPDATE_ADMIN_ACCOUNT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {

    }

    public void privateMessage(MouseEvent mouseEvent) {
    }
}
