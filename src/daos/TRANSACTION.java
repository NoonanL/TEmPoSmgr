package daos;

import Model.Customer;
import Model.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TRANSACTION {

    //declare URLS
    private static String CREATETRANSACTION = "http://localhost:9001/createTransactionServlet";
    private static String GETTRANSACTIONS = "http://localhost:9001/getTransactionsServlet";

    public static boolean createTransaction(Transaction transaction) throws IOException, JSONException {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.putAll(transaction.getParameters());

        return CRUD.create(CREATETRANSACTION, parameters);
    }

    public static ArrayList<Transaction> getTransactions() throws IOException, JSONException {
        URLConnection connection = new URLConnection();
        ArrayList<Transaction> transactionList = new ArrayList<>();
        Map<String, String> parameters = new LinkedHashMap<>();

        JSONObject response = CRUD.retrieve(GETTRANSACTIONS, parameters);

        if(response.getString("connection").equals("true")){
            transactionList = parseTransactionData(response);
        }
        return transactionList;
    }

    private static ArrayList<Transaction> parseTransactionData(JSONObject response) throws JSONException {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        JSONArray transactionArray = response.getJSONArray("transactions");
        //System.out.println(test);
        for (int i = 0; i < transactionArray.length(); i++) {
            JSONObject userJson = transactionArray.getJSONObject(i);
            Transaction transaction = new Transaction();
            transaction.setId(userJson.getString("id"));
            transaction.setCustomerId(userJson.getString("customerId"));
            transaction.setCustomerName(userJson.getString("customerName"));
            transaction.setProductId(userJson.getString("productId"));
            transaction.setProductName(userJson.getString("productName"));
            transaction.setQuantity(userJson.getString("quantity"));
            transactionList.add(transaction);
        }

        return transactionList;
    }
}
