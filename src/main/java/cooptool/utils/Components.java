package cooptool.utils;

import cooptool.models.objects.Department;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.List;

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
}
