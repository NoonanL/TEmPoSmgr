package View;

import TEmPoSmgr.TEmPoSmgr;
import daos.USER;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class HomeController {

    private TEmPoSmgr mainApp;

    public HomeController(){}

    @FXML
    public void logoutClicked() throws IOException, JSONException {
        TEmPoSmgr.authenticatedUser = null;
        mainApp.showLoginStage();
    }

    @FXML
    public void adminSettings() throws IOException, JSONException {

        //Map<String, String> parameters = new LinkedHashMap<>();
        //parameters.put("username" , mainApp.authenticatedUser);
        //send the parameters to the ParameterStringBuilder utility class for formatting
       // String postData = ParameterStringBuilder.getParamsString(parameters);
        if(USER.isAdmin(TEmPoSmgr.authenticatedUser)){
            //System.out.println("LOGGED IN");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AdminSettings.fxml"));
                Parent adminSettings = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(adminSettings));
                stage.show();

        }else{
            System.out.println("NOT AN ADMIN");
            //error.setText("Incorrect Username or Password");
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
