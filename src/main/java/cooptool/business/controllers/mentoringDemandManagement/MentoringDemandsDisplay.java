package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Department;
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
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MentoringDemandsDisplay implements Initializable {
    @FXML
    Pane header_admin,header_student;
    @FXML
    Button creationButton;
    @FXML
    GridPane grid;
    List<MentoringDemand> partialMentoringDemands;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
            creationButton.setVisible(false);
        }
        partialMentoringDemands = MentoringDemandFacade.getInstance().getMentoringDemands();
        for(int i=0;i<6;i++){
            if(i==partialMentoringDemands.size()){
                break;
            }
            MentoringDemand cur = partialMentoringDemands.get(i);
            StudentRole studentRole = (StudentRole) cur.getCreator().getRole();
            BorderPane borderPane = new BorderPane();
            String topText = String.format(
                    "Creator : %s %s, Department : %s%d",
                    studentRole.getFirstName(),
                    studentRole.getLastName(),
                    studentRole.getDepartment().getAbbreviation(),
                    studentRole.getDepartment().getYear()
                    );
            String centerText = String.format(
                    "Description : %s\nSchedules : %s",
                    cur.getDescription(),
                    cur.schedulesToString());
            Button bottomButton = new Button("Go To");
            EventHandler<ActionEvent> event = event1 -> goToMentoringDemand(cur.getId());
            bottomButton.setOnAction(event);

            borderPane.setTop(new Label(topText));
            borderPane.setCenter(new Label(centerText));
            borderPane.setBottom(bottomButton);
            grid.add(borderPane,i%2,i/2);
        }
    }

    public void goToCreationPage(){
        ViewLoader.getInstance().load(ViewPath.CREATE_MENTORING_DEMAND);
    }

    public void goToMentoringDemand(int id){
        ViewLoader.getInstance().load(ViewPath.GET_MENTORING_DEMAND);
    }
}
