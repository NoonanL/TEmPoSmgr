package View.Util;

import Model.Customer;
import Utils.CSVReader;
import daos.CUSTOMER;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CsvParserController {

    private Stage dialogStage;

    @FXML
    private Button back;
    @FXML private TextField headerField;
    @FXML private Button addHeader;
    @FXML private Button chooseFile;
    @FXML private Button deleteHeader;
    @FXML private Label error;
    @FXML private Label selectedFileLabel;
    @FXML private TableView<HeaderType> headerTable;
    @FXML private TableColumn<HeaderType, String> headerColumn;
    @FXML ToggleGroup rdoToggle;
    @FXML private RadioButton rdoCustomer;
    @FXML private RadioButton rdoProduct;

    private String file = "";
    private ArrayList<String> headerList = new ArrayList<>();
    private ObservableList<HeaderType> headers = FXCollections.observableArrayList();

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
     * Gets the headers provided by the user, the csv file location and the type of data to attempt to parse
     * Then attempts to parse data.
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void importCsvClicked() throws IOException, JSONException {

        for(HeaderType h : headers){
            headerList.add(h.getStringValue().get());
        }

        /**
         * This is starting to get messy - refactor me
         */
        if(checkValidInput()){
            if(rdoCustomer.isSelected()){
                System.out.println("Trying to parse customer data from " + file + ".");
                ArrayList<Customer> customerList = CSVReader.parseCustomerCSV(file, headerList);
                for(Customer c : customerList){
                    if(CUSTOMER.createCustomer(c)){
                        error.setText("Customer data successfuly uploaded.");
                    }else{
                        error.setText("Problem uploading customer data.");
                    }

                }
            }else if(rdoProduct.isSelected()){
                System.out.println("Trying to parse product data from " + file + ".");
            }else{
                error.setText("Please select a data type.");
            }
        }else{
            error.setText("Problem trying to parse file.");
        }


    }

    @FXML
    private boolean checkValidInput(){
        return((rdoCustomer.isSelected() || rdoProduct.isSelected()) && (!file.equals("")) && (headerList.size() != 0));
    }


    @FXML
    private void chooseFile(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if( selectedFile != null){
            selectedFileLabel.setText( "File selected: " + selectedFile.getName());
            file = selectedFile.toString();
            System.out.println(selectedFile.toString());
        }else{
            selectedFileLabel.setText("No file selected.");
        }
    }

    /**
     * Forwards the data in the input fields to the createUser method
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void newHeader() throws IOException, JSONException {
        error.setText("");
        //assign the text currently in the username and password text boxes to variables
        String header = headerField.getText();

        if(!header.equals("")){
            HeaderType headerType = new HeaderType(header);
            headers.add(headerType);
            headerField.setText("");
            refreshTable();
        }else{
            error.setText("Please add a valid header.");
        }

        //error.setText(createNewUser(username,password,isAdmin));
    }

    /**
     * Checks a user has been selected then deletes the user and refreshes the table
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void deleteHeader() throws IOException, JSONException {
        error.setText("");
        HeaderType targetHeader;
        targetHeader = headerTable.getSelectionModel().getSelectedItem();

        if(targetHeader == null){
            error.setText("No header selected.");
        }else{
            headers.remove(targetHeader);
            refreshTable();
        }
    }


    /**
     * Initialises the AdminSettings page - fills the table with data, colours cells according to the user admin status
     */
    @FXML
    private void initialize() {
        error.setText("");
        headerColumn.setCellValueFactory(cellData -> cellData.getValue().getStringValue());
        headerTable.setItems(headers);
        addHeader.setDefaultButton(true);
    }

    /**
     * Utility method to refresh the contents of the table
     */
    private void refreshTable() {
        headerTable.refresh();
        error.setText("");
        headerTable.setItems(headers);
    }


    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public class HeaderType {

        private final SimpleStringProperty stringValue ;

        HeaderType(String stringValue) {
            this.stringValue = new SimpleStringProperty(stringValue);
        }
        StringProperty getStringValue() {
            return stringValue ;
        }
    }

}


