package Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Department {

    private SimpleStringProperty id;
    private SimpleStringProperty department;

    public Department(){
        this.id = new SimpleStringProperty();
        this.department = new SimpleStringProperty();
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

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id", this.getId());
        parameters.put("department", this.getDepartment());
        return parameters;
    }

}
