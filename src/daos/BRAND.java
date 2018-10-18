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


    public static boolean createBrand(Brand brand) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(brand.getParameters());

        return CRUD.create(CREATEBRAND, parameters);
    }

    public static boolean editBrand(Brand brand) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(brand.getParameters());

        return CRUD.update(EDITBRAND, parameters);

    }


    public static ArrayList<Brand> getBrands() throws IOException, JSONException {

        ArrayList<Brand> brandList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETBRANDS, parameters);

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

}
