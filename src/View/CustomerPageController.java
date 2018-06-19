package View;

import TEmPoSmgr.TEmPoSmgr;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class CustomerPageController {

    private TEmPoSmgr mainApp;

    public CustomerPageController(){}

    @FXML
    private void backClicked() throws IOException, JSONException {
        mainApp.showHome();
    }


    /**
     * Init method for controller
     */
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
