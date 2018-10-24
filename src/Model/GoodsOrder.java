package Model;

import TEmPoSmgr.TEmPoSmgr;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class GoodsOrder {

    private String id;
    private SimpleStringProperty productId;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty UID;
    private SimpleStringProperty status;

    public GoodsOrder(){
        this.id = "";
        this.productId = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
        //this.UID = new SimpleStringProperty();
//        UUID uuid = UUID.randomUUID();
//        String uidString = uuid.toString();
        this.UID = new SimpleStringProperty();
        this.status = new SimpleStringProperty();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId.get();
    }

    public SimpleStringProperty productIdProperty() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId.set(productId);
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

    public String getUID() {
        return UID.get();
    }

    public SimpleStringProperty UIDProperty() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID.set(UID);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id" , this.getId());
        parameters.put("productId" , this.getProductId());
        parameters.put("quantity" , Integer.toString(this.getQuantity()));
        parameters.put("UID" , this.getUID());
        parameters.put("status" , this.getStatus());
        return parameters;
    }
}
