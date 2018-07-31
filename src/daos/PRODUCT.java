package daos;

import Model.Customer;
import Model.Product;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PRODUCT {

    //Declare URLS
    private static String CREATEPRODUCT = "http://localhost:9001/createProductServlet";
    private static String EDITPRODUCT = "http://localhost:9001/editProductServlet";
    private static String DELETEPRODUCT = "http://localhost:9001/deleteProductServlet";
    private static String GETPRODUCTS = "http://localhost:9001/getProductsServlet";
    private static String SEARCHPRODUCTS = "http://localhost:9001/searchProductsServlet";

    private static String authenticatedUser = "";
    private static String branchId = "";

    public static boolean createProduct(Product product) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.putAll(product.getParameters());

        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(CREATEPRODUCT, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");
    }

    public static boolean editProduct(Product product) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);
        parameters.putAll(product.getParameters());

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(EDITPRODUCT, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    public static boolean deleteProduct(String targetProduct) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);
        parameters.put("id" , targetProduct);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(DELETEPRODUCT, postData);
        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    public static ArrayList<Product> getProducts() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Product> productList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETPRODUCTS, postData);

        if(response.getString("connection").equals("true")){
            productList = parseProductData(response);
        }
        return productList;
    }

    public static ArrayList<Product> searchProducts(String searchString) throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Product> productList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser", authenticatedUser);
        parameters.put("searchString", searchString);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(SEARCHPRODUCTS, postData);

        if(response.getString("connection").equals("true")){
            productList = parseProductData(response);
        }
        return productList;
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
        PRODUCT.authenticatedUser = authenticatedUser;
    }

    public static void setBranch(String branchId) {
        PRODUCT.branchId = branchId;
    }


}
