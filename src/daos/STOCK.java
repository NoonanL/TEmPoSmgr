package daos;

import Model.Product;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class STOCK {
    private static String CREATESTOCK = "http://localhost:9001/createStockServlet";
    private static String EDITSTOCK = "http://localhost:9001/editStockServlet";
    private static String INCREMENTSTOCK = "http://localhost:9001/incrementStockServlet";
    private static String DECREMENTSTOCK = "http://localhost:9001/decrementStockServlet";
    private static String GETSTOCK = "http://localhost:9001/getStockServlet";
    private static String GETSTOCKBYBRANCH = "http://localhost:9001/getStockByBranchServlet";
    private static String CREATEPURCHASEORDER = "http://localhost:9001/createPurchaseOrderServlet";
    private static String GETPURCHASEORDERS = "http://localhost:9001/getPurchaseOrdersServlet";


    public static boolean createStock(Product product) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("productId", product.getId());


        return CRUD.create(CREATESTOCK, parameters);

    }

    public static boolean incrementStock(Product product) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("productId", product.getId());

        return CRUD.update(INCREMENTSTOCK, parameters);

    }

    public static ArrayList<Product> getStockByBranch() throws IOException, JSONException {

        ArrayList<Product> stockLevels = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETSTOCKBYBRANCH, parameters);

        if(response.getString("connection").equals("true")){
            stockLevels = parseProductData(response);
        }
        return stockLevels;

    }

 //   public static boolean createPurchaseOrder(GoodsOrder goodsIn) throws IOException, JSONException {

//        Map<String, String> parameters = new LinkedHashMap<>();
//        parameters.putAll(goodsIn.getParameters());
//
//
//        return CRUD.create(CREATEPURCHASEORDER, parameters);
//    }

 //   public static ArrayList<GoodsOrder> getPurchaseOrders() throws IOException, JSONException {

//        ArrayList<GoodsOrder> purchaseOrders = new ArrayList<>();
//        Map<String, String> parameters = new LinkedHashMap<>();
//
//        JSONObject response = CRUD.retrieve(GETPURCHASEORDERS, parameters);
//
//        if(response.getString("connection").equals("true")){
//            purchaseOrders = parsePurchaseOrders(response);
//        }
//        return purchaseOrders;
//    }

//    private static ArrayList<GoodsOrder> parsePurchaseOrders(JSONObject response) throws JSONException {
//        ArrayList<GoodsOrder> goodsIn = new ArrayList<>();
//        for (Iterator it = response.keys(); it.hasNext(); ) {
//            String json = it.next().toString();
//            //Skip connection response object.
//            if(!json.equals("connection") && !json.equals("error") && !json.equals("response") && !json.equals("sessionId")) {
//                JSONObject userJson = (response.getJSONObject(json));
//                Product goodsInProduct = new Product();
//                GoodsOrder newGoodsIn = new GoodsOrder();
//                newGoodsIn.setId(userJson.getString("id"));
//                goodsInProduct.setSKU(userJson.getString("SKU"));
//                newGoodsIn.setUID(userJson.getString("UID"));
//                goodsInProduct.setQuantity(Integer.parseInt(userJson.getString("quantity")));
//                newGoodsIn.setProduct(goodsInProduct);
//                newGoodsIn.setQuantity(userJson.getInt("quantity"));
//                goodsIn.add(newGoodsIn);
//            }
//        }
//        return goodsIn;
//    }


    private static ArrayList<Product> parseProductData(JSONObject response) throws JSONException {
        ArrayList<Product> productList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response") && !json.equals("sessionId")) {
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

}
