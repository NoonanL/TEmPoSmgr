package Model;


import Utils.CSVReader;
import daos.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpCookie;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Configuration {

    private HashMap loadedConfiguration;
    private SimpleStringProperty branchId;
    private SimpleStringProperty authenticatedUser;
    private String CONFIG_CSV = "configuration.csv";

    public Configuration(){
        this.branchId = new SimpleStringProperty();
        this.setBranchId("");
        //Constructor for logged in user
        this.authenticatedUser = new SimpleStringProperty();
        this.setAuthenticatedUser("");
        loadConfiguration();
    }

    public void loadConfiguration(){

        //Load CSV
        this.loadedConfiguration = CSVReader.parseConfigurationCSV(CONFIG_CSV);

        //Get Branch ID and show it to URL builders
        this.setBranchId((String) loadedConfiguration.get("branchId"));
        USER.setBranch(branchId.get().toString());
        CUSTOMER.setBranch(branchId.toString());
        BRAND.setBranch(branchId.toString());
        DISTRIBUTOR.setBranch(branchId.toString());
        CONFIGURATION.setBranch(branchId.toString());
        PRODUCT.setBranch(branchId.toString());
        DEPARTMENT.setBranch(branchId.toString());
        STOCK.setBranch(branchId.get());
    }

    /**
     * this may work for branch id's but it'll break otherwise. Make me smarter.
     * @param editField
     * @param newVal
     */
    public void editConfiguration(String editField, String newVal){
        Iterator it = loadedConfiguration.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getKey().equals(editField)){
                try (
                        BufferedWriter writer = Files.newBufferedWriter(Paths.get(CONFIG_CSV));

                        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                                .withHeader(pair.getKey().toString()))
                ) {
                    csvPrinter.printRecord(newVal);

                    csvPrinter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getBranchId() {
        //System.out.println(branchId.get());
        return branchId.get();
    }

    public SimpleStringProperty branchIdProperty() {
        return branchId;
    }

    private void setBranchId(String branchId) {
        this.branchId.set(branchId);
    }

    public String getAuthenticatedUser() {
        return authenticatedUser.get();
    }

    public SimpleStringProperty authenticatedUserProperty() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(String authenticatedUser) {
        this.authenticatedUser.set(authenticatedUser);
        USER.setAuthenticatedUser(authenticatedUser);
        CUSTOMER.setAuthenticatedUser(authenticatedUser);
        CONFIGURATION.setAuthenticatedUser(authenticatedUser);
        PRODUCT.setAuthenticatedUser(authenticatedUser);
        BRAND.setAuthenticatedUser(authenticatedUser);
        DISTRIBUTOR.setAuthenticatedUser(authenticatedUser);
        DEPARTMENT.setAuthenticatedUser(authenticatedUser);
        STOCK.setAuthenticatedUser(authenticatedUser);
    }

}
