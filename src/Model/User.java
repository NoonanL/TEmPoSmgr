package Model;

public class User {

    //Variables
    private String username;
    private String isAdmin;

    //Constructors

    public User(){
        this.username = "";
        this.isAdmin = "";
    }

    public User(String username, String isAdmin){
        this.username = username;
        this.isAdmin = isAdmin;
    }

    //Getters and Setters

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setIsAdmin(String isAdmin){
        this.isAdmin = isAdmin;
    }

    public String getIsAdmin(){
        return this.isAdmin;
    }

}
