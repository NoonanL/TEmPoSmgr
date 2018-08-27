package Utils;

import Model.Brand;
import Model.Customer;
import Model.Department;
import Model.Product;
import TEmPoSmgr.TEmPoSmgr;
import daos.BRAND;
import daos.DEPARTMENT;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


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
                            .withTrim())
            ) {
                //for each record
                for (CSVRecord csvRecord : csvParser) {

                    //create a new empty customer object
                    Customer customer = new Customer();

                    //for each header, attempt to map the value to an object parameter
                    for(String s : headers){
                        switch(s){
                            case "title" :
                                customer.setTitle(csvRecord.get(s));
                                break;
                            case "firstname" :
                                customer.setFirstname(csvRecord.get(s));
                                break;
                            case "surname" :
                                customer.setSurname(csvRecord.get(s));
                                break;
                            case "street" :
                                customer.setStreet(csvRecord.get(s));
                                break;
                            case "town" :
                                customer.setTown(csvRecord.get(s));
                                break;
                            case "postcode" :
                                customer.setPostcode(csvRecord.get(s));
                                break;
                            case "city" :
                                customer.setCity(csvRecord.get(s));
                                break;
                            case "country" :
                                customer.setCountry(csvRecord.get(s));
                                break;
                            case "mobile" :
                                customer.setMobile(csvRecord.get(s));
                                break;
                            case "email" :
                                customer.setEmail(csvRecord.get(s));
                                break;
                            case "marketingStatus" :
                                customer.setMarketingStatus(csvRecord.get(s));
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

    public static ArrayList<Product> parseProductCSV(String filename, ArrayList<String> headers) throws IOException, JSONException {
        ArrayList<Product> productList = new ArrayList<>();

        ArrayList<String> departments = DEPARTMENT.getDepartmentList();
        ArrayList<String> brands = BRAND.getBrandList();

        //Attempt to open file
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
            //for each record
            for (CSVRecord csvRecord : csvParser) {

                //create a new empty customer object
                Product product = new Product();

                //for each header, attempt to map the value to an object parameter
                for(String s : headers){
                    switch(s){
                        case "name" :
                            product.setName(csvRecord.get(s));
                            break;
                        case "sku" :
                            product.setSKU(csvRecord.get(s));
                            break;
                        case "rrp" :
                            product.setRRP(csvRecord.get(s));
                            break;
                        case "cost" :
                            product.setCost(csvRecord.get(s));
                            break;
                        case "department" :
                            if(departments.contains(csvRecord.get(s))){
                                product.setDepartment(csvRecord.get(s));
                                break;
                            }
                            else{
                                Department department = new Department();
                                department.setDepartment(csvRecord.get(s));
                                DEPARTMENT.createDepartment(department);
                                product.setDepartment(csvRecord.get(s));
                                break;
                            }
                        case "brand" :
                            if(brands.contains(csvRecord.get(s))){
                                product.setBrand(csvRecord.get(s));
                                break;
                            }
                            else{
                                Brand brand = new Brand();
                                brand.setBrand(csvRecord.get(s));
                                BRAND.createBrand(brand);
                                product.setBrand(csvRecord.get(s));
                                break;
                            }

                        case "description" :
                            product.setDescription(csvRecord.get(s));
                            break;
                    }
                }
                //add the parsed customer to the list
                productList.add(product);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //return the customerList
        return productList;
    }


    public static HashMap parseConfigurationCSV(String filename){
        HashMap<String, String> configuration = new HashMap<>();
        ArrayList<String> headers = new ArrayList<>();
        headers.add("branchId");

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

                //for each header, attempt to map the value to an object parameter
                for(String s : headers){
                    switch(s){
                        case "branchId" :
                            configuration.put("branchId", csvRecord.get(s));
                            break;

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }


}
