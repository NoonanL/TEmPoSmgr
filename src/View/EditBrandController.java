package View;

import Model.Brand;
import Model.Department;
import daos.BRAND;
import daos.DEPARTMENT;
import daos.DISTRIBUTOR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class EditBrandController {

    private Stage dialogStage;
    private Brand selectedBrand;

    public EditBrandController() throws IOException, JSONException {}

    @FXML private Button back;
    @FXML Button submit;
    @FXML private TextField brandField;
    @FXML private ChoiceBox distributorField;
    @FXML private Label error;

    private ObservableList<String> distributors = FXCollections.observableArrayList(DISTRIBUTOR.getDistributorList());

    public void setSelectedBrand(Brand editBrand){
        this.selectedBrand = editBrand;
        brandField.setText(selectedBrand.getBrand());
        distributorField.setValue(selectedBrand.getDistributor());
    }

    @FXML
    private void submit() throws IOException, JSONException {
        //send update request here
        if(brandField.getText().equals("") || distributorField.getSelectionModel().getSelectedItem() == null){
            error.setText("Please enter values for all required fields.");
        }else{
            String brand = brandField.getText();
            String distributor = distributorField.getSelectionModel().getSelectedItem().toString();

            Brand editedBrand = new Brand();
            editedBrand.setId(this.selectedBrand.getId());
            editedBrand.setBrand(brand);
            editedBrand.setDistributor(distributor);

            editBrand(editedBrand);
        }
    }

    private void editBrand(Brand editedBrand) throws IOException, JSONException {
            if(BRAND.editBrand(editedBrand)){
                error.setText("Changes saved.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Changes Saved");
                alert.setHeaderText("Changes Saved");
                alert.setContentText("Changes to user " + editedBrand.getBrand() + " saved.");
                alert.showAndWait();
                dialogStage.close();
            }else{
                error.setText("Error saving changes.");
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
     *
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        submit.setDefaultButton(true);
        distributorField.setItems(distributors);
        error.setText("");

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
