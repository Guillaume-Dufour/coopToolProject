package cooptool.business;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewLoader extends Parent {

    private static ViewLoader viewLoader = null;

    private Stage stage;

    private ViewLoader() {

    }

    public static ViewLoader getInstance() {

        if (viewLoader == null) {
            viewLoader = new ViewLoader();
        }

        return viewLoader;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void load(ViewPath view) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(view.getPath()));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
    }

}
