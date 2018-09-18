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

    public static ArrayList<GoodsIn> getStockByBranch() throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        ArrayList<GoodsIn> stockLevels = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETSTOCKBYBRANCH, postData);

        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                //JSONObject userJson = (response.getJSONObject(json));
                GoodsIn e = new GoodsIn();
                Product product = PRODUCT.getProductById(json);
                //System.out.println(product.getSKU());
                e.setProduct(product);
                e.setQuantity(response.getInt(json));
                //System.out.println(json);
               // System.out.println(response.getInt(json));
                stockLevels.add(e);

            }
        }
        return stockLevels;

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
