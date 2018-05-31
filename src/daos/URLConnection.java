package daos;

import Model.User;
import Utils.ParameterStringBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLConnection {

    /**
    Creates url connection and returns the response as a JSON object
     @String url
     @String params
     */
    private StringBuilder sendPOST(String url, String params) throws IOException, JSONException {

        //System.out.println("We're in the post method");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //con.setRequestProperty("User-Agent", USER_AGENT);

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();

        StringBuilder response = new StringBuilder();

        //get response code
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {    //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            //System.out.println(response.toString());
//            JSONObject responseJson = new JSONObject(response.toString());
//            responseJson.append("responseCode", responseCode);
            //System.out.println(responseJson);
            //return response;

//        }else{
//            JSONObject responseJson = new JSONObject();
//            responseJson.append("responseCode", responseCode);
//            return response;
//        }
        }
        return response;
    }

    public static boolean Login(String username, String password) throws IOException, JSONException {

        URLConnection connection = new URLConnection();

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("username" , username);
        parameters.put("password" , password);
        String postData = ParameterStringBuilder.getParamsString(parameters);

        StringBuilder response = connection.sendPOST("http://localhost:9001/loginServlet", postData);
        JSONObject responseJson = new JSONObject(response.toString());
        return responseJson.getString("auth").equals("OK");

        }

        public static boolean isAdmin(String username) throws IOException, JSONException {

            URLConnection connection = new URLConnection();

            Map<String, String> parameters = new LinkedHashMap<>();
            parameters.put("username" , username);
            String postData = ParameterStringBuilder.getParamsString(parameters);

            StringBuilder response = connection.sendPOST("http://localhost:9001/isAdminServlet", postData);
            JSONObject responseJson = new JSONObject(response.toString());
            return responseJson.getString("isAdmin").equals("true");

        }

        public static boolean createUser(String username, String password, String isAdmin) throws IOException, JSONException {

            URLConnection connection = new URLConnection();

            Map<String, String> parameters = new LinkedHashMap<>();
            parameters.put("username" , username);
            parameters.put("password" , password);
            parameters.put("isAdmin" , isAdmin);

            //send the parameters to the ParameterStringBuilder utility class for formatting
            String postData = ParameterStringBuilder.getParamsString(parameters);
            StringBuilder response = connection.sendPOST("http://localhost:9001/createUserServlet", postData);
            JSONObject responseJson = new JSONObject(response.toString());
            return responseJson.getString("response").equals("OK");
        }

        public static ArrayList<User> getUsers() throws IOException, JSONException {
            URLConnection connection = new URLConnection();

            Map<String, String> parameters = new LinkedHashMap<>();
            //send the parameters to the ParameterStringBuilder utility class for formatting
            String postData = ParameterStringBuilder.getParamsString(parameters);
            StringBuilder response = connection.sendPOST("http://localhost:9001/getUsersServlet", postData);
            //System.out.println(response);
            JSONObject responseJson = new JSONObject(response.toString());

            ArrayList<User> userList = new ArrayList<>();
            //System.out.println(responseJson);
//            Iterator<?> keys = responseJson.keys();
//            while( keys.hasNext() ) {
//                String key = (String) keys.next();
//                if (responseJson.get(key) instanceof JSONObject) {
//                    System.out.println(responseJson.get(key));
//                }
//            }

            for (Iterator it = responseJson.keys(); it.hasNext(); ) {
                String json = it.next().toString();
                JSONObject userJson = (responseJson.getJSONObject(json));
                User user = new User();
                user.setUsername(userJson.getString("username"));
                user.setIsAdmin(userJson.getString("isAdmin"));
                userList.add(user);
            }
            return userList;
        }

}
