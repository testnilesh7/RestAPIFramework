package com.qa.api.gorest.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtils;
import com.qa.api.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {
	
	private String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId="ac9ff38c212b7a3cdbcb99812b53375e9795b0f76238b89f4284c742556233f8";
		ConfigManager.set("BearerToken", tokenId);
	}

	@DataProvider
	public Object[][] getUserExcelData() {
	return ExcelUtils.readData(AppConstants.CREATE_USER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getUserExcelData",description = "Create A New User With Excel Data Provider")
	@Story("Create A New User Go Rest API - Via Excel Data Provider")
	@Description("This test will create a new valid user")
	@Severity(SeverityLevel.CRITICAL)
	public void createNewUserWithExcelDataProviderTest(String name,String gender,String status) {

		User user = new User(null,name,StringUtils.getRandomEmailId() , gender, status);

		restClient = new RestClient();

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.jsonPath().getString("name"), name);
		Assert.assertEquals(response.jsonPath().getString("gender"), gender);
		Assert.assertEquals(response.jsonPath().getString("status"), status);

		Assert.assertNotNull(response.jsonPath().getString("id"));

	}

	
	@DataProvider
	public Object[][] getUserData() {
		
		return new Object[][] {
			{"Nilesh","male","active"},
			{"Shiv","male","active"},
			{"Atharva","male","active"}

		};
	}
	
	@Test(dataProvider = "getUserData",description = "Create User GoRest User API With TestNG Data Provider")
	@Story("Create A New User Go Rest API - TestNG Provider")
	@Description("This test will create a new valid user")
	@Severity(SeverityLevel.CRITICAL)
	public void createAUserWithTestNGDataproviderTest(String name,String gender,String status) {

		User user = new User(null,name,StringUtils.getRandomEmailId() , gender, status);

		restClient = new RestClient();

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.jsonPath().getString("name"), name);
		Assert.assertEquals(response.jsonPath().getString("gender"), gender);
		Assert.assertEquals(response.jsonPath().getString("status"), status);

		Assert.assertNotNull(response.jsonPath().getString("id"));

	}

	@Test(enabled = false)
	public void createAUserWithJsonStringTest() {

		String userJson="{\r\n"
		+ "    \"name\": \"Nilesh1777\",\r\n"
		+ "    \"email\": \""+StringUtils.getRandomEmailId()+"\",\r\n"
		+ "    \"gender\": \"male\",\r\n"
		+ "    \"status\": \"active\"\r\n"
		+ "}";
		
		restClient = new RestClient();

		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.jsonPath().getString("name"), "Nilesh1777");
		Assert.assertNotNull(response.jsonPath().getString("id"));

	}
	
	@Test
	public void createAUserWithJsonFileTest() {

	//	File userFile=new File("./src/test/resources/jsons/user.json");
		String emailId=StringUtils.getRandomEmailId();
        try {
            String rawJson=	new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
			String updatedJson=rawJson.replace("{{email}}",emailId);

			Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, updatedJson, null, null,
					AuthType.BEARER_TOKEN, ContentType.JSON);

			Assert.assertEquals(response.jsonPath().getString("name"), "Atharva");
			Assert.assertNotNull(response.jsonPath().getString("id"));
			String id=response.jsonPath().getString("id");
			ChainTestListener.log("Print New Created User ID : "+id);
			
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
        restClient = new RestClient();
		
	}

}
