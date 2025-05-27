package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactCredentails;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetContactsTest extends BaseTest {
	
	private String tokenId;
	
	@BeforeMethod
	public void getToken() {
		
	ContactCredentails creds=ContactCredentails.builder()
			.email("nilesh@example.com")
			.password("Test@123")
			.build();
	
	Response response=restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, creds, null, null, AuthType.NO_AUTH, ContentType.JSON);
	Assert.assertEquals(response.getStatusCode(), 200);
	
	tokenId = response.jsonPath().getString("token");
	System.out.println("Contacts Login JWT Token ====> " + tokenId);
	
	ConfigManager.set("BearerToken", tokenId);
	}
	
	
	@Test
	public void getAllContactsAPITest() {
		
	Response response=restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	Assert.assertEquals(response.statusCode(), 200);
	
	}

}
