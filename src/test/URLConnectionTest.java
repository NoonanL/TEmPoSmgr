package test;

import Model.User;
import daos.URLConnection;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class URLConnectionTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void login() throws IOException, JSONException {
        if(URLConnection.Login("Test User","password")){
            System.out.println("LOGGED IN");
        }else{
            System.out.println("LOGIN FAILED");
        }
    }

    @org.junit.Test
    public void getUsers() throws IOException, JSONException {
        ArrayList<User> array = URLConnection.getUsers();
        for(int i =0; i<array.size();i++){
            System.out.println(array.get(i).getUsername());
        }
    }
}
