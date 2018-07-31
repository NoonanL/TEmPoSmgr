package daos;

import Model.Customer;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CONFIGURATION {

    //Declare URLS
    private static String GETBRANCHLIST = "http://localhost:9001/branchesServlet";

    private static String authenticatedUser = "";
    private static String branchId = "";

    public static ArrayList<String> getBranchList() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<String> branchList = new ArrayList<>();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETBRANCHLIST, postData);

        if (response.getString("connection").equals("true")) {
            branchList = parseBranchList(response);
        }
        return branchList;
    }

    private static ArrayList<String> parseBranchList(JSONObject response) throws JSONException {
        ArrayList<String> branchList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //System.out.println(json);
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                branchList.add(response.getString(json));
            }
        }
        return branchList;
    }


    /**
     * passes the currently authenticated user to the CUSTOMER dao to allow it to gain permission from the server
     * @param authenticatedUser the currently authenticated user id
     */
    public static void setAuthenticatedUser(String authenticatedUser) {
        CONFIGURATION.authenticatedUser = authenticatedUser;
    }

    public static void setBranch(String branchId) {
        CONFIGURATION.branchId = branchId;
    }
}
