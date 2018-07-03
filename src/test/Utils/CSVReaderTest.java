package Utils;

import Model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CSVReaderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parseCSV() {

        ArrayList<String> headers = new ArrayList<>();// = {"firstname", "surname"};
        headers.add("firstname");
        headers.add("surname");

        ArrayList<Customer> customerList = CSVReader.parseCustomerCSV("C:\\Users\\LiamN\\Desktop/testCSV.csv", headers);
        for(Customer c : customerList){
            System.out.println("List item - " + customerList.indexOf(c));
            System.out.println("---------------");
            System.out.println("User's First Name  : " + c.getFirstname());
            System.out.println("User's Second Name  : " + c.getSurname());
            System.out.println("---------------\n\n");
        }

    }
}