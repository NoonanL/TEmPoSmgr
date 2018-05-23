package View;

import TEmPoSmgr.TEmPoSmgr;
import javafx.fxml.FXML;
import org.json.JSONException;

import java.io.IOException;

public class HomeController {

    private TEmPoSmgr mainApp;

    public HomeController(){}

    @FXML
    public void logoutClicked() throws IOException, JSONException {
        mainApp.authenticatedUser = null;
        mainApp.showLoginStage();
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
