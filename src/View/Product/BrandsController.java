package View.Product;

import Model.Brand;
import TEmPoSmgr.TEmPoSmgr;
import daos.BRAND;
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

public class BrandsController {

    private Stage dialogStage;

    public BrandsController() throws IOException, JSONException {}

    @FXML private Button back;
    @FXML private TextField brandField;
    @FXML private ChoiceBox distributorField;
    @FXML private Label error;
    @FXML private TableView<Brand> brandTable;
    @FXML private TableColumn<Brand, String> brandColumn;
    @FXML private TableColumn<Brand, String> distributorColumn;

    private ObservableList<Brand> brandsData = FXCollections.observableList(BRAND.getBrands());
    private ObservableList<String> distributors = FXCollections.observableArrayList(DISTRIBUTOR.getDistributorList());

    @FXML
    private void createNewClicked() throws IOException, JSONException {
        error.setText("");

        if(brandField.getText().equals("") || distributorField.getSelectionModel() == null){
            error.setText("Please enter values for all required fields.");
        }else{
            String brand = brandField.getText();
            String distributor = distributorField.getSelectionModel().getSelectedItem().toString();

            Brand newBrand = new Brand();
            newBrand.setBrand(brand);
            newBrand.setDistributor(distributor);

            error.setText(createBrand(newBrand));
        }
    }

    @FXML
    private void editClicked() throws IOException, JSONException {
        error.setText("");
        Brand selectedBrand = brandTable.getSelectionModel().getSelectedItem();

        if (selectedBrand == null) {
            error.setText("No Brand selected.");
        } else {
            error.setText(selectedBrand.getBrand() + " selected.");
            showEditBrandDialog(selectedBrand);
        }
        refreshTable();
    }

    private String createBrand(Brand newBrand) throws IOException, JSONException {

        String returnString;

        if(BRAND.createBrand(newBrand)){
            returnString = "New Brand created";
        }
        else{
            returnString = "Error creating new Brand.";
        }

        refreshTable();
        return(returnString);
    }

    private void showEditBrandDialog(Brand brand) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Product/EditBrand.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Brand");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditBrandController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSelectedBrand(brand);

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
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        distributorColumn.setCellValueFactory(cellData -> cellData.getValue().distributorProperty());
        brandTable.setItems(brandsData);
        distributorField.setItems(distributors);

    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    private void refreshTable() throws IOException, JSONException {
        brandsData = FXCollections.observableList(BRAND.getBrands());
        brandTable.refresh();
        //error.setText("");
        brandTable.setItems(brandsData);
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
