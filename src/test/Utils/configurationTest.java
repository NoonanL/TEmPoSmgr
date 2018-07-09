package Utils;

import TEmPoSmgr.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class configurationTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void configureTest() {
        Configuration configuration = new Configuration();
        configuration.loadConfiguration();
        configuration.editConfiguration("branchId","TESTBRANCH");
    }
}
