package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Brand {

    private SimpleStringProperty id;
    private SimpleStringProperty brand;
    private SimpleStringProperty distributor;

    public Brand(){
        this.id = new SimpleStringProperty();
        this.brand = new SimpleStringProperty();
        this.distributor = new SimpleStringProperty();
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

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public String getDistributor() {
        return distributor.get();
    }

    public SimpleStringProperty distributorProperty() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor.set(distributor);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id", this.getId());
        parameters.put("brand", this.getBrand());
        parameters.put("distributor", this.getDistributor());
        return parameters;
    }
}
