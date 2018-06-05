package daos;

import Model.User;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class USER {

    private static String LOGIN = "http://localhost:9001/loginServlet";
    private static String ISADMIN = "http://localhost:9001/isAdminServlet";
    private static String CREATEUSER = "http://localhost:9001/createUserServlet";
    private static String GETUSERS = "http://localhost:9001/getUsersServlet";
    private static String DELETEUSER = "http://localhost:9001/deleteUserServlet";

    /**
     * Authentication function
     * @param username
     * @param password
     * @return true for valid login, false for failed.
     * @throws IOException
     * @throws JSONException
     */
    public static boolean authenticate(String username, String password) throws IOException, JSONException {

        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);
        String postData = ParameterStringBuilder.getParamsString(parameters);

        JSONObject response = connection.sendPOST(LOGIN, postData);

        return response.getString("connection").equals("true")
                && response.getString("auth").equals("OK");
    }

    /**
     * Sends post to server to check if user is an admin
     * @param username
     * @return true for user is Admin, false for is not
     * @throws IOException
     * @throws JSONException
     */
    public static boolean isAdmin(String username) throws IOException, JSONException {

        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("username" , username);
        String postData = ParameterStringBuilder.getParamsString(parameters);

        JSONObject response = connection.sendPOST(ISADMIN, postData);

        return response.getString("connection").equals("true")
                && response.getString("isAdmin").equals("true");
    }

    /**
     * Sends a post request to the server with appropriate variables to create a new user
     * @param username
     * @param password
     * @param isAdmin
     * @return creates a new user given the appropriate input variables
     * @throws IOException
     * @throws JSONException
     */
    public static boolean createUser(String username, String password, String isAdmin) throws IOException, JSONException {

        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("username" , username);
        parameters.put("password" , password);
        parameters.put("isAdmin" , isAdmin);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(CREATEUSER, postData);

        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");

    }

    /**
     * Deletes a target user
     * @param requestUser the user requesting the delete operation (to test for adminStatus)
     * @param targetUser the target to delete
     * @return boolean status true, for success, false for failure
     * @throws IOException
     * @throws JSONException
     */
    public static boolean deleteUser(String requestUser, String targetUser) throws IOException, JSONException {

        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("requestUser" , requestUser);
        parameters.put("targetUser" , targetUser);

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(DELETEUSER, postData);
        return response.getString("connection").equals("true")
                && response.getString("response").equals("OK");
    }


    /**
     * Sends post request to server to get a list of Users.
     * @return ArrayList of User objects
     * @throws IOException
     * @throws JSONException
     */
    public static ArrayList<User> getUsers() throws IOException, JSONException {

        URLConnection connection = new URLConnection();
        ArrayList<User> userList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        //send the parameters to the ParameterStringBuilder utility class for formatting
        String postData = ParameterStringBuilder.getParamsString(parameters);
        JSONObject response = connection.sendPOST(GETUSERS, postData);

        /**
         * This is inefficient, of order n^3 - REFACTOR ME
         */
        if(response.getString("connection").equals("true")){
            for (Iterator it = response.keys(); it.hasNext(); ) {
                String json = it.next().toString();
                //Skip connection response object.
                if(!json.equals("connection")) {
                    JSONObject userJson = (response.getJSONObject(json));
                    User user = new User();
                    user.setUsername(userJson.getString("username"));
                    user.setIsAdmin(userJson.getString("isAdmin"));
                    userList.add(user);
                }
            }
        }
        return userList;
    }
}
