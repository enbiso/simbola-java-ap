package com.enbiso.simbola.ap;

import com.enbiso.simbola.ap.connection.SimConnection;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testConnection() {
        SimConnection connection = new SimConnection("http://dev-ctcinv.proj.enbiso.com", "injas", "injas");
        boolean output = connection.login();
        assertTrue(output);
    }
}
