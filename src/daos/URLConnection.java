package daos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLConnection {

    /**
    Creates url connection and returns the response as a JSON object
     @String url
     @String params
     */
    public JSONObject sendPOST(String url, String params) throws IOException {

        StringBuilder response = new StringBuilder();
        JSONObject responseJson = null;

        try{
            //System.out.println("We're in the post method");
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("secret", "I am the server's secret!");

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

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
                responseJson = new JSONObject(response.toString());
                responseJson.put("connection", "true");
                //System.out.println(responseJson.toString());
            }else {
                Map<String, String> errorResponse = new LinkedHashMap<>();
                errorResponse.put("connection", "false");
                responseJson = new JSONObject(errorResponse);
            }

        }catch(ConnectException e){
            Map<String, String> errorResponse = new LinkedHashMap<>();
            errorResponse.put("connection", "false");
            responseJson = new JSONObject(errorResponse);
            //e.printStackTrace();
            System.out.println("Post Request Failed, no connection to server.");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Json Error.");
        }

        return responseJson;

    }

}
