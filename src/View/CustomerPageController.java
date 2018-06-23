package View;

import Model.Customer;
import TEmPoSmgr.TEmPoSmgr;
import daos.CUSTOMER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.json.JSONException;

import java.io.IOException;

public class CustomerPageController {

    private TEmPoSmgr mainApp;

    public CustomerPageController() throws IOException, JSONException {}

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> firstnameColumn;
    @FXML private TableColumn<Customer, String> surnameColumn;

    private ObservableList<Customer> customerData = FXCollections.observableList(CUSTOMER.getCustomers());



    @FXML
    private void backClicked() throws IOException, JSONException {
        mainApp.showHome();
    }


    /**
     * Init method for controller
     */
    @FXML
    private void initialize(){

        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstname());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getSurname());
        customerTable.setItems(customerData);

    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    private void refreshTable() throws IOException, JSONException {
        customerData = FXCollections.observableList(CUSTOMER.getCustomers());
        customerTable.refresh();
        //error.setText("");
        customerTable.setItems(customerData);
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
