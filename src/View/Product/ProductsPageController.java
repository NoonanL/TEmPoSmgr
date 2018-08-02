package View.Product;

import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import daos.PRODUCT;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;

public class ProductsPageController {

    private TEmPoSmgr mainApp;

    public ProductsPageController(){}

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> idColumn;
    @FXML private TableColumn<Product, String> skuColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> rrpColumn;
    @FXML private TableColumn<Product, String> brandColumn;
    @FXML private TableColumn<Product, String> departmentColumn;
    @FXML private TableColumn<Product, String> costColumn;
    @FXML private Label sku;
    @FXML private Label name;
    @FXML private Label description;
    @FXML private Label rrp;
    @FXML private Label department;
    @FXML private Label brand;
    @FXML private Label error;
    @FXML private TextField search;
    @FXML private Button searchButton;

    private String searchString = "";


    /**
     * returns use to home page
     */
    @FXML
    private void backClicked() {
        mainApp.showHome();
    }

    private void showProductDetails(Product product){
        if(product!=null){
//            id.setText(product.getId());
            name.setText(product.getName());
            sku.setText(product.getSKU());
            description.setText(product.getDescription());
            rrp.setText("£" + product.getRRP());
            //cost.setText(product.getCost());
            department.setText(product.getDepartment());
            brand.setText(product.getBrand());
        }
        else{
            //id.setText("");
            name.setText("");
            sku.setText("");
            description.setText("");
            rrp.setText("");
            //cost.setText("");
            department.setText("");
            brand.setText("");


        }
    }

    @FXML
    private void editClicked() {

        error.setText("");
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null){
            error.setText("No product selected.");
        }else {
            error.setText("Product Id " + selectedProduct.getId() + " selected.");
            showEditProductDialog(selectedProduct);
        }
    }

    private void showEditProductDialog(Product product){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TEmPoSmgr.class.getResource("/View/Product/EditProduct.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(error.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditProductController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSelectedProduct(product);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the search box for user input and then initialises the table again to reflect any change
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void search() throws IOException, JSONException {
        searchString = search.getText();
        initialize();
    }

    /**
     * Init method for controller
     * Sets the table data
     */
    @FXML
    private void initialize() throws IOException, JSONException {

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        skuColumn.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        rrpColumn.setCellValueFactory(cellData -> cellData.getValue().RRPProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());


        if(searchString.equals("")){
            productTable.setItems(FXCollections.observableList(PRODUCT.getProducts()));
        }else{
            productTable.setItems(FXCollections.observableList(PRODUCT.searchProducts(searchString)));
        }

        // Clear person details.
        showProductDetails(null);

        // Listen for selection changes and show the person details when changed.
        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductDetails(newValue));

        searchButton.setDefaultButton(true);
    }

    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void refreshTable() throws IOException, JSONException {
        search();
        productTable.refresh();
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
