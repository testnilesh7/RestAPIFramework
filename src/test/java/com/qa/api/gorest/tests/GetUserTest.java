package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest {

	private String tokenId;

	@BeforeClass
	public void setUpToken() {
		tokenId = "ac9ff38c212b7a3cdbcb99812b53375e9795b0f76238b89f4284c742556233f8";
		ConfigManager.set("BearerToken", tokenId);
	}

	@Story("Get All The User Go Rest API")
	@Description("This test verify returns All valid users data")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Get All User API")
	public void getAllUsersTest() {

		ChainTestListener.log("Get All User Test");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}

	

	@Story("Get All The User Go Rest API")
	@Description("This test verify returns All valid users data with QueryParams")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Get All User API With QueryParams")
	public void getAllUsersWithQueryParamsTest() {
		ChainTestListener.log("Get All User with QueryParams Test");

		Map<String, String> queryParms = new HashMap<String, String>();

		queryParms.put("name", "nilesh");
		queryParms.put("statuc", "active");

		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, queryParms, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}

	@Test(enabled = false)
	public void getSingleUserTest() {
		String userID = "7879589";

		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userID, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getString("id"), userID);

	}

}
