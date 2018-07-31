package View;

import Model.Product;
import daos.BRAND;
import daos.DEPARTMENT;
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

    @FXML private ChoiceBox departmentField;
    @FXML private ChoiceBox brandField;
    @FXML private Label error;

    private ObservableList<String> brands = FXCollections.observableArrayList(BRAND.getBrandList());
    private ObservableList<String> departments = FXCollections.observableArrayList(DEPARTMENT.getDepartmentList());


    public void setSelectedProduct(Product product) {

        this.selectedProduct = product;
        nameField.setText(selectedProduct.getName());
        skuField.setText(selectedProduct.getSKU());
        descriptionField.setText(selectedProduct.getDescription());
        rrpField.setText(selectedProduct.getRRP());
        costField.setText(selectedProduct.getCost());
        departmentField.setValue(selectedProduct.getDepartment());
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
    private void initialize() throws IOException, JSONException {
        submit.setDefaultButton(true);
        departmentField.setItems(departments);
        brandField.setItems(brands);
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

