package View;

import Model.User;
import TEmPoSmgr.TEmPoSmgr;
import daos.USER;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

public class EditUserController {

    private TEmPoSmgr mainApp;

    public EditUserController() throws IOException{}

    @FXML private Button back;
    @FXML private TextField usernameField;
    @FXML private CheckBox isAdminField;
    @FXML private Button submit;
    @FXML private Label error;


    private Stage dialogStage;

    private User user;

    public void setUser(User editUser) {

        this.user = editUser;
        boolean adminStatus;
        usernameField.setText((user.getUsername().get()));

        adminStatus = user.getIsAdmin().get().equals("Y");

        isAdminField.setSelected(adminStatus);
    }


    @FXML
    private void backClicked() throws IOException, JSONException {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void deleteUser() throws IOException, JSONException {
        error.setText("");
        String targetUser = "";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete User");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Are you sure you want to delete " + targetUser + "?"
                + " This action cannot be undone!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            if (USER.deleteUser(TEmPoSmgr.authenticatedUser, targetUser)) {
                error.setText(("User " + targetUser + " deleted."));
                backClicked();
            } else {
                error.setText("Error deleting user.");
            }
        }
    }



    /**
     *
     * Called when the user clicks save changes.
     */
    @FXML
    private void submit() throws IOException, JSONException {
        //send update request here
        String requestUser = TEmPoSmgr.authenticatedUser;
        String targetUser = this.user.getUsername().get();
        String username = usernameField.getText();
        String adminStatus;
        if(isAdminField.isSelected()){
            adminStatus = "Y";
        }else{
            adminStatus = "N";
        }
        //boolean isAdmin = isAdminField.isSelected();

        if(USER.editUser(requestUser,targetUser,username,adminStatus)){
            error.setText("Changes saved.");
        }else{
            error.setText("Error saving changes.");
        }
        //dialogStage.close();
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
