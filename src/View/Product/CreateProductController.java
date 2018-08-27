package View.Product;

import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import daos.BRAND;
import daos.DEPARTMENT;
import daos.PRODUCT;
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

public class CreateProductController {

    private Stage dialogStage;

    public CreateProductController() throws IOException, JSONException {}

    @FXML
    private Button back;
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


    @FXML
    private void submit() throws IOException, JSONException {

        createProduct();

    }


    @FXML
    private void createDepartmentClicked(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Product/Departments.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Departments");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DepartmentsController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            departments = FXCollections.observableArrayList(DEPARTMENT.getDepartmentList());
            departmentField.setItems(departments);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createBrandClicked(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Product/Brands.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Brands");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            BrandsController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            brands = FXCollections.observableArrayList(BRAND.getBrandList());
            brandField.setItems(brands);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    private void createProduct() throws IOException, JSONException {

        //Added input validation for pound symbols here - NOT good enough. Will be fixed serverside in next scrum.
        if (skuField.getText().equals("") ||
                costField.getText().equals("") ||
                costField.getText().contains("£") ||
                rrpField.getText().equals("") ||
                rrpField.getText().contains("£")) {
            error.setText("Please enter values for all required fields.");
        } else {
            Product newProduct = new Product();
            newProduct.setName(nameField.getText());
            newProduct.setBrand(brandField.getSelectionModel().getSelectedItem().toString());
            newProduct.setDepartment(departmentField.getSelectionModel().getSelectedItem().toString());
            newProduct.setDescription(descriptionField.getText());
            newProduct.setCost(costField.getText());
            newProduct.setRRP(rrpField.getText());
            newProduct.setSKU(skuField.getText());


            if (PRODUCT.createProduct(newProduct)) {

                error.setText("Product successfully created.");
            } else {
                error.setText("Failed to create product!");
            }

        }
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
