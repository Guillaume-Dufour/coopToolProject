package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.PostFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;
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

public class MentoringDemandsDisplay implements Initializable {

    @FXML
    Button creationButton;
    @FXML
    GridPane grid;
    @FXML
    HBox pageHbox;
    @FXML
    ComboBox<Subject> subjectComboBox;
    @FXML
    Button focusButton,myDemandsButton;
    
    private final UserFacade userFacade = UserFacade.getInstance();
    private final PostFacade postFacade = PostFacade.getInstance();
    private final MentoringDemandFacade mentoringDemandFacade = MentoringDemandFacade.getInstance();
    private final ViewLoader viewLoader = ViewLoader.getInstance();
    private final SubjectFacade subjectFacade = SubjectFacade.getInstance();
    private final List<Subject> subjects = subjectFacade.getPromotionSubjects();

    private List<MentoringDemand> allMentoringDemands;
    boolean isInitialized = false;
    private boolean currentUserDemands = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!userFacade.isCurrentUserStudent()){
            creationButton.setVisible(false);
        }
        allMentoringDemands = mentoringDemandFacade.getMentoringDemands();
        Components.initializeSubjectBox(subjects,subjectComboBox);
        handleSubjectChange();
        createNavigationButtons(allMentoringDemands);
        showMentoringDemands(allMentoringDemands,0);
        isInitialized = true;
    }

    public void showMentoringDemands(List<MentoringDemand> demands,int offset){
        if(isInitialized){
            clearGrid();
        }
        for(int i=offset;i<6+offset;i++){
            if(i==demands.size()){
                break;
            }
            MentoringDemand cur = demands.get(i);
            StudentRole studentRole = (StudentRole) cur.getCreator().getRole();
            BorderPane borderPane = new BorderPane();
            String topText = studentRole.getStudentRepresentation();
            String centerText = String.format(
                    "Description : %s\nSubject : %s\nSchedules :\n%s",
                    cur.getDescription(),
                    cur.getSubject().getName(),
                    cur.schedulesToString());
            Button bottomButton = new Button("Go To");
            EventHandler<ActionEvent> event = event1 -> goToMentoringDemand(cur.getId());
            bottomButton.setOnAction(event);

            borderPane.setTop(new Label(topText));
            borderPane.setCenter(new Label(centerText));
            borderPane.setBottom(bottomButton);
            grid.add(borderPane,i%2,(i-offset)/2);
        }
    }

    public void clearGrid(){
        grid.getChildren().retainAll(grid.getChildren().get(0));
    }

    private void createNavigationButtons(List<MentoringDemand> demands){
        int numberOfButtons = (demands.size()-1)/6 +1;
        for(int j=1;j<=numberOfButtons;j++){
            Button button = new Button(String.valueOf(j));
            button.setOnAction(event -> {
                showMentoringDemands(demands,(Integer.parseInt(button.getText())-1) * 6);
                focusButton.setDisable(false);
                button.setDisable(true);
                focusButton = button;
            });
            if(j==1){
                focusButton = button;
                focusButton.setDisable(true);
            }
            pageHbox.getChildren().add(button);
        }
    }

    private void handleSubjectChange(){
        subjectComboBox.setOnAction(event -> {
            Subject subject = subjectComboBox.getValue();
            if(subject == null){
                reload(filter());
            }
            else{
                reload(postFacade.filterPostsPerSubject(filter(),subject));
            }
        });
    }

    private List<MentoringDemand> filter(){
        if(currentUserDemands){
            return postFacade.filterCurrentUserPosts(allMentoringDemands);
        }
        else{
            return allMentoringDemands;
        }
    }

    private void reload(List<MentoringDemand> mentoringDemands){
        clearButtons();
        createNavigationButtons(mentoringDemands);
        showMentoringDemands(mentoringDemands,0);
    }

    private void clearButtons(){
        pageHbox.getChildren().clear();
    }

    public void goToCreationPage(){
        viewLoader.load(ViewPath.CREATE_MENTORING_DEMAND);
    }

    public void goToMentoringDemand(int id){
        viewLoader.load(ViewPath.GET_MENTORING_DEMAND,id);
    }

    public void switchMyDemandsState() {
        if(currentUserDemands){
            myDemandsButton.setText("All demands");
            currentUserDemands = false;
        }
        else{
            myDemandsButton.setText("My demands");
            currentUserDemands = true;
        }
        if(subjectComboBox.getValue() == null){
            reload(filter());
        }
        else{
            reload(postFacade.filterPostsPerSubject(filter(),subjectComboBox.getValue()));
        }
    }
}
