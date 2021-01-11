package cooptool.business;

import cooptool.utils.MapResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

/**
 * ViewLoader class
 */
public class ViewLoader extends Parent {

    /**
     * Instance of the ViewLoader
     */
    private static ViewLoader viewLoader = null;

    /**
     * Stage of the application
     */
    private Stage stage;

    /**
     * List of previous views of the application to make the history
     */
    private final Deque<ViewPath> previousView = new LinkedList<>();

    private ViewLoader() {}

    /**
     * Get the instance of the ViewLoader
     * @return Instance of the ViewLoader
     */
    public static ViewLoader getInstance() {

        if (viewLoader == null) {
            viewLoader = new ViewLoader();
        }

        return viewLoader;
    }

    /**
     * Set the stage of the ViewLoader
     * @param stage Stage of the application
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void load(ViewPath view, Object... objects) {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(view.getPath()));

        loader.setResources(new MapResourceBundle(objects));

        Parent root;

        previousView.add(view);

        try {
            root = loader.load();
            double height = stage.getScene() != null ? stage.getScene().getHeight() : Double.NaN;
            double width = stage.getScene() != null ? stage.getScene().getWidth() : Double.NaN;

            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get the previous view to go back
     * @return Previous ViewPath
     */
    public ViewPath getPreviousPath() {
        this.previousView.removeLast();
        return this.previousView.getLast();
    }
}
