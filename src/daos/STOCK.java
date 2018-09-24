package daos;

import Model.GoodsIn;
import Model.Product;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class STOCK {

    //Declare URLS
    private static String CREATESTOCK = "http://localhost:9001/createStockServlet";
    private static String EDITSTOCK = "http://localhost:9001/editStockServlet";
    private static String INCREMENTSTOCK = "http://localhost:9001/incrementStockServlet";
    private static String DECREMENTSTOCK = "http://localhost:9001/decrementStockServlet";
    private static String GETSTOCK = "http://localhost:9001/getStockServlet";
    private static String GETSTOCKBYBRANCH = "http://localhost:9001/getStockByBranchServlet";

    private static String authenticatedUser = "";
    private static String branchId = "";


    public static boolean createStock(Product product) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        //System.out.println(branchId);
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);
        //System.out.println(product.getId());
        parameters.put("productId", product.getId());

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(CREATESTOCK, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    public static boolean incrementStock(Product product) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);
        parameters.put("productId", product.getId());

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(INCREMENTSTOCK, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    public static ArrayList<Product> getStockByBranch() throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        ArrayList<Product> stockLevels = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETSTOCKBYBRANCH, postData);

        if(response.getString("connection").equals("true")){
            stockLevels = parseProductData(response);
        }
        return stockLevels;

    }

    private static ArrayList<Product> parseProductData(JSONObject response) throws JSONException {
        ArrayList<Product> productList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                JSONObject userJson = (response.getJSONObject(json));
                Product product = new Product();
                product.setId(userJson.getString("id"));
                product.setSKU(userJson.getString("SKU"));
                product.setName(userJson.getString("name"));
                product.setRRP(userJson.getString("RRP"));
                product.setCost(userJson.getString("cost"));
                product.setDepartment(userJson.getString("department"));
                product.setBrand(userJson.getString("brand"));
                product.setDescription(userJson.getString("description"));
                product.setQuantity(userJson.getInt("quantity"));
                productList.add(product);
            }
        }
        return productList;
    }

    /**
     * passes the currently authenticated user to the CUSTOMER dao to allow it to gain permission from the server
     * @param authenticatedUser the currently authenticated user id
     */
    public static void setAuthenticatedUser(String authenticatedUser) {
        STOCK.authenticatedUser = authenticatedUser;
    }

    public static void setBranch(String branchId) {
        STOCK.branchId = branchId;
    }
}
