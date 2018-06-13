package Utils;

import Model.Customer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CSVReader {

    /**
     * Opens a CSV file, attempts to parse it and returns an arraylist of customer objects
     * @param filename the filename to open
     * @param headers the headers for to attempt to parse
     * @return an arraylist of customer objects
     */
    public static ArrayList<Customer> parseCustomerCSV(String filename, ArrayList<String> headers){

        ArrayList<Customer> customerList = new ArrayList<>();

        //Attempt to open file
            try (
                    Reader reader = Files.newBufferedReader(Paths.get(filename));
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim());
            ) {
                //for each record
                for (CSVRecord csvRecord : csvParser) {

                    //create a new empty customer object
                    Customer customer = new Customer();

                    //for each header, attempt to map the value to an object parameter
                    for(String s : headers){
                        switch(s){
                            case "firstname" : String firstname = csvRecord.get(s);
                                customer.setFirstname(firstname);
                                break;
                            case "surname" : String surname = csvRecord.get(s);
                                customer.setSurname(surname);
                                break;
                        }
                    }
                    //add the parsed customer to the list
                    customerList.add(customer);
                }

        } catch (IOException e) {
                e.printStackTrace();
            }
            //return the customerList
            return customerList;
    }
}
