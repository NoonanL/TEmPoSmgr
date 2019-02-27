package View.Transactions;

import Model.Customer;
import Model.Transaction;
import TEmPoSmgr.TEmPoSmgr;
import View.Customer.EditCustomerController;
import daos.CUSTOMER;
import daos.TRANSACTION;
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

public class TransactionsPageController {

    private TEmPoSmgr mainApp;

    public TransactionsPageController(){}

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> idColumn;
    @FXML private TableColumn<Transaction, String> customerIdColumn;
    @FXML private TableColumn<Transaction, String> customerNameColumn;
    @FXML private TableColumn<Transaction, String> productIdColumn;
    @FXML private TableColumn<Transaction, String> productNameColumn;
    @FXML private Label transactionIdLabel;
    @FXML private Label customerIdLabel;
    @FXML private Label customerNameLabel;
    @FXML private Label productIdLabel;
    @FXML private Label productNameLabel;
    @FXML private Label error;

    /**
     * returns use to home page
     */
    @FXML
    private void backClicked() {
        mainApp.showHome();
    }


    /**
     * Updates the customer details labels any time a different entry is selected
     * @param transaction
     */
    private void showTransactionDetails(Transaction transaction){
        if(transaction!=null){


//            String addressString = transaction.getStreet() + "\n" +
//                    transaction.getTown() + "\n" +
//                    transaction.getCity() + "\n" +
//                    transaction.getPostcode() + "\n" +
//                    transaction.getCountry();
//            String contactString = transaction.getMobile() + "\n" + transaction.getEmail();
            transactionIdLabel.setText(transaction.getId());
            customerIdLabel.setText(transaction.getCustomerName());
            productIdLabel.setText(transaction.getProductName());
        }
        else{
            transactionIdLabel.setText("");
            customerIdLabel.setText("");
            productIdLabel.setText("");
        }

    }

    /**
     * Checks the search box for user input and then initialises the table again to reflect any change
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void search() throws IOException, JSONException {

    }

    /**
     * tests if a user has been selected in order to forward selected user to the EditUser page
     */
    @FXML
    private void editClicked() {


    }

    /**
     * Shows the dialoge to edit customers - held open until closed and then refreshes the table
     * @param customer
     */
    private void showEditCustomerDialog(Customer customer){

    }

    @FXML
    private void deleteCustomer() throws IOException, JSONException {

    }

    /**
     * Init method for controller
     * Sets the table data
     */
    @FXML
    private void initialize() throws IOException, JSONException {

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());



        // Listen for selection changes and show the person details when changed.
        transactionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTransactionDetails(newValue));

        transactionTable.setItems(FXCollections.observableList(TRANSACTION.getTransactions()));

    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void refreshTable() throws IOException, JSONException {
        search();
        transactionTable.refresh();
        error.setText("");
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
