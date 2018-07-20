package View;

import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ProductsPageController {

    private TEmPoSmgr mainApp;

    public ProductsPageController(){}

    @FXML
    private TableView<Product> productTable;
}
