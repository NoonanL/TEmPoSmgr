package View;

import Model.Customer;
import TEmPoSmgr.TEmPoSmgr;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ConfigurationPageController {

    private Stage dialogStage;

    public ConfigurationPageController(){}

    @FXML private Button back;
    @FXML private Button submit;
    @FXML private ChoiceBox branchField;

    /**
     * sets the current loaded branch configuration
     */
    public void setBranchField() {

        branchField.setValue("");
    }

    @FXML
    private void submit(){


    }

    /**
     * Returns to the adminSettings page
     */
    @FXML
    private void backClicked(){
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        submit.setDefaultButton(true);
        branchField.setItems(FXCollections.observableArrayList());
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}
