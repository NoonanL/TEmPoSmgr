package View;

import Model.Configuration;
import daos.CONFIGURATION;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;


public class ConfigurationPageController {

    private Stage dialogStage;

    public ConfigurationPageController() throws IOException, JSONException {}

    @FXML private Button back;
    @FXML private Button submit;
    @FXML private ChoiceBox branchField;

    private ObservableList<String> branches = FXCollections.observableArrayList(CONFIGURATION.getBranchList());

    /**
     * sets the current loaded branch configuration
     */
    public void setBranchField(String branchId) {
        System.out.println(branchId);
        branches.add(branchId);
        branchField.setValue(branchId);
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
        branchField.setItems(branches);
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
