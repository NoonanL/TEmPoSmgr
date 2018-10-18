package daos;

import Utils.ParameterStringBuilder;
import com.sun.deploy.net.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CRUD {

    private static String authenticatedUser = "";
    private static String branchId = "";


    /**
     * Sends a post request to the server for the creation of objects (also used for updating)
     * @param address  the servlet address
     * @param parameters the parameter hashmap of the object for the request
     * @return true if both the response and connection flags in the server response are true
     * @throws IOException
     * @throws JSONException
     */
    public static boolean create(String address, Map<String, String> parameters) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        //Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        //parameters.putAll(product.getParameters());

        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(address, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");
    }

    /**
     * Sends a post request to the server for the retrieval of objects
     * @param address the servlet address
     * @param parameters the parameter hashmap for the request
     * @return the response from the server in JSON form
     * @throws IOException
     * @throws JSONException
     */
    static JSONObject retrieve(String address, Map<String, String> parameters) throws IOException, JSONException {

        URLConnection connection = new URLConnection();
        parameters.put("branchId", branchId);
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(address, postData);

        return response;

    }

    /**
     * Sends a post request to the server for the updating of objects, re-uses the create method as they use the same format
     * @param address the servlet address
     * @param parameters the parameter hashmap of the updated object for the request
     * @return true on success, false otherwise
     * @throws IOException
     * @throws JSONException
     */
    static boolean update(String address, Map<String, String> parameters) throws IOException, JSONException {
        return create(address, parameters);
    }

    /**
     * Sends a post request to the server for the deletion of objects, re-uses the create method as they use the same format
     * @param address the servlet address
     * @param parameters the parameter hashmap of the updated object for the request
     * @return true on success, false otherwise
     * @throws IOException
     * @throws JSONException
     */
    static boolean delete(String address, Map<String, String> parameters) throws IOException, JSONException {
        return create(address, parameters);
    }

    /**
     * passes the currently authenticated user to the CUSTOMER dao to allow it to gain permission from the server
     * @param authenticatedUser the currently authenticated user id
     */
    public static void setAuthenticatedUser(String authenticatedUser) {
        CRUD.authenticatedUser = authenticatedUser;
    }

    public static void setBranch(String branchId) {
        CRUD.branchId = branchId;
    }

}
