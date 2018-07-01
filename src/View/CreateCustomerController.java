package View;

import Model.Customer;
import daos.CUSTOMER;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class CreateCustomerController {
    private Stage dialogStage;


    @FXML private Button back;
    @FXML private Button submit;
    @FXML private TextField firstnameField;
    @FXML private TextField surnameField;
    @FXML private Label error;

    /**
     * Returns to Home page
     */
    @FXML
    private void backClicked() {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Attempts to create a new customer using the text data in the textfields
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void createNewCustomer() throws IOException, JSONException {
        error.setText("");
        //assign the text currently in the username and password text boxes to variables
        String firstnameFieldText = firstnameField.getText();
        String surnameFieldText = surnameField.getText();

        error.setText(createNewCustomer(firstnameFieldText,surnameFieldText));


    }

    /**
     * Function for creating a new customer
     * @param firstname the customer's first name
     * @param surname the customer's surname - required
     * @return a string which will be shown to the user, either success or an error message
     * @throws IOException
     * @throws JSONException
     */
    private String createNewCustomer(String firstname, String surname) throws IOException, JSONException {
        String returnString = null;

        if(!surname.equals("")){
            Customer customer = new Customer(firstname,surname);
            if(CUSTOMER.createCustomer(customer)){
                returnString = "New Customer Created";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("New Customer Created");
                alert.setContentText("New customer " + firstname + " " + surname + " created.");
                alert.showAndWait();
                dialogStage.close();
            }else{
                returnString = "Error creating new Customer - please provide a surname.";
            }
        }
        return(returnString);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        submit.setDefaultButton(true);
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
