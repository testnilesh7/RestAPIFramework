package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestUserAPISchemaTest extends BaseTest {
	
	@Test
	public void getUsersAPISchemaTest() {
		ConfigManager.set("BearerToken","ac9ff38c212b7a3cdbcb99812b53375e9795b0f76238b89f4284c742556233f8");

	Response response=restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
	Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/getUsersSchema.json"));
	}
	
	@Test
	public void createUserSchemaTest() {
		ConfigManager.set("BearerToken","ac9ff38c212b7a3cdbcb99812b53375e9795b0f76238b89f4284c742556233f8");

		User user=User.builder()
				.name("API")
				.status("active")
				.gender("male")
				.email(StringUtils.getRandomEmailId())
				.build();
		
		Response response=restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/createUserSchema.json"));
		
						
	}
	
	
}
