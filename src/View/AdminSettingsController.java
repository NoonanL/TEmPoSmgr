package View;

import TEmPoSmgr.TEmPoSmgr;
import Utils.ParameterStringBuilder;
import daos.URLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdminSettingsController {

    private TEmPoSmgr mainApp;

    public AdminSettingsController(){}

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

    private void getUsers() throws IOException, JSONException {
        Map<String, String> parameters = new LinkedHashMap<>();
        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
//        if(URLConnection.sendPOST("http://localhost:9001/getUsersServlet", postData, "auth")){
//            System.out.println("Got the list of users");
//
//
//        }else{
//            //System.out.println("LOGIN FAILED");
//
//        }
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



}
