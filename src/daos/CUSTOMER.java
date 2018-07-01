package daos;

import Model.Customer;
import Model.User;
import Utils.ParameterStringBuilder;
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

    private static String authenticatedUser = "";

    /**
     * Sends request to server to create a new customer.
     * @param customer a customer object from which to create a new customer record on the server
     * @return boolean true/false for success or failure
     * @throws IOException
     * @throws JSONException
     */
    public static boolean createCustomer(Customer customer) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("firstname" , customer.getFirstname().get());
        parameters.put("surname" , customer.getSurname().get());
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(CREATECUSTOMER, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");
    }

    /**
     * Sends a request to the server to edit a customer
     * @param targetCustomer the customer to edit
     * @param firstname the updated customer firstname
     * @param surname the updated customer surname
     * @return boolean for success or failure
     * @throws IOException
     * @throws JSONException
     */
    public static boolean editCustomer(String targetCustomer, String firstname, String surname) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("requestUser" , authenticatedUser);
        parameters.put("targetCustomerId" , targetCustomer);
        parameters.put("firstname" , firstname);
        parameters.put("surname" , surname);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(EDITCUSTOMER, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    /**
     * Sends a request to the server to delete a customer
     * @param targetCustomer the customer to attempt to delete
     * @return boolean for success or failure of request
     * @throws IOException
     * @throws JSONException
     */
    public static boolean deleteCustomer(String targetCustomer) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("requestUser" , authenticatedUser);
        parameters.put("targetCustomerId" , targetCustomer);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(DELETECUSTOMER, postData);
        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

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
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETCUSTOMERS, postData);

        if(response.getString("connection").equals("true")){
            customerList = parseCustomerData(response);
        }
        return customerList;
    }

    /**
     * Send request to server to search customer data
     * @param searchString the user#s search string
     * @return an ArrayList of customer objects which match the search criteria
     * @throws IOException
     * @throws JSONException
     */
    public static ArrayList<Customer> searchCustomers(String searchString) throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Customer> customerList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("requestUser", authenticatedUser);
        parameters.put("searchString", searchString);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(SEARCHCUSTOMERS, postData);

        /**
         * This is inefficient, of order n^3 - REFACTOR ME
         */
        if(response.getString("connection").equals("true")){
            customerList = parseCustomerData(response);
        }
        return customerList;
    }

    /**
     * passes the currently authenticated user to the CUSTOMER dao to allow it to gain permission from the server
     * @param authenticatedUser the currently authenticated user id
     */
    public static void setAuthenticatedUser(String authenticatedUser) {
        CUSTOMER.authenticatedUser = authenticatedUser;
    }

    /**
     * method to parse an ArrayList of customer objects from a json object recieved by the server.
     * @param response the json object to parse
     * @return an arraylist of customers
     * @throws JSONException
     */
    private static ArrayList<Customer> parseCustomerData(JSONObject response) throws JSONException {
        ArrayList<Customer> customerList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection")) {
                JSONObject userJson = (response.getJSONObject(json));
                Customer customer = new Customer();
                customer.setId(userJson.getString("id"));
                customer.setFirstname(userJson.getString("firstname"));
                customer.setSurname(userJson.getString("surname"));
                customerList.add(customer);
            }
        }
        return customerList;
    }

}
