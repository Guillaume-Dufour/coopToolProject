package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.MentoringDemand;
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

public class MentoringDemandsDisplay implements Initializable {

    @FXML
    Button creationButton;
    @FXML
    GridPane grid;
    @FXML
    HBox pageHbox;
    List<MentoringDemand> partialMentoringDemands;
    Button focusButton;
    boolean isInitialized = false;
    
    private final UserFacade userFacade = UserFacade.getInstance();
    private final ViewLoader viewLoader = ViewLoader.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.isCurrentUserAdmin()){
            //TODO: reprendre la condition
        } else {
            creationButton.setVisible(false);
        }
        partialMentoringDemands = MentoringDemandFacade.getInstance().getMentoringDemands();
        createNavigationButtons();
        showMentoringDemands(0);
        isInitialized = true;
    }

    public void showMentoringDemands(int offset){
        if(isInitialized){
            clearGrid();
        }
        for(int i=offset;i<6+offset;i++){
            if(i==partialMentoringDemands.size()){
                break;
            }
            MentoringDemand cur = partialMentoringDemands.get(i);
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

    private void createNavigationButtons(){
        int numberOfButtons = (partialMentoringDemands.size()-1)/6 +1;
        for(int j=1;j<=numberOfButtons;j++){
            Button button = new Button(String.valueOf(j));
            button.setOnAction(event -> {
                showMentoringDemands((Integer.parseInt(button.getText())-1) * 6);
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

    public void goToCreationPage(){
        viewLoader.load(ViewPath.CREATE_MENTORING_DEMAND);
    }

    public void goToMentoringDemand(int id){
        viewLoader.load(ViewPath.GET_MENTORING_DEMAND,id);
    }
}
