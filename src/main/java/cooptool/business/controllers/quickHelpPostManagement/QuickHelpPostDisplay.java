package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.StudentRole;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class    QuickHelpPostDisplay implements Initializable {

    @FXML
    Pane header_admin,header_student;
    @FXML
    Button displayQHPButton, displayMyQHPButton, creationQHPButton;
    @FXML
    GridPane grid;
    @FXML
    HBox pageHbox;

    private final UserFacade userFacade = UserFacade.getInstance();
    private final ViewLoader viewLoader = ViewLoader.getInstance();
    List<QuickHelpPost> partialQuickHelpPosts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.isCurrentUserStudent()){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
            disableStudentRights();
        }
        partialQuickHelpPosts = QuickHelpPostFacade.getInstance().getQuickHelpPosts();
        createNavigationButtons();
        displayQuickHelpPosts(0);
    }

    private void displayQuickHelpPosts(int offset) {
        clearGrid();
        for(int i=offset;i<6+offset;i++){
            if(i==partialQuickHelpPosts.size()){
                break;
            }
            QuickHelpPost qhp = partialQuickHelpPosts.get(i);
            StudentRole studentRole = (StudentRole) qhp.getCreator().getRole();
            BorderPane borderPane = new BorderPane();
            String studentDetails = studentRole.getStudentRepresentation();
            String qhpDetails = String.format(
                    "Description : %s\nMatière : %s\n",
                    qhp.getDescription(),
                    qhp.getSubject().getName());
            Button detailsButton = new Button("Détails");
            EventHandler<ActionEvent> event = event1 -> goToQuickHelpPost(qhp.getId());
            detailsButton.setOnAction(event);
            borderPane.setTop(new Label(studentDetails));
            borderPane.setCenter(new Label(qhpDetails));
            borderPane.setBottom(detailsButton);
            grid.add(borderPane,i%2,(i-offset)/2);
        }
    }

    private void goToQuickHelpPost(int id) {
        System.out.println("Etape 1 " + id);
        viewLoader.load(ViewPath.GET_QUICK_HELP_POST, id);
        // we communicate the id of the quick help post that we want to display with details
    }

    public void goToQHPDisplayPage() {
        partialQuickHelpPosts = QuickHelpPostFacade.getInstance().getQuickHelpPosts();
        viewLoader.load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }

    public void goToQHPCreationPage() {
        viewLoader.load(ViewPath.CREATE_QUICK_HELP_POST);
    }

    public void goToMyQHPDisplayPage() {
        partialQuickHelpPosts = QuickHelpPostFacade.getInstance().getMyQuickHelpPosts();
        displayQuickHelpPosts(0);
    }

    private void disableStudentRights(){
        displayQHPButton.setVisible(false);
        creationQHPButton.setVisible(false);
        displayMyQHPButton.setVisible(false);
    }

    private void createNavigationButtons() {
        int numberOfButtons = (partialQuickHelpPosts.size()-1)/6 +1;
        for(int j=1;j<=numberOfButtons;j++){
            Button button = new Button(String.valueOf(j));
            button.setOnAction(event -> {
                displayQuickHelpPosts((Integer.parseInt(button.getText())-1) * 6);
            });
            pageHbox.getChildren().add(button);
        }
    }

    private void clearGrid() {
        grid.getChildren().retainAll(grid.getChildren().get(0));
    }
}
