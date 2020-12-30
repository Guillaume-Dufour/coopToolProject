package cooptool.business;

import cooptool.utils.MapResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class ViewLoader extends Parent {

    private static ViewLoader viewLoader = null;

    private Stage stage;

    private final LinkedList<ViewPath> previousView = new LinkedList<>();

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
            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            Scene scene = new Scene(scrollPane, 800, 600);
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
