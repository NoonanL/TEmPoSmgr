package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Transaction {

    private SimpleStringProperty id;
    private SimpleStringProperty customerId;
    private SimpleStringProperty productId;
    private SimpleStringProperty customerName;
    private SimpleStringProperty productName;
    private SimpleStringProperty quantity;



    public Transaction(){
        this.id = new SimpleStringProperty();
        this.customerId = new SimpleStringProperty();
        this.productId = new SimpleStringProperty();
        this.customerName = new SimpleStringProperty();
        this.productName = new SimpleStringProperty();
        this.quantity = new SimpleStringProperty();
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
        parameters.put("customerName", this.getCustomerName());
        parameters.put("productId" , this.getProductId());
        parameters.put("productName", this.getProductName());
        parameters.put("quantity", this.getQuantity());

        return parameters;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }
}
