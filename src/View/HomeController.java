package View;

import TEmPoSmgr.TEmPoSmgr;
import daos.USER;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class HomeController {

    private TEmPoSmgr mainApp;

    public HomeController(){}

    /**
     * Returns to login page
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    public void logoutClicked() throws IOException, JSONException {
        mainApp.logout();
    }

    /**
     * Opens the adminSettings menu when button is clicked as long as authenticated user is an admin
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    public void adminSettings() throws IOException, JSONException {

        if(USER.isAdmin()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AdminSettings.fxml"));
            Parent adminSettings = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(adminSettings));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }else{
            System.out.println("NOT AN ADMIN");
            //error.setText("Incorrect Username or Password");
        }

    }

    @FXML
    public void showCustomersPage(){
        mainApp.showCustomerPage();
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
