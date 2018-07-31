package daos;

import Model.Brand;
import Model.Department;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DEPARTMENT {

    //Declare URLS
    private static String GETDEPARTMENTS = "http://localhost:9001/getDepartmentsServlet";
    private static String CREATEDEPARTMENT = "http://localhost:9001/createDepartmentServlet";
    private static String EDITDEPARTMENT = "http://localhost:9001/editDepartmentServlet";

    private static String authenticatedUser = "";
    private static String branchId = "";

    public static boolean createDepartment(Department department) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.putAll(department.getParameters());

        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(CREATEDEPARTMENT, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");
    }

    public static boolean editDepartment(Department department) throws IOException, JSONException {
        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser" , authenticatedUser);
        parameters.putAll(department.getParameters());

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(EDITDEPARTMENT, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    public static ArrayList<Department> getDepartments() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Department> departmentList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("branchId", branchId);
        parameters.put("requestUser", authenticatedUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETDEPARTMENTS, postData);

        if(response.getString("connection").equals("true")){
            departmentList = parseDepartmentData(response);
        }
        return departmentList;
    }

    private static ArrayList<Department> parseDepartmentData(JSONObject response) throws JSONException {
        ArrayList<Department> departmentList = new ArrayList<>();
        for (Iterator it = response.keys(); it.hasNext(); ) {
            String json = it.next().toString();
            //Skip connection response object.
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response")) {
                JSONObject userJson = (response.getJSONObject(json));
                Department department = new Department();
                department.setId(userJson.getString("id"));
                department.setDepartment(userJson.getString("department"));
                departmentList.add(department);
            }
        }
        return departmentList;
    }

    public static ArrayList<String> getDepartmentList() throws IOException, JSONException {
        ArrayList<String> brandList = new ArrayList<>();
        for(Department x : getDepartments()){
            brandList.add(x.getDepartment());
        }
        return  brandList;
    }


    /**
     * passes the currently authenticated user to the CUSTOMER dao to allow it to gain permission from the server
     * @param authenticatedUser the currently authenticated user id
     */
    public static void setAuthenticatedUser(String authenticatedUser) {
        DEPARTMENT.authenticatedUser = authenticatedUser;
    }

    public static void setBranch(String branchId) {
        DEPARTMENT.branchId = branchId;
    }
}
