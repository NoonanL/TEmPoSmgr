package View.Util;

import Model.Distributor;
import daos.DISTRIBUTOR;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class EditDistributorController {

    private Stage dialogStage;
    private Distributor selectedDistributor;

    public EditDistributorController(){}

    @FXML private Button back;
    @FXML Button submit;
    @FXML private TextField distributorField;
    @FXML private Label error;

    public void setSelectedDistributor(Distributor editDistributor){
        this.selectedDistributor = editDistributor;
        distributorField.setText(selectedDistributor.getName());
    }

    @FXML
    private void submit() throws IOException, JSONException {
        //send update request here

        String distributor = distributorField.getText();

        Distributor editedDistributor = new Distributor();
        editedDistributor.setName(distributor);
        editedDistributor.setId(this.selectedDistributor.getId());

        if(DISTRIBUTOR.editDistributor(editedDistributor)){
            error.setText("Changes saved.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Changes Saved");
            alert.setHeaderText("Changes Saved");
            alert.setContentText("Changes to user " + distributor + " saved.");
            alert.showAndWait();
            dialogStage.close();
        }else{
            error.setText("Error saving changes.");
        }
    }

    @FXML
    private void backClicked() {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    /**
     *
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        submit.setDefaultButton(true);
        error.setText("");

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
