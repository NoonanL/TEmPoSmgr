package View;

import Model.Customer;
import Model.User;
import daos.CUSTOMER;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.json.JSONException;
import javafx.scene.control.*;

import java.io.IOException;

public class EditCustomerController {

    private Stage dialogStage;
    private Customer selectedCustomer;

    /**
     * constructor for the controller
     * @throws IOException
     */
    public EditCustomerController() {}

    @FXML private Button back;
    @FXML private TextField firstnameField;
    @FXML private TextField surnameField;
    @FXML private javafx.scene.control.Label error;


    /**
     * Fills the empty textfields and checkboxes to the appropriate details for the currently selected user
     * @param editCustomer
     */
    public void setSelectedCustomer(Customer editCustomer) {

        this.selectedCustomer = editCustomer;
        firstnameField.setText((selectedCustomer.getFirstname().get()));
        surnameField.setText((selectedCustomer.getSurname().get()));
    }

    @FXML
    private void submit() throws IOException, JSONException {
        String targetCustomer = this.selectedCustomer.getId().get();
        String firstname = firstnameField.getText();
        String surname = surnameField.getText();

        if(CUSTOMER.editCustomer(targetCustomer,firstname,surname)){
            error.setText("Changes saved.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Changes Saved");
            alert.setHeaderText("Changes Saved");
            alert.setContentText("Changes to customer " + firstname + " " + surname + " saved.");
            alert.showAndWait();
            alert.showAndWait();
            dialogStage.close();
        }else{
            error.setText("Error saving changes.");
        }
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