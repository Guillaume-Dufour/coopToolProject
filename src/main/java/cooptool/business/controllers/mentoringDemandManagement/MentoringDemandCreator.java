package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.*;
import cooptool.utils.Components;
import cooptool.utils.TimeUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class MentoringDemandCreator implements Initializable {
    private final MentoringDemandFacade mentoringDemandFacade = MentoringDemandFacade.getInstance();
    private final SubjectFacade subjectFacade = SubjectFacade.getInstance();
    private final UserFacade userFacade = UserFacade.getInstance();

    @FXML
    ComboBox<Subject> subjectComboBox;
    @FXML
    ComboBox<Integer> hourBox,minBox;
    @FXML
    TextArea description;
    @FXML
    DatePicker date;
    @FXML
    Label errorLabel;
    
    private final ViewLoader viewLoader = ViewLoader.getInstance();
    private final User currentUser = userFacade.getCurrentUser();
    private final StudentRole student = (StudentRole) currentUser.getRole();
    private final List<Subject> subjects = subjectFacade.getSubjectsByDepartment(student.getDepartment());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Components.initializeSubjectBox(subjects,subjectComboBox);
        initializeHourAndMinutesBoxes();
    }

    public void create() {
        if(subjectComboBox.getValue() == null){
            errorLabel.setText("Please pick a subject");
        }
        else if(date.getValue() == null || minBox.getValue() == null || hourBox.getValue() == null){
            errorLabel.setText("Please pick a date and a time");
        }
        else{
            LocalDateTime dateTime = LocalDateTime.of(date.getValue(),LocalTime.of(hourBox.getValue(), minBox.getValue()));
            mentoringDemandFacade.create(subjectComboBox.getValue(),description.getText(),dateTime);
            viewLoader.load(ViewPath.MENTORING_DEMAND_HOME_PAGE);
        }
    }

    private void initializeHourAndMinutesBoxes(){
        List<Integer> hours = TimeUtils.getHoursArrayList();
        List<Integer> minutes = TimeUtils.getMinutesArrayList();

        hourBox.setItems(FXCollections.observableList(hours));
        minBox.setItems(FXCollections.observableList(minutes));
    }

}
