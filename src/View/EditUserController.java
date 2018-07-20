package View;

import Model.User;
import daos.USER;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

public class EditUserController {

    private Stage dialogStage;
    private User selectedUser;

    /**
     * constructor for the controller
     * @throws IOException
     */
    public EditUserController(){}

    @FXML private Button back;
    @FXML private TextField usernameField;
    @FXML private CheckBox isAdminField;
    @FXML private Label error;


    /**
     * Fills the empty textfields and checkboxes to the appropriate details for the currently selected user
     * @param editUser
     */
    public void setSelectedUser(User editUser) {

        this.selectedUser = editUser;
        boolean adminStatus;
        usernameField.setText((selectedUser.getUsernameProperty().get()));

        adminStatus = selectedUser.getIsAdminProperty().get().equals("Y");

        isAdminField.setSelected(adminStatus);
    }

    /**
     * Returns to the adminSettings page
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void backClicked() {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Deletes user and returns to the adminSettings page
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void deleteUser() throws IOException, JSONException {
        error.setText("");
        String targetUser = selectedUser.getUsernameProperty().get();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete User");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Are you sure you want to delete " + targetUser + "?"
                + " This action cannot be undone!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            if (USER.deleteUser(targetUser)) {
                error.setText(("User " + targetUser + " deleted."));
                backClicked();
            } else {
                error.setText("Error deleting selectedUser.");
            }
        }
    }


    /**
     * Sends edited user data to server.
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void submit() throws IOException, JSONException {
        //send update request here
        String targetUser = this.selectedUser.getUsernameProperty().get();
        String username = usernameField.getText();
        String adminStatus;
        if(isAdminField.isSelected()){
            adminStatus = "Y";
        }else{
            adminStatus = "N";
        }
        //boolean isAdmin = isAdminField.isSelected();

        User editedUser = new User();
        editedUser.setUsername(username);
        editedUser.setIsAdmin(adminStatus);
        if(USER.editUser(targetUser,editedUser)){
            error.setText("Changes saved.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Changes Saved");
            alert.setHeaderText("Changes Saved");
            alert.setContentText("Changes to user " + username + " saved.");
            alert.showAndWait();
            dialogStage.close();
        }else{
            error.setText("Error saving changes.");
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }



}
