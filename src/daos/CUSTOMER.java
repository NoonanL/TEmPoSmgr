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
    private static String EDITCUSTOMER = "http://localhost:9001/editCustomersServlet";
    private static String DELETECUSTOMER = "http://localhost:9001/deleteCustomerServlet";

    private static String authenticatedUser = "";

    //create customer
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

    public static boolean editCustomer(String targetCustomer, String firstname, String surname) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("requestUser" , authenticatedUser);
        parameters.put("targetCustomer" , targetCustomer);
        parameters.put("firstname" , firstname);
        parameters.put("surname" , surname);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(EDITCUSTOMER, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

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



    //get Customers
    public static ArrayList<Customer> getCustomers() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Customer> customerList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETCUSTOMERS, postData);

        /**
         * This is inefficient, of order n^3 - REFACTOR ME
         */
        if(response.getString("connection").equals("true")){
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
        }
        return customerList;
    }
    //edit customer
    //delete customer
    //search customers

    public static void setAuthenticatedUser(String authenticatedUser) {
        CUSTOMER.authenticatedUser = authenticatedUser;
    }

}
