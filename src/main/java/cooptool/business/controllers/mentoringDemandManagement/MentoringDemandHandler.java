package cooptool.business.controllers.mentoringDemandManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.MentoringDemandFacade;
import cooptool.business.facades.SubjectFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import javax.swing.text.View;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MentoringDemandHandler implements Initializable {
    User user = UserFacade.getInstance().getCurrentUser();
    StudentRole student = (StudentRole) user.getRole();
    @FXML
    ComboBox<Subject> subject;
    @FXML
    TextArea description;
    @FXML
    DatePicker date;
    @FXML
    Label infoLabel,errorLabel;

    private List<Subject> subjects = SubjectFacade.getInstance().getSubjectsByDepartment(student.getDepartment());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        try {
            MentoringDemand demand = (MentoringDemand) resources.getObject("1");
            infoLabel.setText("Edit");
        } catch (MissingResourceException e) {
            infoLabel.setText("Create");
        }


    }

    public void create(ActionEvent actionEvent) {
        if(subject.getValue() == null){
            errorLabel.setText("Please pick a subject");
        }
        else if(date.getValue() == null){
            errorLabel.setText("Please pick a date");
        }
        else{
            ArrayList<Schedule> schedules = new ArrayList<>();
            LocalDate selectedDate = date.getValue();
            schedules.add(new Schedule(selectedDate,user));
            MentoringDemand demand =
                    new MentoringDemand(
                            student.getDepartment(),
                            subject.getValue(),
                            description.getText(),
                            schedules,
                            user
                    );
            MentoringDemandFacade.getInstance().create(demand);
            ViewLoader.getInstance().load(ViewPath.MENTORING_DEMAND_HOME_PAGE);
        }
    }
}
