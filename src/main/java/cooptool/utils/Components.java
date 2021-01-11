package cooptool.utils;

import cooptool.business.ViewLoader;
import cooptool.models.objects.Department;
import cooptool.models.objects.Subject;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Optional;

/**
 * Components class
 */
public class Components {

    /**
     * Set a department combobox in parameter with the list of departments in parameter
     * @param departmentComboBox ComboBox
     * @param listDepartments List of departments we want to put into the ComboBox
     */
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

    /**
     * Set the button in parameter with the availability state in parameter
     * @param button Button
     * @param available Availability to display the button properly
     */
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

    /**
     * Create an alert window with the message
     * @param message Message we want to print into the alert window
     * @return Choice done by the user
     */
    public static Optional<ButtonType> createConfirmationAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.initOwner(ViewLoader.getInstance().getStage());

        return alert.showAndWait();
    }

    /**
     * Set a subject combobox in parameter with the list of subjects in parameter
     * @param subjects List of subject we want to put into the ComboBox
     * @param subjectComboBox ComboBox
     */
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
