package Utils;

import Model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

        ArrayList<Customer> customerList = CSVReader.parseCSV("C:\\Users\\LiamN\\Desktop/testCSV.csv", headers);
        for(Customer c : customerList){
            System.out.println("List item - " + customerList.indexOf(c));
            System.out.println("---------------");
            System.out.println("User's First Name  : " + c.getFirstname().get());
            System.out.println("User's Second Name  : " + c.getSurname().get());
            System.out.println("---------------\n\n");
        }

    }
}