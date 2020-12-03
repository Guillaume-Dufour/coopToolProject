package cooptool;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main of Coop'Tool Application
 */
public class CoopToolApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method set the primary stage and load the primary scene
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        ViewLoader viewLoader = ViewLoader.getInstance();
        viewLoader.setStage(primaryStage);
        viewLoader.load(ViewPath.LOGIN);
        primaryStage.show();
    }
}
