package View.Stock;

import TEmPoSmgr.TEmPoSmgr;
import javafx.fxml.FXML;

public class GoodsInPageController {

    private TEmPoSmgr mainApp;

    public GoodsInPageController(){}

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
