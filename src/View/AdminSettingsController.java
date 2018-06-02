package View;

import Model.User;
import TEmPoSmgr.TEmPoSmgr;
import daos.URLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

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

    public final ObservableList<User> userData = FXCollections.observableList(URLConnection.getUsers());

    private void getUsers() throws IOException, JSONException {
        ArrayList<User> array = URLConnection.getUsers();

    }

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
        System.out.println("attempting to create new user with username " +
                            username +
                            " password " +
                            password +
                            " admin status " +
                            isAdmin + ".");

        error.setText(createNewUser(username,password,isAdmin));

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

            if(URLConnection.createUser(username,password,isAdminString)){
                //System.out.println("New user created");
                returnString = "New user created";
            }else{
                //System.out.println("LOGIN FAILED");
                returnString = "Error creating new user.";
            }
        }else {
            returnString = "Please complete all marked fields.";
        }
        return(returnString);
    }



    @FXML
    private void initialize(){
        usernameColumn.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        isAdminColumn.setCellValueFactory(
                new PropertyValueFactory<>("isAdmin")
        );

        //usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
        //isAdminColumn.setCellValueFactory(cellData -> cellData.getValue().getIsAdmin());
        userTable.setItems(userData);
        //listen for selection changes and show patient details when changed
    }


}
