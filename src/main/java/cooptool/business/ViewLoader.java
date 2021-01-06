package cooptool.business;

import cooptool.utils.MapResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class ViewLoader extends Parent {

    private static ViewLoader viewLoader = null;

    private Stage stage;

    private final LinkedList<ViewPath> previousView = new LinkedList<ViewPath>();

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
            double height = stage.getHeight();
            double width = stage.getWidth();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ViewPath getPreviousPath() {
        this.previousView.removeLast();
        return this.previousView.getLast();
    }

}
