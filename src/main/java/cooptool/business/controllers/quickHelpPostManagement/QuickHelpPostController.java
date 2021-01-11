package cooptool.business.controllers.quickHelpPostManagement;

import cooptool.business.ViewLoader;
import cooptool.business.ViewPath;
import cooptool.business.facades.PostFacade;
import cooptool.business.facades.QuickHelpPostFacade;
import cooptool.business.facades.UserFacade;
import cooptool.exceptions.CommentFormatException;
import cooptool.models.objects.Comment;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.Subject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * QuickHelpPostController class
 */
public class QuickHelpPostController implements Initializable {

    @FXML
    private ScrollPane commentsPane;

    @FXML
    private Text descriptionArea;

    @FXML
    private TextArea commentArea;

    @FXML
    private Label creatorLabel,subjectLabel,creationDateLabel;

    @FXML
    private Button deleteButton, updateDescriptionButton, comeBackButton, commentButton;

    /**
     * Attribute to access to the UserFacade methods
     */
    private final UserFacade userFacade = UserFacade.getInstance();

    /**
     * Attribute to access to the ViewLoader singleton
     */
    private final ViewLoader viewLoader = ViewLoader.getInstance();

    /**
     * Attribute to access to the QuickHelpPostFacade methods
     */
    private final QuickHelpPostFacade qhpFacade = QuickHelpPostFacade.getInstance();

    /**
     * Attribute to access to the PostFacade methods
     */
    private final PostFacade postFacade = PostFacade.getInstance();

    /**
     * Attribute to access to the QuickHelpPost methods
     */
    private QuickHelpPost qhp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get back the id and then the quick help post
        int idQHP = (int) resources.getObject("1");
        qhp = qhpFacade.getQuickHelpPost(idQHP);
        // display the quick help post
        displayQuickHelpPost();
        if (!(userFacade.isCurrentUserStudent())) {
            commentButton.setVisible(false);
            commentArea.setVisible(false);
            updateDescriptionButton.setVisible(false);
        }
        else {
            //Disabling edition and deletion if the current user is not the creator of the demand
            if (!qhpFacade.isCurrentUserCreatorOfQHP(qhp)) {
                updateDescriptionButton.setVisible(false);
                deleteButton.setVisible(false);
            }
        }
    }

    /**
     * Method called by initialize method <br>
     * Display all elements of the quick help post
     */
    public void displayQuickHelpPost() {
        displayCreator();
        displayCreationDate();
        displaySubject();
        displayDescription();
        displayComments();
    }

    /**
     * Method called by displayQuickHelpPost method <br>
     * Display quick help post's creator
     */
    public void displayCreator() {
        String creator = ((StudentRole)(qhp.getCreator().getRole())).getStudentRepresentation();
        String creatorString = String.format(
                "Auteur : %s",
                creator);
        creatorLabel.setText(creatorString);
    }

    /**
     * Method called by displayQuickHelpPost method <br>
     * Display the creation date of the quick help post
     */
    public void displayCreationDate() {
        LocalDateTime creationDate = qhp.getCreationDate();
        String creationDateString = String.format(
                "Date de publication : %s",
                creationDate);
        creationDateLabel.setText(creationDateString);
    }

    /**
     * Method called by displayQuickHelpPost method <br>
     * Display the subject of the quick help post
     */
    public void displaySubject() {
        Subject qhpSubject = qhp.getSubject();
        String subjectString = String.format(
                "Matière : %s",
                qhpSubject.getName()
        );
        subjectLabel.setText(subjectString);
    }

    /**
     * Method called by displayQuickHelpPost method <br>
     * Display the description of the quick help post
     */
    public void displayDescription() {
        descriptionArea.setText(String.format("Description :\n%s",qhp.getDescription()));
    }

    /**
     * Method called by displayQuickHelpPost method <br>
     * Display all the comments linked to the quick help post
     */
    public void displayComments() {
        ArrayList<Comment> comments = qhp.getComments();
        GridPane gridPane = new GridPane();
        commentsPane.setContent(gridPane);
        int counter = 0;
        for(Comment comment : comments){
            StudentRole studentRole = (StudentRole) comment.getCreator().getRole();
            gridPane.add(new Label(studentRole.getStudentRepresentation() + " : "),0,counter);
            gridPane.add(new Text(comment.getContent()),1,counter);
            // if the use is not the admin or the creator of the qhp he can not delete the comment
            if(userFacade.isCurrentUserStudent() || qhpFacade.isCurrentUserCreatorOfQHP(qhp)) {
                Button deleteComButton = new Button("Supprimer");
                EventHandler<ActionEvent> event = event1 -> deleteComment(comment);
                deleteComButton.setOnAction(event);
                gridPane.add(deleteComButton, 2,counter);
            }
            counter++;
        }
    }

    /**
     * Method called by deleteComButton <br>
     * Delete the comment and reload the view
     * @param comment the comment we want delete
     */

    private void deleteComment(Comment comment) {
        postFacade.deleteComment(comment);
        refresh();
    }

    /**
     * Method called by updateDescriptionButton <br>
     * Open a dialog window to change description.
     * If the new description is empty when updateButton is pressed, the old one is kept,
     * Otherwise the description changes.
     * If cancelButton is pressed, nothing is done, it cancelled the action.
     */
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
        Label errorLabel = new Label("");
        grid.add(errorLabel, 0,1);
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(description::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                if(description.getText() == "") {
                }
                else {
                    return description.getText();
                }
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(updatedDesc -> {
            qhpFacade.updateDescription(qhp,updatedDesc);
            refresh();
        });
    }

    /**
     * Method called by deleteButton <br>
     * Open an alert before deleting the quick help post
     * If the user confirmed, comment is deleted and the view is reloaded.
     * Otherwise nothing is done, it cancelled the action.
     */
    public void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer cette aide rapide");
        alert.setHeaderText("Etes-vous sûr(e) de vouloir supprimer ce post rapide ?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                qhpFacade.delete(qhp);
                comeBackHome();
            }
        }
    }

    /**
     * Method called by updateDescription, deleteComment and comment methods <br>
     * Display the quick help post by calling the controller
     */
    private void refresh(){
        viewLoader.load(ViewPath.GET_QUICK_HELP_POST,qhp.getId());
    }

    /**
     * Method called by delete method <br>
     * Display the quick help post by calling the controller
     */
    public void comeBackHome() {
        viewLoader.load(ViewPath.QUICK_HELP_POST_HOME_PAGE);
    }

    /**
     * Method called by commentButton <br>
     * Add the new comment linked to the quick help post and reload the view
     */
    public void comment() {
        try {
            postFacade.comment(commentArea.getText(),qhp);
            refresh();
        } catch (CommentFormatException e) {
        }
    }
}
