package View.Stock;

import Model.GoodsIn;
import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import daos.PRODUCT;
import daos.STOCK;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONException;

import java.io.IOException;

public class StockPageController {

    private TEmPoSmgr mainApp;

    public StockPageController(){}

    @FXML
    private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> idColumn;
    @FXML private TableColumn<Product, String> skuColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> rrpColumn;
    @FXML private TableColumn<Product, String> brandColumn;
    @FXML private TableColumn<Product, String> departmentColumn;
    @FXML private TableColumn<Product, String> costColumn;
    @FXML private TableColumn<Product, String> quantityColumn;
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
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());


        productTable.setItems(FXCollections.observableList(STOCK.getStockByBranch()));

        searchButton.setDefaultButton(true);
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
