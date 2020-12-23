package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.StudentRole;

public class HeaderController {

    UserFacade userFacade = UserFacade.getInstance();

    public void goToHome() {
        ViewLoader.getInstance().load(ViewPath.HOME);
    }

    public void searchStudent() {ViewLoader.getInstance().load(ViewPath.SEARCH_STUDENT);}

    public void manageDepartment() {
        ViewLoader.getInstance().load(ViewPath.HANDLE_DEPARTMENTS);
    }

    public void goToProfil() {
        if (userFacade.getCurrentUser().getRole() instanceof StudentRole){
            ViewLoader.getInstance().load(ViewPath.STUDENT_PROFIL, userFacade.getCurrentUser());
        } else {
            ViewLoader.getInstance().load(ViewPath.UPDATE_ADMIN_ACCOUNT);
        }
    }

    public void disconnect() {
        userFacade.disconnect();
        ViewLoader.getInstance().load(ViewPath.LOGIN);
        System.out.println(userFacade.getCurrentUser());
    }

    public void privateMessage() {
    }
}
