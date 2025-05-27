package com.qa.api.amadeus.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest {
	
	private String accessToken;
	
	@BeforeMethod
	public void getOAuth2Token() {
		
	Response response=restClient.post(BASE_URL_OAUTH2_AMADEUS, AMADEUS_OAUTH2_TOKEN_ENDPOINT, 
			ConfigManager.get("amadeus-clientid"), 
			ConfigManager.get("amadeus-clientsecret"), 
			ConfigManager.get("amadeus-granttype"), ContentType.URLENC);
	
		accessToken =response.getBody().jsonPath().getString("access_token");
		
		System.out.println("Print Auth2 Amadeus Token====> : " + accessToken);	
		ConfigManager.set("BearerToken", accessToken);
	}
	
	
	@Test
	public void getFlightDetailsTest() {	
	//	Maps.of("origin", "PAR","maxPrice","200");
				
		Map<String, String>	queryParams = new HashMap<String, String>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", "200");
		
	Response response=restClient.get(BASE_URL_OAUTH2_AMADEUS, AMADEUS_FLIGHT_DEST_ENDPOINT , queryParams, null, AuthType.BEARER_TOKEN, ContentType.ANY);
	Assert.assertEquals(response.statusCode(), 200);
		
	}

}
