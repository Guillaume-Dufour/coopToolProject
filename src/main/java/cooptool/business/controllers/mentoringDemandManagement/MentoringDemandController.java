package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MentoringDemandController implements Initializable {
    @FXML
    Pane header_admin,header_student;
    @FXML
    Text descriptionArea;
    @FXML
    Label creatorLabel,subjectLabel,participationLabel,infoLabel,errorLabel;
    @FXML
    Button learnButton,teachButton,suppressParticipationButton;
    @FXML
    VBox schedulesVBox;

    private MentoringDemand demand;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (UserFacade.getInstance().getCurrentUser().getRole() instanceof StudentRole){
            header_admin.setVisible(false);
        } else {
            header_student.setVisible(false);
        }

        try {
            int idDemand = (int) resources.getObject("1");
            demand = MentoringDemandFacade.getInstance().getMentoringDemand(idDemand);

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

            ArrayList<Participation> participationArray = demand.getParticipationArray();
            StringBuilder participants = new StringBuilder("Participants :\n");
            int studentsNumber = 0;
            int tutorsNumber = 0;
            for(Participation participation : participationArray){
                String participationType = participation.getParticipationType() == MentoringDemand.STUDENT ? "Student" : "Tutor";
                StudentRole participantRole = (StudentRole) participation.getParticipant().getRole();
                participants.append(String.format(
                        "%s %s %s\n",
                        participantRole.getFirstName(),
                        participantRole.getLastName(),
                        participationType
                ));
                if(participation.getParticipationType() == MentoringDemand.STUDENT){
                    studentsNumber += 1;
                }
                else{
                    tutorsNumber += 1;
                }
            }
            creatorLabel.setText(creatorString);
            subjectLabel.setText(subjectString);
            participationLabel.setText(participants.toString());
            infoLabel.setText(String.format("Students : %d, Tutors : %d",studentsNumber,tutorsNumber));

            ArrayList<Schedule> schedules = demand.getSchedules();
            Participation currentUserParticipation = MentoringDemandFacade.getInstance().getCurrentUserParticipation(demand);
            if(currentUserParticipation != null){
                learnButton.setVisible(false);
                teachButton.setVisible(false);
                for(Schedule schedule:schedules){
                    boolean selectedSchedule = false;
                    for(Schedule participationSchedule : currentUserParticipation.getParticipationSchedules()){
                        if (participationSchedule.getDate().equals(schedule.getDate())) {
                            selectedSchedule = true;
                            break;
                        }
                    }
                    String scheduleString = selectedSchedule ? schedule.getDate() + " Selected" : schedule.getDate() + " Not Selected";
                    schedulesVBox.getChildren().add(
                                new Label(scheduleString)
                    );
                }
            }
            else{
                suppressParticipationButton.setVisible(false);
                for(Schedule schedule: schedules){
                    CheckBox checkBox = new CheckBox(schedule.getDate().toString());
                    schedulesVBox.getChildren().add(checkBox);
                }
            }
        } catch (MissingResourceException ignored) {
        }
    }

    public void suppressParticipation(ActionEvent actionEvent) {
        MentoringDemandFacade.getInstance().suppressCurrentUserParticipation(demand);
        ViewLoader.getInstance().load(ViewPath.GET_MENTORING_DEMAND,demand.getId());
    }

    public void participate(ActionEvent actionEvent) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        for(Node node:schedulesVBox.getChildren()) {
            CheckBox checkBox = (CheckBox) node;
            if(checkBox.isSelected()){
                LocalDateTime checkBoxDate = LocalDateTime.parse(checkBox.getText());
                schedules.add(new Schedule(checkBoxDate,null));
            }
        }
        Button sourceButton = (Button) actionEvent.getSource();
        int participationType = sourceButton == learnButton ? MentoringDemand.STUDENT : MentoringDemand.TUTOR;
        if(schedules.size() == 0){
            if(participationType == MentoringDemand.STUDENT){
                errorLabel.setText("You must select a schedule before participating");
            }
            else{
                //use that atm but we want our tutor to be able to create schedule upon entering
                errorLabel.setText("You must select a schedule before participating");
                //create schedule
            }
        }
        else{
            MentoringDemandFacade.getInstance().participate(demand,participationType,schedules);
            ViewLoader.getInstance().load(ViewPath.GET_MENTORING_DEMAND,demand.getId());
        }
    }
}
