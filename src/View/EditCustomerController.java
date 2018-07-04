package View;

import Model.Customer;
import daos.CUSTOMER;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

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
     * Fills the empty textfields and checkboxes to the appropriate details for the currently selected user
     * @param editCustomer
     */
    public void setSelectedCustomer(Customer editCustomer) {

        this.selectedCustomer = editCustomer;
        titleField.setValue(selectedCustomer.getTitle());
        firstnameField.setText((selectedCustomer.getFirstname()));
        surnameField.setText((selectedCustomer.getSurname()));
        streetField.setText(selectedCustomer.getStreet());
        townField.setText(selectedCustomer.getTown());
        postcodeField.setText(selectedCustomer.getPostcode());
        cityField.setText(selectedCustomer.getCity());
        countryField.setText(selectedCustomer.getCountry());
        mobileField.setText(selectedCustomer.getMobile());
        emailField.setText(selectedCustomer.getEmail());
        marketingStatusField.setValue(selectedCustomer.getMarketingStatus());
    }

    @FXML
    private void submit() throws IOException, JSONException {
        String targetCustomer = this.selectedCustomer.getId();

        Customer editCustomer = new Customer();
        editCustomer.setTitle(titleField.getSelectionModel().getSelectedItem().toString());
        editCustomer.setFirstname(firstnameField.getText());
        editCustomer.setSurname(surnameField.getText());
        editCustomer.setStreet(streetField.getText());
        editCustomer.setTown(townField.getText());
        editCustomer.setPostcode(postcodeField.getText());
        editCustomer.setCity(cityField.getText());
        editCustomer.setCountry(countryField.getText());
        editCustomer.setMobile(mobileField.getText());
        editCustomer.setEmail(emailField.getText());
        editCustomer.setMarketingStatus(marketingStatusField.getSelectionModel().getSelectedItem().toString());

        if(CUSTOMER.editCustomer(editCustomer,targetCustomer)){
            error.setText("Changes saved.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Changes Saved");
            alert.setHeaderText("Changes Saved");
            alert.setContentText("Changes to customer " + editCustomer.getId() + " " + editCustomer.getSurname() + " saved.");
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
        submit.setDefaultButton(true);
        titleField.setItems(FXCollections.observableArrayList("-","Mr & Mrs","Mr","Mrs","Ms","Miss"));
        marketingStatusField.setItems(FXCollections.observableArrayList("True","False"));
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
