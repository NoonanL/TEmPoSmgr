package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    public StringProperty getUsername(){
        return this.username;
    }

    public void setIsAdmin(String isAdmin){
        this.isAdmin.set(isAdmin);
    }

    public StringProperty getIsAdmin(){
        return this.isAdmin;
    }


}
