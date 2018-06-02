package Model;

import javafx.beans.property.SimpleStringProperty;

public class User {

    //Variables
    private final SimpleStringProperty username;
    private final SimpleStringProperty isAdmin;

    //Constructors
    public User(){
        this.username = new SimpleStringProperty();
        this.isAdmin = new SimpleStringProperty();
    }

    public User(String username, String isAdmin){
        this.username = new SimpleStringProperty(username);
        this.isAdmin = new SimpleStringProperty(isAdmin);
    }

    //Getters and Setters

    public void setUsername(String username)
    {
        this.username.set(username);
    }

    public String getUsername(){
        return this.username.get();
    }

    public void setIsAdmin(String isAdmin){
        this.isAdmin.set(isAdmin);
    }

    public String getIsAdmin(){
        return this.isAdmin.get();
    }


}
