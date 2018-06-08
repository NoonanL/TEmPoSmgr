package View;

import TEmPoSmgr.TEmPoSmgr;
import daos.USER;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONException;

import java.io.IOException;

public class LoginController {

    private TEmPoSmgr mainApp;

    public LoginController(){}

    //declare text field for patientID input
    @FXML
    private TextField username;
    //declare text field for password input
    @FXML
    private TextField password;
    @FXML
    private Label error;
    @FXML
    private Button loginButton;

       //method to check if login has been clicked
    @FXML
        public void isLoginClicked() throws IOException, JSONException {
            error.setText("");

            //assign the text currently in the username and password text boxes to variables
            String authenticateID = username.getText();
            String authenticatePassword = password.getText();

            if(USER.authenticate(authenticateID, authenticatePassword)){
                //System.out.println("LOGGED IN");
                TEmPoSmgr.authenticatedUser = authenticateID;
                mainApp.showHome();
            }else{
                //System.out.println("LOGIN FAILED");
                error.setText("Unable to Login");
            }
        }


        @FXML
        public void handleQuit(){
        System.exit(0);
        }

    //this MUST be here. Initializes the class. Ignore "not used" hint.
    @FXML
    private void initialize(){

        loginButton.setDefaultButton(true);

    }

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
