package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Customer {

    /**
     * declare variables
     */
    private SimpleStringProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty firstname;
    private SimpleStringProperty surname;
    private SimpleStringProperty street;
    private SimpleStringProperty town;
    private SimpleStringProperty postcode;
    private SimpleStringProperty city;
    private SimpleStringProperty country;
    private SimpleStringProperty mobile;
    private SimpleStringProperty email;
    private SimpleStringProperty marketingStatus;

    /**
     * constructors
     */
    public Customer(){
        this.id = new SimpleStringProperty();
        this.title = new SimpleStringProperty();
        this.firstname = new SimpleStringProperty();
        this.surname = new SimpleStringProperty();
        this.street = new SimpleStringProperty();
        this.town = new SimpleStringProperty();
        this.postcode = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
        this.mobile = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.marketingStatus = new SimpleStringProperty();
    }

    public Customer(String firstname, String surname){
        this.firstname = new SimpleStringProperty(firstname);
        this.surname = new SimpleStringProperty(surname);
    }

    /**
     * getters.setters
     * NOTE - getters return a stringproperty for the use of FX
     */




    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getTitle(){
        return this.title.get();
    }


    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getTown() {
        return town.get();
    }

    public SimpleStringProperty townProperty() {
        return town;
    }

    public void setTown(String town) {
        this.town.set(town);
    }

    public String getPostcode() {
        return postcode.get();
    }

    public SimpleStringProperty postcodeProperty() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getMobile() {
        return mobile.get();
    }

    public SimpleStringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getMarketingStatus() {
        return marketingStatus.get();
    }

    public SimpleStringProperty marketingStatusProperty() {
        return marketingStatus;
    }

    public void setMarketingStatus(String marketingStatus) {
        this.marketingStatus.set(marketingStatus);
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

    public String getFirstname() {
        return firstname.get();
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id", this.getId());
        parameters.put("title" , this.getTitle());
        parameters.put("firstname" , this.getFirstname());
        parameters.put("surname" , this.getSurname());
        parameters.put("street" , this.getStreet());
        parameters.put("town" , this.getTown());
        parameters.put("postcode" , this.getPostcode());
        parameters.put("city" , this.getCity());
        parameters.put("country" , this.getCountry());
        parameters.put("mobile" , this.getMobile());
        parameters.put("email" , this.getEmail());
        parameters.put("marketingStatus" , this.getMarketingStatus());
        return parameters;
    }
    
}
