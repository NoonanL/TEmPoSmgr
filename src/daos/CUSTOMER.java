package daos;

import Model.Customer;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CUSTOMER {


    //Declare URLS
    private static String CREATECUSTOMER = "http://localhost:9001/createCustomerServlet";

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
    //edit customer
    //delete customer
    //search customers

    public static void setAuthenticatedUser(String authenticatedUser) {
        CUSTOMER.authenticatedUser = authenticatedUser;
    }

}
