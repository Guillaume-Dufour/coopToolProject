package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.*;
import cooptool.utils.TimeUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class MentoringDemandCreator implements Initializable {
    User user = UserFacade.getInstance().getCurrentUser();
    StudentRole student = (StudentRole) user.getRole();
    @FXML
    ComboBox<Subject> subject;
    @FXML
    ComboBox<Integer> hourBox,minBox;
    @FXML
    TextArea description;
    @FXML
    DatePicker date;
    @FXML
    Label infoLabel,errorLabel;

    private final List<Subject> subjects = SubjectFacade.getInstance().getSubjectsByDepartment(student.getDepartment());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSubjectBox();
        initializeHourAndMinutesBoxes();
    }

    public void create() {
        if(subject.getValue() == null){
            errorLabel.setText("Please pick a subject");
        }
        else if(date.getValue() == null || minBox.getValue() == null || hourBox.getValue() == null){
            errorLabel.setText("Please pick a date and a time");
        }
        else{
            ArrayList<Schedule> schedules = new ArrayList<>();
            LocalDateTime dateTime = LocalDateTime.of(date.getValue(),LocalTime.of(hourBox.getValue(), minBox.getValue()));
            schedules.add(new Schedule(dateTime,user));
            MentoringDemand demand =
                    new MentoringDemand(
                            -1,
                            subject.getValue(),
                            description.getText(),
                            LocalDateTime.now(),
                            schedules,
                            user
                    );
            MentoringDemandFacade.getInstance().create(demand);
            ViewLoader.getInstance().load(ViewPath.MENTORING_DEMAND_HOME_PAGE);
        }
    }

    private void initializeSubjectBox(){
        subject.setItems(FXCollections.observableList(subjects));
        subject.setConverter(new StringConverter<>() {

            @Override
            public String toString(Subject object) {
                return object != null ? object.getName() : "select a subject...";
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }

        });
    }

    private void initializeHourAndMinutesBoxes(){
        ArrayList<Integer> hours = TimeUtils.getHoursArrayList();
        ArrayList<Integer> minutes = TimeUtils.getMinutesArrayList();

        hourBox.setItems(FXCollections.observableList(hours));
        minBox.setItems(FXCollections.observableList(minutes));
    }

}
