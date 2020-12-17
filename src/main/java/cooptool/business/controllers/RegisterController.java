package cooptool.business.controllers;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import javax.naming.Name;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputMail;
    @FXML
    TextField inputPromotion;
    @FXML
    PasswordField inputPassword;
    @FXML
    PasswordField inputConfirmedPassword;
    @FXML
    Text errorLabel;
    @FXML
    Button buttonLogin;
    @FXML
    Button buttonRegister;
    @FXML
    ComboBox<Department> listDepartments;

    UserFacade userFacade = UserFacade.getInstance();
    DepartmentFacade departmentFacade = DepartmentFacade.getInstance();

    public void goToLoginPage(ActionEvent event) {
        try {
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(ActionEvent event) {
        System.out.println(event);
        buttonRegister.setDisable(true);
        String firstName = inputFirstName.getText();
        String lastName = inputLastName.getText();
        String mail = inputMail.getText();
        String promotion = inputPromotion.getText();
        String password = inputPassword.getText();
        String confirmedPassword = inputConfirmedPassword.getText();
        try {
            Department department = departmentFacade.getDepartment();
            userFacade.register(firstName, lastName, mail, department, password, confirmedPassword);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputFirstName.setText("mathilde");
        inputLastName.setText("tribot");
        inputMail.setText("math.tribot@etu.umontpellier.fr");
        inputPassword.setText("guillaume");
        inputConfirmedPassword.setText("guillaume");

        ObservableList<Department> departmentObservableList = FXCollections.observableList(departmentFacade.getAllDepartments());

        listDepartments.setItems(departmentObservableList);

        listDepartments.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department object) {
                System.out.println(object);
                return "Bonjour";
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });
    }
}
