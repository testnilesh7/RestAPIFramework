package com.qa.api.circuit.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.XmlPathUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CircuitAPITest extends BaseTest {


    @Test
    public void getAllCircuitsTest() {
        // Implement the test logic here
        // For example, you can call the API endpoint and validate the response
         Response response = restClient.get(BASE_URL_EARGAST_AMADEUS, ERGAST_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);

        List<String> allCircutNames = XmlPathUtils.readList(response, "MRData.CircuitTable.Circuit.CircuitName");

        for (String e: allCircutNames){
        Assert.assertNotNull(e);
        }
        
       String americaLoc=XmlPathUtils.read(response, "**.find{ it.@circuitId == 'americas' }.Location.Locality");
       System.out.println("America Location : " + americaLoc );
       Assert.assertEquals(americaLoc, "Austin");
    }


    
}
