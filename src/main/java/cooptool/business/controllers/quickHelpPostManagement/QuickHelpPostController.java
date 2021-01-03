package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.UserFacade;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.Subject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuickHelpPostController implements Initializable {

    @FXML
    Pane header_admin,header_student;
    @FXML
    Text descriptionArea;
    @FXML
    Label creatorLabel,subjectLabel,creationDateLabel;
    @FXML
    Button deleteButton, updateDescriptionButton, comeBackButton;

    private final UserFacade userFacade = UserFacade.getInstance();
    private final ViewLoader viewLoader = ViewLoader.getInstance();
    private final QuickHelpPostFacade qhpFacade = QuickHelpPostFacade.getInstance();
    private QuickHelpPost qhp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userFacade.isCurrentUserStudent()){
            header_admin.setVisible(false);

        } else {
            header_student.setVisible(false);
        }
        // Get back the id and then the quick help post
        int idQHP = (int) resources.getObject("1");
        qhp = qhpFacade.getQuickHelpPost(idQHP);
        // display the quick help post
        displayQuickHelpPost(qhp);
        //Disabling edition and deletion if the current user is not the creator of the demand
        if(!qhpFacade.isCurrentUserCreatorOfQHP(qhp)) {
            updateDescriptionButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    public void displayQuickHelpPost(QuickHelpPost qhp) {
        // Creator
        String creator = ((StudentRole)(qhp.getCreator().getRole())).getStudentRepresentation();
        String creatorString = String.format(
                "Auteur : %s",
                creator);
        creatorLabel.setText(creatorString);
        // Publication date
        LocalDateTime creationDate = qhp.getCreationDate();
        String creationDateString = String.format(
                "Date de publication : %s",
                creationDate);
        creationDateLabel.setText(creationDateString);
        // Subject
        Subject qhpSubject = qhp.getSubject();
        String subjectString = String.format(
                "Matière : %s",
                qhpSubject.getName()
        );
        subjectLabel.setText(subjectString);
        // Description
        descriptionArea.setText(String.format("Description :\n%s",qhp.getDescription()));

    }

    public void updateDescription() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Modifier la description");
        // Set the button types.
        ButtonType updateButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
        // Create the inside content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextArea description = new TextArea(qhp.getDescription());
        grid.add(new Label("Description:"),0,0);
        grid.add(description,1,0);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(description::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return description.getText();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(updatedDesc -> {
            qhpFacade.updateDescription(qhp,updatedDesc);
            refresh();
        });
    }

    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer cette aide rapide");
        alert.setHeaderText("Etes-vous sûr(e) de vouloir supprimer ce post rapide ?");
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                qhpFacade.delete(qhp);
                viewLoader.load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
            }
        }
    }

    private void refresh(){
        viewLoader.load(ViewPath.GET_QUICK_HELP_POST,qhp.getId());
    }

    public void comeBackHome() {
        viewLoader.load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }
}
