package View.Transactions;

import MQTT.Subscriber;
import Model.Customer;
import Model.Transaction;
import TEmPoSmgr.TEmPoSmgr;
import daos.TRANSACTION;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class TransactionsPageController {

    private TEmPoSmgr mainApp;

    public TransactionsPageController(){


    }

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> idColumn;
    @FXML private TableColumn<Transaction, String> customerIdColumn;
    @FXML private TableColumn<Transaction, String> customerNameColumn;
    @FXML private TableColumn<Transaction, String> productIdColumn;
    @FXML private TableColumn<Transaction, String> productNameColumn;
    @FXML private TableColumn<Transaction, String> productQuantityColumn;
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


    }

    /**
     * Checks the search box for user input and then initialises the table again to reflect any change
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void search() throws IOException, JSONException, MqttException {
        initialize();
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
    private void initialize() throws IOException, JSONException, MqttException {

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());



        // Listen for selection changes and show the person details when changed.
        transactionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTransactionDetails(newValue));

        //ArrayList<Transaction> testList = TRANSACTION.getTransactions();
        transactionTable.setItems(FXCollections.observableList(TRANSACTION.getTransactions()));


       // Subscriber testSub = new Subscriber("update", "TEmPoS_MGR_update", transactionTable);

    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    public void refreshTable() throws IOException, JSONException, MqttException {
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
