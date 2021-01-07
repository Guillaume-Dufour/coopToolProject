package cooptool;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.NotificationFacade;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main of Coop'Tool Application
 */
public class CoopToolApplication extends Application {

    /**
     *
     * @param args Parameters of the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method set the primary stage and load the primary scene
     * @param primaryStage Stage of the window
     */
    @Override
    public void start(Stage primaryStage) {
        ViewLoader viewLoader = ViewLoader.getInstance();
        primaryStage.setTitle("Coop'Tool");
        primaryStage.setOnCloseRequest(event -> NotificationFacade.getInstance().stopTimer());
        primaryStage.getIcons().add(new Image("images/logo.JPG"));
        viewLoader.setStage(primaryStage);
        viewLoader.load(ViewPath.LOGIN);
        primaryStage.show();
    }
}
