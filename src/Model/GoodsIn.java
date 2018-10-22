package Model;

import TEmPoSmgr.TEmPoSmgr;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class GoodsIn {

    private Product product;
    private SimpleIntegerProperty quantity;
    private String UID;



    public GoodsIn(){
        this.product = null;
        this.quantity = new SimpleIntegerProperty(1);
        UUID uuid = UUID.randomUUID();
        this.UID = uuid.toString();
    }

    public Product getProduct() {
        return product;
    }
    public void setUID(String uid){
        this.UID = uid;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("SKU" , this.product.getSKU());
        parameters.put("quantity" , this.product.getQuantityString());
        parameters.put("productId" , this.product.getId());
        parameters.put("branchId" , TEmPoSmgr.configuration.getBranchId());
        parameters.put("status" , "Ordered");
        parameters.put("UID" , this.UID);
        return parameters;
    }
}
