package cooptool.business.controllers.userManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.DepartmentFacade;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.MailAlreadyExists;
import cooptool.exceptions.MailNotConformed;
import cooptool.exceptions.PasswordNotConformed;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.objects.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputMail;
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
        Department department = listDepartments.getValue();
        System.out.println(department);
        String password = inputPassword.getText();
        String confirmedPassword = inputConfirmedPassword.getText();
        try {
            userFacade.register(firstName, lastName, mail, department, password, confirmedPassword);
            userFacade.sendValidationCode(mail);
            ViewLoader.getInstance().load(ViewPath.LOGIN);
        } catch (MailAlreadyExists | MailNotConformed |PasswordNotConformed | UnmatchedPassword e){
            buttonRegister.setDisable(false);
            errorLabel.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputFirstName.setText("mathilde");
        inputLastName.setText("tribot");
        inputMail.setText("mathilde.tribot@etu.umontpellier.fr");
        inputPassword.setText("guillaume");
        inputConfirmedPassword.setText("guillaume");

        listDepartments.setItems(FXCollections.observableList(departmentFacade.getAllDepartments()));

        listDepartments.setConverter(new StringConverter<>() {
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
