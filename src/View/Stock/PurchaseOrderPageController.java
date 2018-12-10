package View.Stock;

import Model.GoodsOrder;
import Model.Product;
import Model.PurchaseOrder;
import TEmPoSmgr.TEmPoSmgr;
import daos.GOODSORDER;
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
import java.util.UUID;

public class PurchaseOrderPageController {

    private TEmPoSmgr mainApp;

    public PurchaseOrderPageController(){}

    private String searchString = "";

    @FXML
    private TableView<Product> searchTable;
    @FXML private TableColumn<Product, String> skuSearchColumn;
    @FXML private TableColumn<Product, String> nameSearchColumn;

    @FXML private TableView<PurchaseOrder> purchaseOrderTable;
    @FXML private TableColumn<PurchaseOrder, String> UIDcolumn;
    @FXML private TableColumn<PurchaseOrder, String> statusColumn;

    @FXML private TableView<Product> goodsOrderTable;
    @FXML private TableColumn<Product, String> skuColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> quantityColumn;


    @FXML private TextField search;
    @FXML private Button searchButton;
    @FXML private Label error;

    private int lastSelectedPo;
    private String saved;

    private ArrayList<Product> goodsOrderArray = new ArrayList<>();
    private ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<>();

    /**
     * returns use to home page
     */
    @FXML
    private void backClicked() {
        if(saved.equals("true")){
            mainApp.showHome();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Leave without saving?");
            alert.setHeaderText("Leave without saving?");
            alert.setContentText("Are you sure you want to leave without saving your changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
               mainApp.showHome();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }

    }

    @FXML
    private void SaveOrder() throws IOException, JSONException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Purchase Order?");
        alert.setHeaderText("Save Purchase Order?");
        alert.setContentText("Are you sure you're ready to save this Purchase Order?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(savePurchaseOrder()){
                goodsOrderArray = new ArrayList<>();
                refreshTable();
                saved="true";
            }else{
                error.setText("Saving Purchase Order Failed");
            }
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
        if(goodsOrderTable.getSelectionModel().getSelectedItem() !=null){
            goodsOrderTable.getSelectionModel().getSelectedItem().decrementQuantity();
            if(goodsOrderTable.getSelectionModel().getSelectedItem().getQuantity()>0){
                initialize();
            }else{
                goodsOrderArray.remove(goodsOrderTable.getSelectionModel().getSelectedItem());
                initialize();
            }
        }
        saved="false";

    }

    @FXML
    private void increment() throws IOException, JSONException {
        if(goodsOrderTable.getSelectionModel().getSelectedItem() !=null) {
            goodsOrderTable.getSelectionModel().getSelectedItem().incrementQuantity();
            if (goodsOrderTable.getSelectionModel().getSelectedItem().getQuantity() > 0) {
                initialize();
            } else {
                goodsOrderArray.remove(goodsOrderTable.getSelectionModel().getSelectedItem());
                initialize();
            }
        }

        saved = "false";
    }
    /**
     * Init method for controller
     */
    @FXML
    private void initialize() throws IOException, JSONException {

        saved = "false";
        purchaseOrders = STOCK.getPurchaseOrders();
        UIDcolumn.setCellValueFactory(cellData -> cellData.getValue().UIDProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        purchaseOrderTable.setItems(FXCollections.observableList(purchaseOrders));

        searchButton.setDefaultButton(true);
        skuSearchColumn.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
        nameSearchColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        if(searchString.equals("")){
            searchTable.setItems(FXCollections.observableList(PRODUCT.getProducts()));
        }else{
            searchTable.setItems(FXCollections.observableList(PRODUCT.searchProducts(searchString)));
        }

        goodsOrderTable.setItems(FXCollections.observableList(goodsOrderArray));

        searchTable.setRowFactory( tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Product rowData = row.getItem();
                    rowData.incrementQuantity();
                    goodsOrderArray.add(rowData);
                    saved = "false";
                    try {
                        initialize();
                    } catch (IOException | JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            return row ;
        });


        maintainSelection();

        skuColumn.setCellValueFactory(cellData -> cellData.getValue().SKUProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityColumn.setOnEditCommit( p -> saved ="false");
        quantityColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setQuantity(Integer.parseInt(t.getNewValue()))
        );

    }


    private boolean savePurchaseOrder() throws IOException, JSONException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Saving Purchase Order");
        alert.setHeaderText("Save as New or Overwrite?");
        alert.setContentText("Would you like to overwrite the selected Purchase Order or save it as a new order?");

        ButtonType overwrite = new ButtonType("Overwrite");
        ButtonType saveNew = new ButtonType("Save as New");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(overwrite, saveNew, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == overwrite){
            // ... user chose "One"
            if(purchaseOrderTable.getSelectionModel().getSelectedItem()!=null){
                if(!overwritePurchaseOrder()){
                    System.out.println("Failed to overwrite");
                    error.setText("Failed to overwrite existing purchase order.");
                    return false;
                }else{
                    return true;
                }
            }else{
                error.setText("No purchase order selected.");
                return false;
            }
        } else if (result.get() == saveNew) {
            // ... user chose "Two"
            return saveNewPurchaseOrder();
        }
        return true;
    }

    private boolean overwritePurchaseOrder() throws IOException, JSONException {
        PurchaseOrder selectedPO = purchaseOrderTable.getSelectionModel().getSelectedItem();
        String UID = selectedPO.getUID();
        ArrayList<GoodsOrder> goodsOrders = GOODSORDER.getGoodsOrdersByUID(UID);
        for(GoodsOrder g : goodsOrders){
            GOODSORDER.deleteGoodsOrder(Integer.parseInt(g.getId()));
        }
        for(Product i : goodsOrderArray) {
            GoodsOrder newGoodsOrder = new GoodsOrder();
            newGoodsOrder.setProductId(i.getId());
            newGoodsOrder.setUID(UID);
            newGoodsOrder.setQuantity(i.getQuantity());
            newGoodsOrder.setStatus(selectedPO.getStatus());
               if(!GOODSORDER.createGoodsOrder(newGoodsOrder)){
                   error.setText("Failed to rebuild goods orders.");
                   return false;
               }
        }
        return true;
    }


    private boolean saveNewPurchaseOrder() throws IOException, JSONException {
        //create random UID
        UUID uuid = UUID.randomUUID();
        String UID = uuid.toString();

        //For selected products, convert to GoodsIn object and send to server
        for(Product i : goodsOrderArray){
            GoodsOrder newGoodsOrder = new GoodsOrder();
            newGoodsOrder.setProductId(i.getId());
            newGoodsOrder.setUID(UID);
            newGoodsOrder.setQuantity(i.getQuantity());
            newGoodsOrder.setStatus("Pending");
            //System.out.println(newGoodsOrder.getQuantity());
            if(!GOODSORDER.createGoodsOrder(newGoodsOrder)) {
                error.setText("Creating Goods Order failed.");
                return false;
            }
        }

        //Create purchase order with matching UID and send to server
        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setStatus("Pending");
        newPurchaseOrder.setBranchId(TEmPoSmgr.configuration.getBranchId());
        newPurchaseOrder.setUID(UID);
        //System.out.println("Got here");
        if(!STOCK.createPurchaseOrder(newPurchaseOrder)){
            error.setText("Creating Purchase Order Failed");
            return false;
        }

        return true;
    }

    @FXML
    private void loadPurchaseOrder() throws IOException, JSONException {
        PurchaseOrder purchaseOrder = purchaseOrderTable.getSelectionModel().getSelectedItem();
        if(purchaseOrder!=null){
            goodsOrderArray = new ArrayList<>();
            ArrayList<GoodsOrder> goodsOrders;
            goodsOrders = GOODSORDER.getGoodsOrdersByUID(purchaseOrder.getUID());
            for(GoodsOrder i : goodsOrders){
                Product product = PRODUCT.getProductById(i.getProductId());
                product.setQuantity(i.getQuantity());
                goodsOrderArray.add(product);
            }
            lastSelectedPo = purchaseOrderTable.getSelectionModel().getSelectedIndex();
            refreshTable();
        }
    }



    private void maintainSelection(){
        purchaseOrderTable.getSelectionModel().select(lastSelectedPo);
    }
    /**
     * Utility method to refresh the contents of the table
     * @throws IOException
     * @throws JSONException
     */
    @FXML
    private void refreshTable() throws IOException, JSONException {
        search();
        goodsOrderTable.refresh();
        purchaseOrderTable.refresh();
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
