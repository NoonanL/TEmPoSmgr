package View;

import Model.User;
import TEmPoSmgr.TEmPoSmgr;
import daos.USER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

public class AdminSettingsController {

    private TEmPoSmgr mainApp;

    public AdminSettingsController() throws IOException, JSONException {}

    @FXML
    private Button back;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private CheckBox isAdminField;
    @FXML
    private Label error;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> isAdminColumn;

    private ObservableList<User> userData = FXCollections.observableList(USER.getUsers());


    @FXML
    private void backClicked() throws IOException, JSONException {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void createNewUserClicked() throws IOException, JSONException {
        error.setText("");
        //assign the text currently in the username and password text boxes to variables
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isAdmin = isAdminField.isSelected();

        error.setText(createNewUser(username,password,isAdmin));

    }

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
                if(USER.deleteUser(TEmPoSmgr.authenticatedUser, targetUser)){
                    error.setText(("User " + targetUser + " deleted."));
                    refreshTable();
                } else{
                    error.setText("Error deleting user.");
                }

            }
        }

    }

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



    @FXML
    private void initialize() throws IOException, JSONException {

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

    private void refreshTable() throws IOException, JSONException {
        userData = FXCollections.observableList(USER.getUsers());
        userTable.refresh();
        userTable.setItems(userData);
    }


}
