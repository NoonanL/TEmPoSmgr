package daos;

import Model.Brand;
import Model.Product;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BRAND {


    //Declare URLS
    private static String GETBRANDS = "http://localhost:9001/getBrandsServlet";
    private static String CREATEBRAND = "http://localhost:9001/createBrandServlet";
    private static String EDITBRAND = "http://localhost:9001/editBrandServlet";

    private static String authenticatedUser = "";
    private static String branchId = "";

    public static boolean createBrand(Brand brand) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.putAll(brand.getParameters());

        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(CREATEBRAND, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");
    }

    public static boolean editBrand(Brand brand) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);
        parameters.putAll(brand.getParameters());

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(EDITBRAND, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }


    public static ArrayList<Brand> getBrands() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Brand> brandList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETBRANDS, postData);

        if(response.getString("connection").equals("true")){
            brandList = parseBrandData(response);
        }
        return brandList;
    }


    private static ArrayList<Brand> parseBrandData(JSONObject response) throws JSONException {
        ArrayList<Brand> brandList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                JSONObject userJson = (response.getJSONObject(json));
                Brand brand = new Brand();
                brand.setId(userJson.getString("id"));
                brand.setBrand(userJson.getString("brand"));
                brand.setDistributor(userJson.getString("distributor"));
                brandList.add(brand);
            }
        }
        return brandList;
    }

    public static ArrayList<String> getBrandList() throws IOException, JSONException {
        ArrayList<String> brandList = new ArrayList<>();
        for(Brand x : getBrands()){
            brandList.add(x.getBrand());
        }
        return  brandList;
    }

    /**
     * passes the currently authenticated user to the CUSTOMER dao to allow it to gain permission from the server
     * @param authenticatedUser the currently authenticated user id
     */
    public static void setAuthenticatedUser(String authenticatedUser) {
        BRAND.authenticatedUser = authenticatedUser;
    }

    public static void setBranch(String branchId) {
        BRAND.branchId = branchId;
    }
}
