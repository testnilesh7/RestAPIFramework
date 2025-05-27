package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUserWithDeserilizationTest extends BaseTest {

private String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId="ac9ff38c212b7a3cdbcb99812b53375e9795b0f76238b89f4284c742556233f8";
		ConfigManager.set("BearerToken", tokenId);
	}

	@Test
	public void createAUserTest() {

		User user = new User(null,"Nilesh",StringUtils.getRandomEmailId() , "male", "active");

		restClient = new RestClient();

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.jsonPath().getString("name"), "Nilesh");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		String userID=response.jsonPath().getString("id");
		
		// GET
		
		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userID, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		
		User userResponse=JsonUtils.deserialize(responseGet, User.class);
		
		Assert.assertEquals(userResponse.getName(), user.getName());
	}
	
	
}
