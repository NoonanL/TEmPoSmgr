package daos;

import Model.Brand;
import Model.Department;
import Model.Distributor;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DISTRIBUTOR  {

    private static String GETDISTRIBUTORS = "http://localhost:9001/getDistributorsServlet";
    private static String CREATEDISTRIBUTOR = "http://localhost:9001/createDistributorServlet";
    private static String EDITDISTRIBUTOR = "http://localhost:9001/editDistributorServlet";

    private static String authenticatedUser = "";
    private static String branchId = "";


    public static boolean createDistributor(Distributor distributor) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.putAll(distributor.getParameters());

        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(CREATEDISTRIBUTOR, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");
    }

    public static boolean editDistributor(Distributor distributor) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);
        parameters.putAll(distributor.getParameters());

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(EDITDISTRIBUTOR, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    public static ArrayList<Distributor> getDistributors() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Distributor> distributorList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETDISTRIBUTORS, postData);

        if(response.getString("connection").equals("true")){
            distributorList = parseDistributorData(response);
        }
        return distributorList;
    }

    private static ArrayList<Distributor> parseDistributorData(JSONObject response) throws JSONException {
        ArrayList<Distributor> distributorList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                JSONObject userJson = (response.getJSONObject(json));
                Distributor distributor = new Distributor();
                distributor.setId(userJson.getString("id"));
                distributor.setName(userJson.getString("name"));
                distributorList.add(distributor);
            }
        }
        return distributorList;
    }

    public static ArrayList<String> getDistributorList() throws IOException, JSONException {
        ArrayList<String> distributorList = new ArrayList<>();
        for(Distributor x : getDistributors()){
            distributorList.add(x.getName());
        }
        return  distributorList;
    }

    /**
     * passes the currently authenticated user to the CUSTOMER dao to allow it to gain permission from the server
     * @param authenticatedUser the currently authenticated user id
     */
    public static void setAuthenticatedUser(String authenticatedUser) {
        DISTRIBUTOR.authenticatedUser = authenticatedUser;
    }

    public static void setBranch(String branchId) {
        DISTRIBUTOR.branchId = branchId;
    }

}
