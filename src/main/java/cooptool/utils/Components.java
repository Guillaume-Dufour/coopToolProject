package cooptool.utils;

import cooptool.business.ViewLoader;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Optional;

public class Components {

    public static void createDepartmentComboBox(ComboBox<Department> departmentComboBox, List<Department> listDepartments) {
        departmentComboBox.setItems(FXCollections.observableList(listDepartments));

        departmentComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department object) {
                return object != null ? object.getAbbreviation() + object.getYear() : "Choisir un departement";
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });
    }

    public static void createAvailabilityButton(Button button, int available) {

        if (available == 1) {
            button.setText("Disponible");
            button.setStyle("-fx-background-color: #51e056");
        }
        else {
            button.setText("Indisponible");
            button.setStyle("-fx-background-color: #f0524d");
        }
    }

    public static Optional<ButtonType> createConfirmationAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.initOwner(ViewLoader.getInstance().getStage());

        return alert.showAndWait();

    }

    public static void initializeSubjectBox(List<Subject> subjects,ComboBox<Subject> subjectComboBox){
        subjects.add(null);
        subjectComboBox.setItems(FXCollections.observableList(subjects));
        subjectComboBox.setConverter(new StringConverter<>() {

            @Override
            public String toString(Subject object) {
                return object != null ? object.getName() : "Tous les sujets";
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }

        });
    }

}
