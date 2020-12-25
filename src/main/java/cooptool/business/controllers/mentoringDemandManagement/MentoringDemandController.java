package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MentoringDemandController implements Initializable {
    @FXML
    Pane header_admin,header_student;
    @FXML
    Text descriptionArea;
    @FXML
    Label creatorLabel,subjectLabel,participationLabel,infoLabel;
    @FXML
    Button learnButton,teachButton;
    @FXML
    VBox schedulesVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
        }

        try {
            int idDemand = (int) resources.getObject("1");
            MentoringDemand demand = MentoringDemandFacade.getInstance().getMentoringDemand(idDemand);
            descriptionArea.setText(String.format("Description :\n%s",demand.getDescription()));
            StudentRole creatorStudentRole = (StudentRole) demand.getCreator().getRole();
            Subject demandSubject = demand.getSubject();
            String creatorString = String.format(
                    "Creator : %s %s, Department : %s%d",
                    creatorStudentRole.getFirstName(),
                    creatorStudentRole.getLastName(),
                    creatorStudentRole.getDepartment().getAbbreviation(),
                    creatorStudentRole.getDepartment().getYear()
            );
            String subjectString = String.format(
                    "Subject : %s",
                    demandSubject.getName()
            );
            ArrayList<Schedule> schedules = demand.getSchedules();

            for(Schedule schedule: schedules){
                StudentRole scheduleCreatorRole = (StudentRole) schedule.getCreator().getRole();
                String schedulesString = String.format(
                    "%s, Creator : %s %s\n",
                    schedule.getDate().toString(),
                    scheduleCreatorRole.getFirstName(),
                    scheduleCreatorRole.getLastName()
                );
                CheckBox checkBox = new CheckBox(schedulesString);
                schedulesVBox.getChildren().add(checkBox);
            }

            ArrayList<Participation> participationArray = demand.getParticipationArray();
            String participants = "Participants :\n";
            int studentsNumber = 0;
            int tutorsNumber = 0;
            for(Participation participation : participationArray){
                String participationType = participation.getParticipationType() == MentoringDemand.STUDENT ? "Student" : "Tutor";
                StudentRole participantRole = (StudentRole) participation.getParticipant().getRole();
                participants += String.format(
                        "%s %s %s\n",
                        participantRole.getFirstName(),
                        participantRole.getLastName(),
                        participationType
                );
                if(participation.getParticipationType() == MentoringDemand.STUDENT){
                    studentsNumber += 1;
                }
                else{
                    tutorsNumber += 1;
                }
            }
            creatorLabel.setText(creatorString);
            subjectLabel.setText(subjectString);
            participationLabel.setText(participants);
            infoLabel.setText(String.format("Students : %d, Tutors : %d",studentsNumber,tutorsNumber));
        } catch (MissingResourceException e) {
        }
    }

    public void learn(ActionEvent actionEvent) {

        for(Node node:schedulesVBox.getChildren()) {
            CheckBox checkBox = (CheckBox) node;
        }
    }

    public void teach(ActionEvent actionEvent) {
    }
}
