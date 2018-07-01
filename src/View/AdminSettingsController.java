package View;

import Model.User;
import TEmPoSmgr.TEmPoSmgr;
import daos.USER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

public class AdminSettingsController {

    private TEmPoSmgr mainApp;

    public AdminSettingsController() throws IOException, JSONException {}

    @FXML private Button back;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private CheckBox isAdminField;
    @FXML private Label error;
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, String> isAdminColumn;

    private ObservableList<User> userData = FXCollections.observableList(USER.getUsers());


    /**
     * Returns user to previous screen
     */
    @FXML
    private void backClicked() {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Tests if a user has been selected in order to forward selected user to the EditUser page
     */
    @FXML
    private void editClicked() {

        error.setText("");
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if(selectedUser == null){
            error.setText("No user selected.");
        }else {
            error.setText(selectedUser.getUsername().get() + " selected.");
            showEditUserDialogue(selectedUser);
        }
    }

    /**
     * Forwards the data in the input fields to the createUser method
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void createNewUserClicked() throws IOException, JSONException {
        error.setText("");
        //assign the text currently in the username and password text boxes to variables
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isAdmin = isAdminField.isSelected();

        error.setText(createNewUser(username,password,isAdmin));
    }

    /**
     * Checks a user has been selected then deletes the user and refreshes the table
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void deleteUser() throws IOException, JSONException {
        error.setText("");
        String targetUser = "";
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if(selectedUser == null){
            error.setText("No user selected.");
        }else{
            targetUser = selectedUser.getUsername().get();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete User");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to delete " + targetUser + "?"
            + " This action cannot be undone!");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                if(USER.deleteUser(targetUser)){
                    error.setText(("User " + targetUser + " deleted."));
                    refreshTable();
                } else{
                    error.setText("Error deleting user.");
                }
            }
        }
    }

    /**
     * Sends data to server to create a new user, refreshes table if successful.
     * @param username the new user's username
     * @param password the new user's password
     * @param isAdmin the new user's admin status
     * @return the status of the createUser attempt
     * @throws IOException
     * @throws JSONException
     */
    private String createNewUser(String username, String password, boolean isAdmin) throws IOException, JSONException {

        String isAdminString;
        String returnString;

        if(isAdmin){
            isAdminString = "Y";
        }else{
            isAdminString = "N";
        }

        if(!username.equals("") && !password.equals("")){

            if(USER.createUser(username,password,isAdminString)){
                //System.out.println("New user created");
                returnString = "New user created";
                userData = FXCollections.observableList(USER.getUsers());
                refreshTable();
            }else{
                returnString = "Error creating new user.";
            }
        }else {
            returnString = "Please complete all marked fields.";
        }
        return(returnString);
    }


    /**
     * shows the editUser page as a dialogue box
     * @param user
     */
    private void showEditUserDialogue(User user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/EditUser.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSelectedUser(user);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Initialises the AdminSettings page - fills the table with data, colours cells according to the user admin status
     */
    @FXML
    private void initialize() {
        error.setText("");
        //Set columns to their appropriate value factory
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
        isAdminColumn.setCellValueFactory(cellData -> cellData.getValue().getIsAdmin());

        //Custom renderer for IsAdmin column to colour the cell depending on admin status
        isAdminColumn.setCellFactory((TableColumn<User, String> column) -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                }

                // Style alString() == "false")l dates in March with a different color.
                if ("N".equals(item)) {
                    setText("Not Admin");
                    setTextFill(Color.WHITE);
                    setStyle("-fx-background-color: red");
                }
                if ("Y".equals(item)) {
                    setText("Is Admin");
                    setTextFill(Color.WHITE);
                    setStyle("-fx-background-color: green");
                }
            }
        });
        userTable.setItems(userData);
    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    private void refreshTable() throws IOException, JSONException {
        userData = FXCollections.observableList(USER.getUsers());
        userTable.refresh();
        error.setText("");
        userTable.setItems(userData);
    }

}
