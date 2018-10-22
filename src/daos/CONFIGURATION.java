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


    public static ArrayList<String> getBranchList() throws IOException, JSONException {

        ArrayList<String> branchList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETBRANCHLIST, parameters);

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
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response") && !json.equals("sessionId")) {
                branchList.add(response.getString(json));
            }
        }
        return branchList;
    }


}
