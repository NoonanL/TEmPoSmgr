package View.Util;

import Model.Department;
import Model.Distributor;
import TEmPoSmgr.TEmPoSmgr;
import View.Product.EditDepartmentController;
import daos.DEPARTMENT;
import daos.DISTRIBUTOR;
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

public class DistributorPageController {

    private Stage dialogStage;

    public DistributorPageController() throws IOException, JSONException {}

    @FXML private Button back;
    @FXML private TextField distributorField;
    @FXML private Label error;
    @FXML private TableView<Distributor> distributorTable;
    @FXML private TableColumn<Distributor, String> nameColumn;

    private ObservableList<Distributor> distributorData = FXCollections.observableList(DISTRIBUTOR.getDistributors());

    @FXML
    private void createNewClicked() throws IOException, JSONException {
        error.setText("");
        //assign the text currently in the username and password text boxes to variables
        String distributorName = distributorField.getText();

        error.setText(createDistributor(distributorName));
        refreshTable();
    }

    private String createDistributor(String distributor) throws IOException, JSONException {

        String returnString;

        if(!distributor.equals("")){

            Distributor newDistributor = new Distributor();
            newDistributor.setName(distributor);

            if(DISTRIBUTOR.createDistributor(newDistributor)){
                //System.out.println("New user created");
                returnString = "New distributor created";
            }else{
                returnString = "Error creating new Distributor.";
            }
        }else {
            returnString = "Please complete all marked fields.";
        }
        return(returnString);
    }

    @FXML
    private void editClicked() throws IOException, JSONException {
        error.setText("");
        Distributor selectedDistributor = distributorTable.getSelectionModel().getSelectedItem();

        if(selectedDistributor == null){
            error.setText("No distributor selected.");
        }else {
            error.setText(selectedDistributor.getName() + " selected.");
            showEditDistributorDaliog(selectedDistributor);
        }
        refreshTable();
    }

    private void showEditDistributorDaliog(Distributor distributor) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Util/EditDistributor.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Distributor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditDistributorController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSelectedDistributor(distributor);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backClicked() {
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
        error.setText("");
        //Set columns to their appropriate value factory
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        distributorTable.setItems(distributorData);

    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    private void refreshTable() throws IOException, JSONException {
        distributorData = FXCollections.observableList(DISTRIBUTOR.getDistributors());
        distributorTable.refresh();
        //error.setText("");
        distributorTable.setItems(distributorData);
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
