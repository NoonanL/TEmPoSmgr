package View;

import TEmPoSmgr.TEmPoSmgr;
import Utils.ParameterStringBuilder;
import daos.URLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.json.JSONException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginController {

    private TEmPoSmgr mainApp;

    public LoginController(){}

    //declare text field for patientID input
    @FXML
    private TextField username;
    //declare text field for password input
    @FXML
    private TextField password;

    //declare boolean for login clicked (true or false)
    final boolean loginClicked = false;


       //method to check if login has been clicked
    @FXML
        public void isLoginClicked() throws IOException, JSONException {

            //assign the text currently in the username and password text boxes to variables
            String authenticateID = username.getText();
            String authenticatePassword = password.getText();

            Map<String, String> parameters = new LinkedHashMap<>();
            parameters.put("username" , authenticateID);
            parameters.put("password" , authenticatePassword);
            //send the parameters to the ParameterStringBuilder utility class for formatting
            String postData = ParameterStringBuilder.getParamsString(parameters);
            //call the Put class method which requires the path (which api call you're executing and the postData itself
            //URLConnection.sendPOST("http://localhost:9001/loginServlet",postData);

            //URLConnection test = new URLConnection();
            //if authenticate method(from main) returns true
            if(URLConnection.sendPOST("http://localhost:9001/loginServlet", postData)){
                System.out.println("LOGGED IN");
            }else{
                System.out.println("LOGIN FAILED YO");
            }
        }

    //this MUST be here. Initializes the class. Ignore "not used" hint.
    @FXML
    private void initialize(){}

    /**
     * This is the method that the MainApp will call to set this as the main page the user sees
     * and interacts with. Pretty much always follows this format, can be copy and pasted.
     *
     * @param mainApp
     */
    public void setMainApp(TEmPoSmgr mainApp) {
        this.mainApp = mainApp;
    }

}
