package View;

import Model.Customer;
import TEmPoSmgr.TEmPoSmgr;
import daos.CUSTOMER;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

public class CustomerPageController {

    private TEmPoSmgr mainApp;

    public CustomerPageController() {}

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> firstnameColumn;
    @FXML private TableColumn<Customer, String> surnameColumn;
    @FXML private TableColumn<Customer, String> postcodeColumn;
    @FXML private TableColumn<Customer, String> mobileColumn;
    @FXML private TableColumn<Customer, String> emailColumn;
    @FXML private Label name;
    @FXML private Label address;
    @FXML private Label contact;
    @FXML private Label error;
    @FXML private TextField search;
    @FXML private Button searchButton;

    private String searchString = "";


    /**
     * returns use to home page
     */
    @FXML
    private void backClicked() {
        mainApp.showHome();
    }

    private void showCustomerDetails(Customer customer){
        if(customer!=null){

            String nameString = customer.getTitle() + " " + customer.getFirstname() + " " + customer.getSurname();
            String addressString = customer.getStreet() + "\n" +
                    customer.getTown() + "\n" +
                    customer.getCity() + "\n" +
                    customer.getPostcode() + "\n" +
                    customer.getCountry();
            String contactString = customer.getMobile() + "\n" + customer.getEmail();
            name.setText(nameString);
            address.setText(addressString);
            contact.setText(contactString);
        }
        else{
            name.setText("");
            address.setText("");
            contact.setText("");
        }

    }

    /**
     * Checks the search box for user input and then initialises the table again to reflect any change
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void search() throws IOException, JSONException {
        searchString = search.getText();
        initialize();
    }

    /**
     * tests if a user has been selected in order to forward selected user to the EditUser page
     */
    @FXML
    private void editClicked() {

        error.setText("");
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            error.setText("No user selected.");
        }else {
            error.setText("Customer Id " + selectedCustomer.getId() + " selected.");
            showEditCustomerDialog(selectedCustomer);
        }
    }

    /**
     * Shows the dialoge to edit customers - held open until closed and then refreshes the table
     * @param customer
     */
    private void showEditCustomerDialog(Customer customer){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/EditCustomer.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Customer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditCustomerController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSelectedCustomer(customer);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Safely deletes a customer - with "Are your sure" prompt.
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void deleteCustomer() throws IOException, JSONException {
        error.setText("");
        String targetCustomer = "";
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            error.setText("No customer selected.");
        }else{
            targetCustomer = selectedCustomer.getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete Customer");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you sure you want to delete " + targetCustomer + "?"
                    + " This action cannot be undone!");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                if(CUSTOMER.deleteCustomer(targetCustomer)){
                    error.setText(("Customer " + targetCustomer + " deleted."));
                    refreshTable();
                } else{
                    error.setText("Error deleting customer.");
                }
            }
        }
    }
    /**
     * Init method for controller
     * Sets the table data
     */
    @FXML
    private void initialize() throws IOException, JSONException {

        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().firstnameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        postcodeColumn.setCellValueFactory(cellData -> cellData.getValue().postcodeProperty());
        mobileColumn.setCellValueFactory(cellData -> cellData.getValue().mobileProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        if(searchString.equals("")){
            customerTable.setItems(FXCollections.observableList(CUSTOMER.getCustomers()));
        }else{
            customerTable.setItems(FXCollections.observableList(CUSTOMER.searchCustomers(searchString)));
        }

        // Clear person details.
        showCustomerDetails(null);

        // Listen for selection changes and show the person details when changed.
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCustomerDetails(newValue));

        searchButton.setDefaultButton(true);
    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void refreshTable() throws IOException, JSONException {
        search();
        customerTable.refresh();
        error.setText("");
    }

    /**
     * shows a dialoge stage to create a new customer - waits for close and then refreshes table
     */
    @FXML
    private void showCreateNewCustomer() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/CreateCustomer.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Customer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CreateCustomerController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


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
