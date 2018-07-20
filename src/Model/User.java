package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class User {

    /**
     * declare variables
     */
    private SimpleStringProperty username;
    private SimpleStringProperty isAdmin;

    /**
     * constructors
     */
    public User(){
        this.username = new SimpleStringProperty();
        this.isAdmin = new SimpleStringProperty();
    }

    public User(String username, String isAdmin){
        this.username = new SimpleStringProperty(username);
        this.isAdmin = new SimpleStringProperty(isAdmin);
    }

    /**
     * getters.setters
     * NOTE - getters return a stringproperty for the use of FX
     */

    public void setUsername(String username)
    {
        this.username.set(username);
    }

    public StringProperty getUsernameProperty(){
        return this.username;
    }

    public String getUsername(){
        return this.username.get();
    }

    public void setIsAdmin(String isAdmin){
        this.isAdmin.set(isAdmin);
    }

    public StringProperty getIsAdminProperty(){
        return this.isAdmin;
    }

    public String getIsAdmin(){
        return this.isAdmin.get();
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("username" , this.getUsername());
        parameters.put("isAdmin" , this.getIsAdmin());
        return parameters;
    }

}
