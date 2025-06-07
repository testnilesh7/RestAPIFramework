package com.qa.api.mocking.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockGetUserAPITest extends BaseTest {
	
	@Test
	public void getDummyUserMockAPI() {
		APIMocks.defineGetUserMock();
		
	Response response=restClient.get(BASE_URL_MOCK_SERVER, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
	response.then().assertThat().statusCode(200);
	
	System.out.println();
			
	}
	
	@Test
	public void getDummyUserMockAPIWithJsonFile() {
		APIMocks.defineGetUserMockJsonFile();
		
	Response response=restClient.get(BASE_URL_MOCK_SERVER, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
	response.then().assertThat().statusCode(200);
	
	System.out.println();
			
	}
	
	@Test
	public void getDummyUserMockAPIWithQueryParam() {
		APIMocks.defineGetUserMockQueryParam();
		
		Map<String, String> userQueryMap=new HashMap<>();
		userQueryMap.put("username", "janedoe89");
		
		
	Response response=restClient.get(BASE_URL_MOCK_SERVER, "/api/users", userQueryMap, null, AuthType.NO_AUTH, ContentType.ANY);
	response.then().assertThat().statusCode(200);
	
	System.out.println();
			
	}
}
