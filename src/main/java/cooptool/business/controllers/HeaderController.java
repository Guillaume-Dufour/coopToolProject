package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;

import java.io.IOException;

public class HeaderController {

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
        try {
            ViewLoader.getInstance().load(ViewPath.PROFIL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {

    }

}
