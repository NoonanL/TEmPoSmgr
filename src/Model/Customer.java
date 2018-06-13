package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

    /**
     * declare variables
     */
    private SimpleStringProperty firstname;
    private SimpleStringProperty surname;

    /**
     * constructors
     */
    public Customer(){
        this.firstname = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
    }

    public Customer(String firstname, String surname){
        this.firstname = new SimpleStringProperty(firstname);
        this.surname = new SimpleStringProperty(surname);
    }

    /**
     * getters.setters
     * NOTE - getters return a stringproperty for the use of FX
     */

    public void setFirstname(String firstname)
    {
        this.firstname.set(firstname);
    }

    public StringProperty getFirstname(){
        return this.firstname;
    }

    public void setSurname(String surname){
        this.surname.set(surname);
    }

    public StringProperty getSurname(){
        return this.surname;
    }



}
