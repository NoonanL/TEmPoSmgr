package daos;

import Model.Distributor;
import Model.GoodsOrder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GOODSORDER {

    private static String CREATEGOODSORDER = "http://localhost:9001/createGoodsOrderServlet";
    private static String GETGOODSORDERS = "http://localhost:9001/getGoodsOrderServlet";
    private static String EDITGOODSORDER = "http://localhost:9001/editGoodsOrderServlet";
    private static String DELETEGOODSORDER = "http://localhost:9001/deleteGoodsOrderServlet";

    public static boolean createGoodsOrder(GoodsOrder goodsOrder) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>(goodsOrder.getParameters());
        return CRUD.create(CREATEGOODSORDER, parameters);
    }

    public static boolean editGoodsOrder(GoodsOrder goodsOrder) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>(goodsOrder.getParameters());
        return CRUD.create(EDITGOODSORDER, parameters);
    }

    public static boolean deleteGoodsOrder(int targetId) throws IOException, JSONException {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("goodsOrderId", Integer.toString(targetId));
        return CRUD.delete(DELETEGOODSORDER, parameters);
    }

    public static ArrayList<GoodsOrder> getGoodsOrdersByUID(String UID) throws IOException, JSONException {

        ArrayList<GoodsOrder> goodsOrderList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("UID", UID);
        JSONObject response = CRUD.retrieve(GETGOODSORDERS, parameters);
        //System.out.println(response);
        if(response.getString("connection").equals("true")){
            goodsOrderList = parseGoodsOrderData(response);
        }
        return goodsOrderList;
    }


    private static ArrayList<GoodsOrder> parseGoodsOrderData(JSONObject response) throws JSONException{
        ArrayList<GoodsOrder> goodsOrderList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response") && !json.equals("sessionId")) {
                JSONObject userJson = (response.getJSONObject(json));
                GoodsOrder goods = new GoodsOrder();
                goods.setId(userJson.getString("id"));
                goods.setProductId(userJson.getString("productId"));
                goods.setQuantity(Integer.parseInt(userJson.getString("quantity")));
                goods.setStatus(userJson.getString("status"));
                goods.setUID(userJson.getString("UID"));
                goodsOrderList.add(goods);
            }
        }
        return goodsOrderList;
    }
}
