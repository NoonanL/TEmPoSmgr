package View;

import Model.Customer;
import Model.User;
import TEmPoSmgr.TEmPoSmgr;
import daos.CUSTOMER;
import daos.USER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public CustomerPageController() throws IOException, JSONException {}

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> firstnameColumn;
    @FXML private TableColumn<Customer, String> surnameColumn;
    @FXML private Label firstname;
    @FXML private Label surname;
    @FXML private Label error;
    @FXML private TextField search;
    @FXML private Button searchButton;

    private String searchString = "";


    @FXML
    private void backClicked() throws IOException, JSONException {
        mainApp.showHome();
    }

    private void showCustomerDetails(Customer customer){
        if(customer!=null){
            firstname.setText(customer.getFirstname().get());
            surname.setText(customer.getSurname().get());
        }
        else{
            firstname.setText("");
            surname.setText("");
        }

    }

    @FXML
    private void search() throws IOException, JSONException {
        searchString = search.getText();
        initialize();
    }

    /**
     * tests if a user has been selected in order to forward selected user to the EditUser page
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void editClicked() throws IOException, JSONException {

        error.setText("");
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            error.setText("No user selected.");
        }else {
            error.setText("Customer Id " + selectedCustomer.getId().get() + " selected.");
            showEditCustomerDialog(selectedCustomer);
        }
    }

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


    @FXML
    private void deleteCustomer() throws IOException, JSONException {
        error.setText("");
        String targetCustomer = "";
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            error.setText("No customer selected.");
        }else{
            targetCustomer = selectedCustomer.getId().get();

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
     */
    @FXML
    private void initialize() throws IOException, JSONException {

        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstname());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getSurname());

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
    private void refreshTable() throws IOException, JSONException {
        customerTable.refresh();
        //error.setText("");
    }

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
