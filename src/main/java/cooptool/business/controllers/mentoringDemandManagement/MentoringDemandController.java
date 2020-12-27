package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.*;
import cooptool.utils.TimeUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MentoringDemandController implements Initializable {
    @FXML
    Pane header_admin,header_student;
    @FXML
    Text descriptionArea;
    @FXML
    Label creatorLabel,subjectLabel,participationLabel,infoLabel,errorLabel;
    @FXML
    Button learnButton,teachButton,suppressParticipationButton,addScheduleButton;
    @FXML
    GridPane schedulesPane;

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
            int counter = 0;
            if(currentUserParticipation != null){
                learnButton.setVisible(false);
                teachButton.setVisible(false);
                for(Schedule schedule:schedules){
                    boolean selectedSchedule = false;
                    for(Schedule participationSchedule : currentUserParticipation.getParticipationSchedules()){
                        if (participationSchedule.getDateTime().equals(schedule.getDateTime())) {
                            selectedSchedule = true;
                            break;
                        }
                    }
                    Text scheduleText = new Text(schedule.toString());
                    Color color = selectedSchedule ? Color.GREEN : Color.RED;
                    scheduleText.setFill(color);
                    schedulesPane.add(
                                scheduleText,0,counter
                    );
                    Button button;
                    if(!selectedSchedule){
                        button = new Button("Not available");
                        button.setOnAction(event -> {
                            MentoringDemandFacade.getInstance().participateToSchedule(
                                    demand,currentUserParticipation.getParticipationType(),schedule
                            );
                            refresh();
                        });
                    }
                    else{
                        button = new Button("I'm available");
                        button.setOnAction(event -> {
                            MentoringDemandFacade.getInstance().quitSchedule(
                                    demand,schedule
                            );
                            refresh();
                        });
                    }
                    schedulesPane.add(button,1,counter);
                    addScheduleDeletionButtonIfCreator(schedule,counter);
                    counter++;
                }
            }
            else{
                suppressParticipationButton.setVisible(false);
                for(Schedule schedule: schedules){
                    CheckBox checkBox = new CheckBox(schedule.toString());
                    schedulesPane.add(checkBox,0,counter);
                    addScheduleDeletionButtonIfCreator(schedule,counter);
                    counter++;
                }
            }


        } catch (MissingResourceException ignored) {
        }
    }

    public void suppressParticipation() {
        MentoringDemandFacade.getInstance().suppressCurrentUserParticipation(demand);
        refresh();
    }

    public void participate(ActionEvent actionEvent) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        for(Node node:schedulesPane.getChildren()) {
            if(GridPane.getColumnIndex(node) == 0){
                CheckBox checkBox = (CheckBox) node;
                if(checkBox.isSelected()){
                    LocalDateTime checkBoxDate = LocalDateTime.parse(checkBox.getText());
                    schedules.add(new Schedule(checkBoxDate,null));
                }
            }
        }
        Button sourceButton = (Button) actionEvent.getSource();
        int participationType = sourceButton == learnButton ? MentoringDemand.STUDENT : MentoringDemand.TUTOR;
        if(schedules.size() == 0){
            if(participationType == MentoringDemand.STUDENT){
                errorLabel.setText("You must select a schedule before participating");
            }
            else{
                errorLabel.setText("You must select a schedule before participating");
                addSchedule();
            }
        }
        else{
            MentoringDemandFacade.getInstance().participate(demand,participationType,schedules);
            refresh();
        }
    }

    public void refresh(){
        ViewLoader.getInstance().load(ViewPath.GET_MENTORING_DEMAND,demand.getId());
    }

    public void addSchedule(){
        ArrayList<Integer> hours = TimeUtils.getHoursArrayList();
        ArrayList<Integer> minutes = TimeUtils.getMinutesArrayList();

        // Create the custom dialog.
        Dialog<LocalDateTime> dialog = new Dialog<>();
        dialog.setTitle("Add new schedule");
        dialog.setHeaderText("Insert fields and submit to create new schedule.");


        // Set the button types.
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create the inside
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Integer> hourBox = new ComboBox<>(FXCollections.observableList(hours));
        ComboBox<Integer> minBox = new ComboBox<>(FXCollections.observableList(minutes));
        DatePicker datePicker = new DatePicker();

        grid.add(new Label("Date:"),0,0);
        grid.add(datePicker,1,0);
        grid.add(new Label("Hour:"), 0, 1);
        grid.add(hourBox, 1, 1);
        grid.add(new Label("Minutes:"), 0, 2);
        grid.add(minBox, 1, 2);


        Node submitButton = dialog.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);

        hourBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && minBox.getValue() != null && datePicker.getValue() != null){
                submitButton.setDisable(false);
            }
        });

        minBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && hourBox.getValue() != null && datePicker.getValue() != null){
                submitButton.setDisable(false);
            }
        });

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && hourBox.getValue() != null && minBox.getValue() != null){
                submitButton.setDisable(false);
            }
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(hourBox::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourBox.getValue(), minBox.getValue()));
            }
            return null;
        });

        Optional<LocalDateTime> result = dialog.showAndWait();

        result.ifPresent(localDateTime -> {
            MentoringDemandFacade.getInstance().addSchedule(demand,localDateTime);
            refresh();
        });
    }

    private void addScheduleDeletionButtonIfCreator(Schedule schedule,int counter){
        if(MentoringDemandFacade.getInstance().isCurrentUserCreatorOfSchedule(schedule)){
            Button deleteSchedule = new Button("Delete schedule");
            deleteSchedule.setOnAction(event -> {
                MentoringDemandFacade.getInstance().deleteSchedule(demand,schedule);
                refresh();
            });
            schedulesPane.add(deleteSchedule,2,counter);
        }
    }
}
