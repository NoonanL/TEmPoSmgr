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


    public static boolean createDepartment(Department department) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(department.getParameters());

        return CRUD.create(CREATEDEPARTMENT, parameters);
    }

    public static boolean editDepartment(Department department) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(department.getParameters());

        return CRUD.update(EDITDEPARTMENT, parameters);

    }

    public static ArrayList<Department> getDepartments() throws IOException, JSONException {

        ArrayList<Department> departmentList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETDEPARTMENTS, parameters);

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
            if(!json.equals("connection") && !json.equals("error") && !json.equals("response") && !json.equals("sessionId")) {
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

}
