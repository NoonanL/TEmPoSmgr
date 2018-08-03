package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Distributor {

    private SimpleStringProperty id;
    private SimpleStringProperty name;

    public Distributor(){
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id", this.getId());
        parameters.put("distributor" , this.getName());
        return parameters;
    }
}
