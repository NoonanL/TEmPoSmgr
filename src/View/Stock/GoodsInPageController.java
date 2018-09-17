package View.Stock;

import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import daos.PRODUCT;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.json.JSONException;

import java.io.IOException;

public class GoodsInPageController {

    private TEmPoSmgr mainApp;

    public GoodsInPageController(){}

    private String searchString = "";

    @FXML private TableView<Product> searchTable;
    @FXML private TableColumn<Product, String> skuSearchColumn;
    @FXML private TableColumn<Product, String> nameSearchColumn;
    @FXML private TextField search;
    @FXML private Button searchButton;

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
     */
    @FXML
    private void initialize() throws IOException, JSONException {

        searchButton.setDefaultButton(true);
        skuSearchColumn.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
        nameSearchColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        if(searchString.equals("")){
            searchTable.setItems(FXCollections.observableList(PRODUCT.getProducts()));
        }else{
            searchTable.setItems(FXCollections.observableList(PRODUCT.searchProducts(searchString)));
        }

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
