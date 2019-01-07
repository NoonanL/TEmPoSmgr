package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Transaction {

    private SimpleStringProperty id;
    private SimpleStringProperty customerId;
    private SimpleStringProperty productId;


    public Transaction(){
        this.id = new SimpleStringProperty();
        this.customerId = new SimpleStringProperty();
        this.productId = new SimpleStringProperty();
    }

    public Transaction(String customerId, String productId){
        this.customerId = new SimpleStringProperty(customerId);
        this.productId = new SimpleStringProperty(productId);
    }


    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getCustomerId() {
        return customerId.get();
    }

    public SimpleStringProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
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

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id", this.getId());
        parameters.put("customerId" , this.getCustomerId());
        parameters.put("productId" , this.getProductId());

        return parameters;
    }
}
