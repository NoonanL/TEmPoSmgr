package View.Stock;

import Model.GoodsIn;
import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import daos.PRODUCT;
import daos.STOCK;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class PurchaseOrderPageController {

    private TEmPoSmgr mainApp;

    public PurchaseOrderPageController(){}

    private String searchString = "";

    @FXML
    private TableView<Product> searchTable;
    @FXML private TableColumn<Product, String> skuSearchColumn;
    @FXML private TableColumn<Product, String> nameSearchColumn;

    @FXML private TableView<Product> goodsInTable;
    @FXML private TableColumn<Product, String> skuColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> quantityColumn;


    @FXML private TextField search;
    @FXML private Button searchButton;
    @FXML private Label error;

    private ArrayList<Product> goodsInArray = new ArrayList<>();

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

        if(goodsInTable.getSelectionModel().getSelectedItem() !=null){
            goodsInTable.getSelectionModel().getSelectedItem().decrementQuantity();
            if(goodsInTable.getSelectionModel().getSelectedItem().getQuantity()>0){
                initialize();
            }else{
                goodsInArray.remove(goodsInTable.getSelectionModel().getSelectedItem());
                initialize();
            }
        }

    }

    @FXML
    private void increment() throws IOException, JSONException {
        if(goodsInTable.getSelectionModel().getSelectedItem() !=null) {
            goodsInTable.getSelectionModel().getSelectedItem().incrementQuantity();
            if (goodsInTable.getSelectionModel().getSelectedItem().getQuantity() > 0) {
                initialize();
            } else {
                goodsInArray.remove(goodsInTable.getSelectionModel().getSelectedItem());
                initialize();
            }
        }
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

        goodsInTable.setItems(FXCollections.observableList(goodsInArray));


        searchTable.setRowFactory( tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Product rowData = row.getItem();
                    rowData.incrementQuantity();
                    //GoodsIn e = new GoodsIn();
                    //e.setProduct(rowData);
                    goodsInArray.add(rowData);
//                    System.out.println(rowData.toString());
                    try {
                        initialize();
                    } catch (IOException | JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            return row ;
        });




        skuColumn.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setQuantity(Integer.parseInt(t.getNewValue()))
        );

    }

    private boolean processPurchaseOrder() throws IOException, JSONException {
        for(Product i : goodsInArray){
            GoodsIn goodsIn = new GoodsIn();
            goodsIn.setProduct(i);
            //STOCK.createStock(i);
            STOCK.createPurchaseOrder(goodsIn);

        }
        return true;
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