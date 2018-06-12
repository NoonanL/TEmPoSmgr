package View;

import Model.User;
import daos.USER;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class CustomerParserController {

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

    private ObservableList<HeaderType> headers = FXCollections.observableArrayList();

    /**
     * Returns to Home page
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void backClicked() throws IOException, JSONException {
        // get a handle to the stage
        Stage stage = (Stage) back.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void chooseFile(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if( selectedFile != null){
            selectedFileLabel.setText( "File selected: " + selectedFile.getName());
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
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void initialize() throws IOException, JSONException {
        error.setText("");
        headerColumn.setCellValueFactory(cellData -> cellData.getValue().getStringValue());
        headerTable.setItems(headers);
    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    private void refreshTable() throws IOException, JSONException {
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


