package cooptool;

import cooptool.views.ViewLoader;
import cooptool.views.ViewPath;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main of Coop'Tool Application
 */
public class CoopToolApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //TODO: ViewLoader
        ViewLoader viewLoader = new ViewLoader(primaryStage.getScene());
        viewLoader.load(ViewPath.LOGIN);
        primaryStage.show();
    }
}
