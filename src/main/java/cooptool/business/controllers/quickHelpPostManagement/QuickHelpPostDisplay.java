package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.*;
import cooptool.models.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuickHelpPostDisplay implements Initializable {

    @FXML
    Button displayQHPButton, displayMyQHPButton, creationQHPButton, validateDepartmentButton;
    @FXML
    GridPane grid;
    @FXML
    HBox pageHbox;
    @FXML
    ComboBox<Department> department;

    private final QuickHelpPostFacade quickHelpPostFacade = QuickHelpPostFacade.getInstance();
    private final PostFacade postFacade = PostFacade.getInstance();
    private final UserFacade userFacade = UserFacade.getInstance();
    private final ViewLoader viewLoader = ViewLoader.getInstance();
    List<QuickHelpPost> partialQuickHelpPosts;
    private DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // The user is the admin
        if (!userFacade.isCurrentUserStudent()) {
            disableStudentRights();
            createFilterDepartment();
        }
        // The user is a student
        else {
            disableAdminRights();
            partialQuickHelpPosts = quickHelpPostFacade.getQuickHelpPosts(null);
            createNavigationButtons();
            displayQuickHelpPosts(0);
        }
    }

    public void createFilterDepartment() {
        List<Department> departments = departmentFacade.getAllDepartments();
        department.setItems(FXCollections.observableList(departments));
        department.setConverter(new StringConverter<>() {

            @Override
            public String toString(Department department) {
                return department != null ? department.getAbbreviation() + " " + department.getYear(): "Choisir un départment...";
            }

            @Override
            public Department fromString(String string) {
                return null;
            }

        });
    }

    public void displayQuickHelpPosts(int offset) {
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

    public void goToQuickHelpPost(int id) {
        // we communicate the id of the quick help post that we want to display with details
        viewLoader.load(ViewPath.GET_QUICK_HELP_POST, id);
    }

    public void goToQHPDisplayPage() {
        partialQuickHelpPosts = quickHelpPostFacade.getQuickHelpPosts(null);
        viewLoader.load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }

    public void goToQHPCreationPage() {
        viewLoader.load(ViewPath.CREATE_QUICK_HELP_POST);
    }

    public void goToMyQHPDisplayPage() {
        partialQuickHelpPosts = postFacade.filterCurrentUserPosts(partialQuickHelpPosts);
        displayQuickHelpPosts(0);
    }

    public void disableStudentRights(){
        displayQHPButton.setVisible(false);
        creationQHPButton.setVisible(false);
        displayMyQHPButton.setVisible(false);
    }

    public void disableAdminRights() {
        department.setVisible(false);
        validateDepartmentButton.setVisible(false);
    }

    public void createNavigationButtons() {
        int numberOfButtons = (partialQuickHelpPosts.size()-1)/6 +1;
        for(int j=1;j<=numberOfButtons;j++){
            Button button = new Button(String.valueOf(j));
            button.setOnAction(event -> {
                displayQuickHelpPosts((Integer.parseInt(button.getText())-1) * 6);
            });
            pageHbox.getChildren().add(button);
        }
        pageHbox.setVisible(true);
    }

    public void clearGrid() {
        grid.getChildren().retainAll(grid.getChildren().get(0));
    }

    public void clearNavigationButtons() {
        pageHbox.getChildren().clear();
    }

    public void displayQHPByAbbreviationYear() {
        clearGrid();
        clearNavigationButtons();
        String dep = department.getValue().getAbbreviation();
        int year = department.getValue().getYear();
        partialQuickHelpPosts = quickHelpPostFacade.getQHPByAbbreviation(dep, year);
        createNavigationButtons();
        displayQuickHelpPosts(0);
    }
}
