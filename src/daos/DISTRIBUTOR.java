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

    private static String CREATEDISTRIBUTOR = "http://localhost:9001/createDistributorServlet";
    private static String GETDISTRIBUTORS = "http://localhost:9001/getDistributorsServlet";
    private static String EDITDISTRIBUTOR = "http://localhost:9001/editDistributorServlet";

    public static boolean createDistributor(Distributor distributor) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>(distributor.getParameters());
        return CRUD.create(CREATEDISTRIBUTOR, parameters);

    }

    public static boolean editDistributor(Distributor distributor) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(distributor.getParameters());

        return CRUD.update(EDITDISTRIBUTOR, parameters);
    }

    public static ArrayList<Distributor> getDistributors() throws IOException, JSONException {

        ArrayList<Distributor> distributorList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETDISTRIBUTORS, parameters);

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


}
