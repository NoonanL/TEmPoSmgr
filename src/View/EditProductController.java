package View;

import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import daos.CONFIGURATION;
import daos.DISTRIBUTOR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class EditProductController {

    private Stage dialogStage;
    private Product selectedProduct;

    public EditProductController() throws IOException, JSONException {}

    @FXML private Button back;
    @FXML private Button submit;
    @FXML private Button addBrand;
    @FXML private Button addDepartment;
    @FXML private TextField nameField;
    @FXML private TextField skuField;
    @FXML private TextArea descriptionField;
    @FXML private TextField rrpField;
    @FXML private TextField costField;

    @FXML private ChoiceBox departmenttField;
    @FXML private ChoiceBox brandField;
    @FXML private Label error;

    public void setSelectedProduct(Product product) {

        this.selectedProduct = product;
        nameField.setText(selectedProduct.getName());
        skuField.setText(selectedProduct.getSKU());
        descriptionField.setText(selectedProduct.getDescription());
        rrpField.setText(selectedProduct.getRRP());
        costField.setText(selectedProduct.getCost());
        departmenttField.setValue(selectedProduct.getDepartment());
        brandField.setValue(selectedProduct.getBrand());
    }

    @FXML
    private void submit() {

    }

    @FXML
    private void backClicked(){
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
        submit.setDefaultButton(true);
        //titleField.setItems(FXCollections.observableArrayList("-","Mr & Mrs","Mr","Mrs","Ms","Miss"));
        //marketingStatusField.setItems(FXCollections.observableArrayList("True","False"));
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

