package Model;


import Utils.CSVReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Configuration {

    private HashMap loadedConfiguration;
    private SimpleStringProperty branchId;
    private String SAMPLE_CSV_FILE = "configuration.csv";

    public Configuration(){
        this.branchId = new SimpleStringProperty();
    }

    public void loadConfiguration(){
        this.loadedConfiguration = CSVReader.parseConfigurationCSV("configuration.csv");
        this.setBranchId((String) loadedConfiguration.get("branchId"));
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
                        BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

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
        return branchId.get();
    }

    public SimpleStringProperty branchIdProperty() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId.set(branchId);
    }
}
