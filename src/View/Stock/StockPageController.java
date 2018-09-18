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
    private TableView<GoodsIn> productTable;
    @FXML private TableColumn<GoodsIn, String> idColumn;
    @FXML private TableColumn<GoodsIn, String> skuColumn;
    @FXML private TableColumn<GoodsIn, String> nameColumn;
    @FXML private TableColumn<GoodsIn, String> rrpColumn;
    @FXML private TableColumn<GoodsIn, String> brandColumn;
    @FXML private TableColumn<GoodsIn, String> departmentColumn;
    @FXML private TableColumn<GoodsIn, String> costColumn;
    @FXML private TableColumn<GoodsIn, String> quantityColumn;
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

    @FXML
    private void test() throws IOException, JSONException {
        STOCK.getStockByBranch();
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

        idColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().idProperty());
        skuColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().SKUProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());
        rrpColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().RRPProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().departmentProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().costProperty());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().getProduct().brandProperty());
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
