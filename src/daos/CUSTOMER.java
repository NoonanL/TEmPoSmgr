package daos;

import Model.Customer;
import Model.User;
import Utils.ParameterStringBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CUSTOMER {


    //Declare URLS
    private static String CREATECUSTOMER = "http://localhost:9001/createCustomerServlet";
    private static String GETCUSTOMERS = "http://localhost:9001/getCustomersServlet";
    private static String EDITCUSTOMER = "http://localhost:9001/editCustomerServlet";
    private static String DELETECUSTOMER = "http://localhost:9001/deleteCustomerServlet";
    private static String SEARCHCUSTOMERS = "http://localhost:9001/searchCustomerServlet";
    private static String GETCUSTOMERBYID = "http://localhost:9001/getCustomerByIdServlet";

    /**
     * Sends request to server to create a new customer.
     * @param customer a customer object from which to create a new customer record on the server
     * @return boolean true/false for success or failure
     * @throws IOException
     * @throws JSONException
     */
    public static boolean createCustomer(Customer customer) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(customer.getParameters());

        return CRUD.create(CREATECUSTOMER, parameters);
    }

    /**
     * Sends a request to the server to edit a customer
     * @return boolean for success or failure
     * @throws IOException
     * @throws JSONException
     */
    public static boolean editCustomer(Customer customer) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(customer.getParameters());

        return CRUD.update(EDITCUSTOMER, parameters);

    }

    /**
     * Sends a request to the server to delete a customer
     * @param targetCustomer the customer to attempt to delete
     * @return boolean for success or failure of request
     * @throws IOException
     * @throws JSONException
     */
    public static boolean deleteCustomer(String targetCustomer) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("targetCustomerId" , targetCustomer);

        return CRUD.delete(DELETECUSTOMER, parameters);

    }

    /**
     * Get all customer data from the server
     * @return an ArrayList of customer objects
     * @throws IOException
     * @throws JSONException
     */
    public static ArrayList<Customer> getCustomers() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Customer> customerList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETCUSTOMERS, parameters);

        if(response.getString("connection").equals("true")){
            customerList = parseCustomerData(response);
        }
        return customerList;
    }

    public static Customer getCustomerById(String id) throws IOException, JSONException {


        Customer customer = new Customer();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("id",id);


        JSONObject response = CRUD.retrieve(GETCUSTOMERBYID, parameters);

        if(response.getString("connection").equals("true")){
            for (Iterator it = response.keys(); it.hasNext(); ) {
                String json = it.next().toString();
                //Skip connection response object.
                if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                    JSONObject userJson = (response.getJSONObject(json));
                    customer.setId(userJson.getString("id"));
                    customer.setTitle(userJson.getString("title"));
                    customer.setFirstname(userJson.getString("firstname"));
                    customer.setSurname(userJson.getString("surname"));
                    customer.setStreet(userJson.getString("street"));
                    customer.setTown(userJson.getString("town"));
                    customer.setPostcode(userJson.getString("postcode"));
                    customer.setCity(userJson.getString("city"));
                    customer.setCountry(userJson.getString("country"));
                    customer.setMobile(userJson.getString("mobile"));
                    customer.setEmail(userJson.getString("email"));
                    customer.setMarketingStatus(userJson.getString("marketingStatus"));
                }
            }
        }
        return customer;
    }


    /**
     * Send request to server to search customer data
     * @param searchString the user#s search string
     * @return an ArrayList of customer objects which match the search criteria
     * @throws IOException
     * @throws JSONException
     */
    public static ArrayList<Customer> searchCustomers(String searchString) throws IOException, JSONException {

        ArrayList<Customer> customerList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("searchString", searchString);

        JSONObject response = CRUD.retrieve(SEARCHCUSTOMERS, parameters);

        if(response.getString("connection").equals("true")){
            customerList = parseCustomerData(response);
        }
        return customerList;
    }



    /**
     * method to parse an ArrayList of customer objects from a json object recieved by the server.
     * @param response the json object to parse
     * @return an arraylist of customers
     * @throws JSONException
     */
    private static ArrayList<Customer> parseCustomerData(JSONObject response) throws JSONException {
        ArrayList<Customer> customerList = new ArrayList<>();
        JSONArray customerArray = response.getJSONArray("customers");
        //System.out.println(test);
        for (int i = 0; i < customerArray.length(); i++) {
            JSONObject userJson = customerArray.getJSONObject(i);
            Customer customer = new Customer();
            customer.setId(userJson.getString("id"));
            customer.setTitle(userJson.getString("title"));
            customer.setFirstname(userJson.getString("firstname"));
            customer.setSurname(userJson.getString("surname"));
            customer.setStreet(userJson.getString("street"));
            customer.setTown(userJson.getString("town"));
            customer.setPostcode(userJson.getString("postcode"));
            customer.setCity(userJson.getString("city"));
            customer.setCountry(userJson.getString("country"));
            customer.setMobile(userJson.getString("mobile"));
            customer.setEmail(userJson.getString("email"));
            customer.setMarketingStatus(userJson.getString("marketingStatus"));
            customerList.add(customer);
        }


        return customerList;
    }


}
