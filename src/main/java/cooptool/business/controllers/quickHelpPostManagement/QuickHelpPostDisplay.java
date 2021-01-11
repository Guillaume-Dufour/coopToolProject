package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.*;
import cooptool.models.objects.Department;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.Subject;
import cooptool.utils.Components;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * QuickHelpPostDisplay class
 */
public class QuickHelpPostDisplay implements Initializable {

    @FXML
    private Button displayQHPButton, displayMyQHPButton, creationQHPButton, validateDepartmentButton;

    @FXML
    private GridPane grid;

    @FXML
    private HBox pageHbox;

    @FXML
    private ComboBox<Department> department;

    @FXML
    private ComboBox<Subject> subjectComboBox;

    /**
     * Attribute to access to the QuickHelpPostFacade methods
     */
    private final QuickHelpPostFacade quickHelpPostFacade = QuickHelpPostFacade.getInstance();

    /**
     * Attribute to access to the PostFacade methods
     */
    private final PostFacade postFacade = PostFacade.getInstance();

    /**
     * Attribute to access to the current user's methods
     */
    private final UserFacade userFacade = UserFacade.getInstance();

    /**
     * Attribute to access to the ViewLoader methods
     */
    private final ViewLoader viewLoader = ViewLoader.getInstance();

    /**
     * Attribute that we change according the quick help posts we want to display
     */
    private List<QuickHelpPost> partialQuickHelpPosts;

    /**
     * Attribute to access to the Department methods
     */
    private final DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    /**
     * Attribute to access to the SubjectFacade methods
     */
    private final SubjectFacade subjectFacade = SubjectFacade.getInstance();

    /**
     * Attribute with all the subjects of a department
     */
    private final List<Subject> subjects = subjectFacade.getPromotionSubjects();

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
            partialQuickHelpPosts = quickHelpPostFacade.getQuickHelpPosts();
            Components.initializeSubjectBox(subjects,subjectComboBox);
            handleSubjectChange();
            createNavigationButtons();
            displayQuickHelpPosts(0);
        }
    }

    /**
     * Method called by initialize method <br>
     * Create filter to choose the department of the quick help posts the admin wants to see
     */
    public void createFilterDepartment() {
        List<Department> departments = departmentFacade.getAllDepartments();
        Components.createDepartmentComboBox(department, departments);
    }

    /**
     * Method called by initialize method <br>
     * Display the quick help post according the list partialQuickHelpPosts
     * @param offset integer to decide how many quick help posts we want to see by page more/less than 6
     */
    public void displayQuickHelpPosts(int offset) {

        clearGrid();

        for(int i = offset; i < 6 + offset; i++) {

            if (i == partialQuickHelpPosts.size()) {
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

    /**
     * Method called by detailsButton (method displayQuickHelpPosts(offset)) <br>
     * Change the view to see all the details of the quick help post
     * @param id the id of the quick help post we want to see
     */
    public void goToQuickHelpPost(int id) {
        // we communicate the id of the quick help post that we want to display with details
        viewLoader.load(ViewPath.GET_QUICK_HELP_POST, id);
    }

    /**
     * Method called by displayQHPButton <br>
     * Change the view to come back to quick help post home page with all the quick help post of the user's department
     */
    public void goToQHPDisplayPage() {
        partialQuickHelpPosts = quickHelpPostFacade.getQuickHelpPosts();
        viewLoader.load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }

    /**
     * Method called by creationQHPButton <br>
     * Change the view to go to the creation page of quick help post
     */
    public void goToQHPCreationPage() {
        viewLoader.load(ViewPath.CREATE_QUICK_HELP_POST);
    }

    /**
     * Method called by displayMyQHPButton <br>
     * Change the view to display only the current student's quick help posts
     */
    public void goToMyQHPDisplayPage() {
        partialQuickHelpPosts = postFacade.filterCurrentUserPosts(partialQuickHelpPosts);
        displayQuickHelpPosts(0);
    }

    /**
     * Method called by initialize method <br>
     * Hide the elements linked to the student's options only
     */
    public void disableStudentRights(){
        displayQHPButton.setVisible(false);
        creationQHPButton.setVisible(false);
        displayMyQHPButton.setVisible(false);
        subjectComboBox.setVisible(false);
    }

    /**
     * Method called by initialize method <br>
     * Hide the elements linked to the admin's options only
     */
    public void disableAdminRights() {
        department.setVisible(false);
        validateDepartmentButton.setVisible(false);
    }

    /**
     * Method called by initialize method <br>
     * Create navigation bar according the number of quick help posts to display
     */
    public void createNavigationButtons() {
        int numberOfButtons = (partialQuickHelpPosts.size() - 1) / 6 + 1;
        for(int j = 1; j <= numberOfButtons; j++) {
            Button button = new Button(String.valueOf(j));
            button.setOnAction(event -> {
                displayQuickHelpPosts((Integer.parseInt(button.getText()) - 1) * 6);
            });
            pageHbox.getChildren().add(button);
        }
        pageHbox.setVisible(true);
    }

    /**
     * Method called by displayQuickHelpPosts and displayQHPByAbbreviationYear methods <br>
     * Remove the quick help posts which are inside the grid component
     */
    public void clearGrid() {
        grid.getChildren().retainAll(grid.getChildren().get(0));
    }

    /**
     * Method called by reload and displayQHOByAbbreviationYear methods <br>
     * Remove the navigation bar
     */
    public void clearNavigationButtons() {
        pageHbox.getChildren().clear();
    }

    /**
     * Method called by validateDepartmentButton <br>
     * Get the value of the department comboBox, the linked quick help posts and then call the method to display them
     */
    public void displayQHPByAbbreviationYear() {
        clearGrid();
        clearNavigationButtons();
        String dep = department.getValue().getAbbreviation();
        int year = department.getValue().getYear();
        partialQuickHelpPosts = quickHelpPostFacade.getQHPByAbbreviation(dep, year);
        createNavigationButtons();
        displayQuickHelpPosts(0);
    }

    /**
     * Method called by the initialize method <br>
     * Display all the department if there is no value for the subjects comboBox,
     * Otherwise call method to display the quick help posts linked to the chosen subject
     */
    private void handleSubjectChange(){
        subjectComboBox.setOnAction(event -> {
            Subject subject = subjectComboBox.getValue();
            if(subject !=null) {
                partialQuickHelpPosts = quickHelpPostFacade.getQuickHelpPosts();
                reload(postFacade.filterPostsPerSubject(partialQuickHelpPosts,subject));
            }
            else {
                goToQHPDisplayPage();
            }
        });
    }

    /**
     * Method called by handleSubjectChange method <br>
     * Reload the page if the student chose a subject and call methd to display the quick help posts linked to it
     * @param qhpList list of quick help posts of the chosen subject that we want display
     */
    private void reload(List<QuickHelpPost> qhpList){
        clearNavigationButtons();
        createNavigationButtons();
        partialQuickHelpPosts = qhpList;
        displayQuickHelpPosts(0);
    }
}
