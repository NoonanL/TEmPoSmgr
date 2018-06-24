package View;

import Model.Customer;
import Model.User;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class EditCustomerController {

    private Stage dialogStage;
    private Customer selectedCustomer;

    /**
     * constructor for the controller
     * @throws IOException
     */
    public EditCustomerController() throws IOException{}

    @FXML
    private Button back;
    @FXML private TextField firstnameField;
    @FXML private TextField surnameField;

    /**
     * Fills the empty textfields and checkboxes to the appropriate details for the currently selected user
     * @param editCustomer
     */
    public void setSelectedCustomer(Customer editCustomer) {

        this.selectedCustomer = editCustomer;
        firstnameField.setText((selectedCustomer.getFirstname().get()));
        surnameField.setText((selectedCustomer.getSurname().get()));
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
