package cooptool;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
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

        ViewLoader viewLoader = ViewLoader.getInstance();
        viewLoader.setStage(primaryStage);
        viewLoader.load(ViewPath.LOGIN);
        primaryStage.show();
    }
}
