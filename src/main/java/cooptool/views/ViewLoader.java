package cooptool.views;

import cooptool.CoopToolApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ViewLoader extends Parent {
    private Scene main;
    //TODO: ViewLoader fonctionne pas
    public ViewLoader(Scene primaryScene){
        this.main = primaryScene;
    }

    public void load(ViewPath view) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CoopToolApplication.class.getClassLoader().getResource(view.getPath()));
        main.setRoot(loader.load());
    }

}
