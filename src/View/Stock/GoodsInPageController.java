package View.Stock;

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
import java.util.*;

public class GoodsInPageController {

    private TEmPoSmgr mainApp;

    public GoodsInPageController(){}

    private String searchString = "";

    @FXML private TableView<Product> searchTable;
    @FXML private TableColumn<Product, String> skuSearchColumn;
    @FXML private TableColumn<Product, String> nameSearchColumn;

    @FXML private TableView<Product> goodsInTable;
    @FXML private TableColumn<Product, String> skuColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> quantityColumn;

//    @FXML private TableView<GoodsOrder> purchaseOrderTable;
//    @FXML private TableColumn<GoodsOrder, String> uidColumn;
//    @FXML private TableColumn<GoodsOrder, String> skuPoColumn;
//    @FXML private TableColumn<GoodsOrder, String> quantityPoColumn;


    @FXML private TextField search;
    @FXML private Button searchButton;
    @FXML private Label error;

    private ArrayList<Product> goodsInArray = new ArrayList<>();
    //private ArrayList<GoodsOrder> purchaseOrderArray = FXCollections.STOCK.getPurchaseOrders()

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
        alert.setTitle("Confirm Goods In");
        alert.setHeaderText("Confirm Goods In");
        alert.setContentText("Are you sure you want to book these items into stock?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            if(processGoodsIn()){
                error.setText("Changes saved.");
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Changes Saved");
                alert2.setHeaderText("Changes Saved");
                alert2.setContentText("Goods In Completed.");
                alert2.showAndWait();
                goodsInArray = new ArrayList<>();
                initialize();
            }else{
                error.setText("Error saving changes.");
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }


    }
//
//    @FXML
//    private void purchaseOrderSaveClicked() throws IOException, JSONException {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Save Purchase Order");
//        alert.setHeaderText("Save Purchase Order");
//        alert.setContentText("Are you sure you want to save this as a purchase order?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK){
//            // ... user chose OK
//            if(processPurchaseOrder()){
//                error.setText("Purchase Order Saved.");
//                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
//                alert2.setTitle("Saved");
//                alert2.setHeaderText("Purchase Order Saved");
//                alert2.setContentText("Purchase Order Saved.");
//                alert2.showAndWait();
//                goodsInArray = new ArrayList<>();
//                initialize();
//            }else{
//                error.setText("Error saving changes.");
//            }
//        } else {
//            // ... user chose CANCEL or closed the dialog
//        }
//
//
//    }

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
                    //GoodsOrder e = new GoodsOrder();
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

    private boolean processGoodsIn() throws IOException, JSONException {
        for(Product i : goodsInArray){
            STOCK.createStock(i);
            for(int e = 0; e < i.getQuantity(); e++){
                STOCK.incrementStock(i);
            }
        }
        return true;
    }

//    private boolean processPurchaseOrder() throws IOException, JSONException {
//        UUID uuid = UUID.randomUUID();
//        String UID = uuid.toString();
//        System.out.println("The generated UID is " + UID);
//        for(Product i : goodsInArray){
//            GoodsOrder goodsIn = new GoodsOrder();
//            goodsIn.setProduct(i);
//            goodsIn.setUID(UID);
//            System.out.println(UID);
//            //STOCK.createStock(i);
//            STOCK.createPurchaseOrder(goodsIn);
//
//        }
//        return true;
//    }


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
