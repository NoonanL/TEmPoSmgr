package View.Customer;

import Model.Customer;
import daos.CUSTOMER;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class CreateCustomerController {
    private Stage dialogStage;


    @FXML private Button back;
    @FXML private Button submit;
    @FXML private ChoiceBox titleField;
    @FXML private TextField firstnameField;
    @FXML private TextField surnameField;
    @FXML private TextField streetField;
    @FXML private TextField townField;
    @FXML private TextField postcodeField;
    @FXML private TextField cityField;
    @FXML private TextField countryField;
    @FXML private TextField mobileField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox marketingStatusField;
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
        Customer newCustomer = new Customer();
        newCustomer.setTitle(titleField.getSelectionModel().getSelectedItem().toString());
        newCustomer.setFirstname(firstnameField.getText());
        newCustomer.setSurname(surnameField.getText());
        newCustomer.setStreet(streetField.getText());
        newCustomer.setTown(townField.getText());
        newCustomer.setPostcode(postcodeField.getText());
        newCustomer.setCity(cityField.getText());
        newCustomer.setCountry(countryField.getText());
        newCustomer.setMobile(mobileField.getText());
        newCustomer.setEmail(emailField.getText());
        newCustomer.setMarketingStatus(marketingStatusField.getSelectionModel().getSelectedItem().toString());

        error.setText(createNewCustomer(newCustomer));


    }

    /**
     * Function for creating a new customer
     * @return a string which will be shown to the user, either success or an error message
     * @throws IOException
     * @throws JSONException
     */
    private String createNewCustomer(Customer customer) throws IOException, JSONException {
        String returnString;

        if(!customer.getSurname().equals("")){
            if(CUSTOMER.createCustomer(customer)){
                returnString = "New Customer Created";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("New Customer Created");
                alert.setContentText("New customer " + customer.getFirstname() + " " + customer.getSurname() + " created.");
                alert.showAndWait();
                dialogStage.close();
            }
            else{
                returnString = "Error creating new customer - bad response from server.";
            }
        }else{
            returnString = "Error creating new Customer - please provide a surname.";
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
        titleField.setItems(FXCollections.observableArrayList("-","Mr & Mrs","Mr","Mrs","Ms","Miss"));
        marketingStatusField.setItems(FXCollections.observableArrayList("True","False"));
        marketingStatusField.setValue("False");
        titleField.setValue("-");
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
