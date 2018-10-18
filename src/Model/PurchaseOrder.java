package Model;

import java.util.ArrayList;

public class PurchaseOrder {

    private ArrayList<GoodsIn> productsOrdered;

    public PurchaseOrder(){
        this.productsOrdered = new ArrayList<>();
    }

    public ArrayList<GoodsIn> getProductsOrdered() {
        return productsOrdered;
    }

    public void addProduct(GoodsIn product){
        this.productsOrdered.add(product);
    }

}
