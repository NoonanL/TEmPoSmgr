package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class Product {

    private SimpleStringProperty id;
    private SimpleStringProperty SKU;
    private SimpleStringProperty name;
    private SimpleStringProperty RRP;
    private SimpleStringProperty cost;
    private SimpleStringProperty department;
    private SimpleStringProperty brand;
    private SimpleStringProperty description;
    private SimpleIntegerProperty quantity;

    public Product() {
        this.id = new SimpleStringProperty();
        this.SKU = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.RRP = new SimpleStringProperty();
        this.cost = new SimpleStringProperty();
        this.department = new SimpleStringProperty();
        this.brand = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty(0);
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

    public String getSKU() {
        return SKU.get();
    }

    public SimpleStringProperty SKUProperty() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU.set(SKU);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRRP() {
        return RRP.get();
    }

    public SimpleStringProperty RRPProperty() {
        return RRP;
    }

    public void setRRP(String RRP) {
        this.RRP.set(RRP);
    }

    public String getCost() {
        return cost.get();
    }

    public SimpleStringProperty costProperty() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public String getQuantityString() {
        return String.valueOf(quantity.get());
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void incrementQuantity(){
        this.setQuantity(this.quantity.get() + 1);
    }

    public void decrementQuantity(){
        this.setQuantity(this.quantity.get() - 1);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id", this.getId());
        parameters.put("SKU" , this.getSKU());
        parameters.put("name" , this.getName());
        parameters.put("RRP" , this.getRRP());
        parameters.put("cost" , this.getCost());
        parameters.put("department" , this.getDepartment());
        parameters.put("brand" , this.getBrand());
        parameters.put("description" , this.getDescription());
        parameters.put("quantity", this.getQuantityString());
        return parameters;
    }


}
