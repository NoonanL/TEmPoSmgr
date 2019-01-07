package daos;

import Model.Customer;
import Model.Product;
import Utils.ParameterStringBuilder;
import org.json.JSONArray;
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
    private static String GETPRODUCTBYID = "http://localhost:9001/getProductByIdServlet";

    public static boolean createProduct(Product product) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(product.getParameters());


        return CRUD.create(CREATEPRODUCT, parameters);
    }

    public static boolean editProduct(Product product) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(product.getParameters());


        return CRUD.update(EDITPRODUCT, parameters);

    }

    public static boolean deleteProduct(String targetProduct) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("targetProductId" , targetProduct);

        return CRUD.delete(DELETEPRODUCT, parameters);

    }

    public static ArrayList<Product> getProducts() throws IOException, JSONException {

        ArrayList<Product> productList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETPRODUCTS, parameters);

        if(response.getString("connection").equals("true")){
            productList = parseProductData(response);
        }
        return productList;
    }

    public static Product getProductById(String id) throws IOException, JSONException {

        Product product = new Product();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("id", id);

        JSONObject response = CRUD.retrieve(GETPRODUCTBYID, parameters);
        if(response.getString("connection").equals("true")){
            for (Iterator it = response.keys(); it.hasNext(); ) {
                String json = it.next().toString();
                //Skip connection response object.
                if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                    JSONObject userJson = (response.getJSONObject(json));
                    product.setId(userJson.getString("id"));
                    product.setSKU(userJson.getString("SKU"));
                    product.setName(userJson.getString("name"));
                    product.setRRP(userJson.getString("RRP"));
                    product.setCost(userJson.getString("cost"));
                    product.setDepartment(userJson.getString("department"));
                    product.setBrand(userJson.getString("brand"));
                    product.setDescription(userJson.getString("description"));
                }
            }
        }
        return product;
    }


    public static ArrayList<Product> searchProducts(String searchString) throws IOException, JSONException {

        ArrayList<Product> productList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("searchString", searchString);


        JSONObject response = CRUD.retrieve(SEARCHPRODUCTS, parameters);

        if(response.getString("connection").equals("true")){
            productList = parseProductData(response);
        }
        return productList;
    }



    private static ArrayList<Product> parseProductData(JSONObject response) throws JSONException {
        ArrayList<Product> productList = new ArrayList<>();
        JSONArray productArray = response.getJSONArray("products");
            //Skip connection response object.
            for (int i = 0; i < productArray.length(); i++) {
                JSONObject userJson = (productArray.getJSONObject(i));
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

        return productList;
    }



}
