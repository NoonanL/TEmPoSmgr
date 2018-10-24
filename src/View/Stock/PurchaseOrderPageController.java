package View.Stock;

import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONException;

import java.io.IOException;
import java.util.Optional;

public class PurchaseOrderPageController {

    private TEmPoSmgr mainApp;

    public PurchaseOrderPageController(){}

    private String searchString = "";

    @FXML
    private TableView<Product> searchTable;
    @FXML private TableColumn<Product, String> skuSearchColumn;
    @FXML private TableColumn<Product, String> nameSearchColumn;

//    @FXML private TableView<Product> goodsInTable;
//    @FXML private TableColumn<Product, String> skuColumn;
//    @FXML private TableColumn<Product, String> nameColumn;
//    @FXML private TableColumn<Product, String> quantityColumn;


    @FXML private TextField search;
    @FXML private Button searchButton;
    @FXML private Label error;


    /**
     * returns use to home page
     */
    @FXML
    private void backClicked() {
        mainApp.showHome();
    }

    @FXML
    private void SubmitClicked() throws IOException, JSONException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Purchase Order?");
        alert.setHeaderText("Save Purchase Order?");
        alert.setContentText("Save Purchase Order?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK

        } else {
            // ... user chose CANCEL or closed the dialog
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


    @FXML
    private void decrement() throws IOException, JSONException {

//        if(goodsInTable.getSelectionModel().getSelectedItem() !=null){
//            goodsInTable.getSelectionModel().getSelectedItem().decrementQuantity();
//            if(goodsInTable.getSelectionModel().getSelectedItem().getQuantity()>0){
//                initialize();
//            }else{
//                goodsInArray.remove(goodsInTable.getSelectionModel().getSelectedItem());
//                initialize();
//            }
//        }

    }

    @FXML
    private void increment() throws IOException, JSONException {
//        if(goodsInTable.getSelectionModel().getSelectedItem() !=null) {
//            goodsInTable.getSelectionModel().getSelectedItem().incrementQuantity();
//            if (goodsInTable.getSelectionModel().getSelectedItem().getQuantity() > 0) {
//                initialize();
//            } else {
//                goodsInArray.remove(goodsInTable.getSelectionModel().getSelectedItem());
//                initialize();
//            }
//        }
    }
    /**
     * Init method for controller
     */
    @FXML
    private void initialize() throws IOException, JSONException {


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
