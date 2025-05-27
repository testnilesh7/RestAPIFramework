package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class UpdateUserTest extends BaseTest {

private String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId="ac9ff38c212b7a3cdbcb99812b53375e9795b0f76238b89f4284c742556233f8";
		ConfigManager.set("BearerToken", tokenId);
	}

    @Test
    public void updateUserTest() {

        // 1. Create a new user - Post
        // User user = new User("Nilesh",StringUtils.getRandomEmailId() , "male", "active");

       User user= User.builder()
                .name("Nilesh")
                .email(StringUtils.getRandomEmailId())
                .status("active")
                .gender("male")
                .build();

       	Response response= restClient.post(BASE_URL_GOREST,GOREST_USERS_ENDPOINT,user,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertEquals(response.jsonPath().getString("name"), "Nilesh");
        Assert.assertNotNull(response.jsonPath().getString("id"));

        // fetch the user id
        String userId = response.jsonPath().getString("id");
        System.out.println("User ID=====> : " + userId);
        
        System.out.println("============= POST PASSED==================");

        System.out.println("----------------------------------------------------------------------------");


        //2. Get the user - Get
        Response getUserResponse = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
                        AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertTrue(getUserResponse.statusLine().contains("OK"));
        Assert.assertEquals(getUserResponse.jsonPath().getString("id"), userId);
        
        System.out.println("============= GET PASSED==================");

        System.out.println("----------------------------------------------------------------------------");

     //   3. Update the user - Put

        user.setName("Nilesh18 Automation");
        user.setStatus("inactive");

        Response responsePUT= restClient.put(BASE_URL_GOREST,GOREST_USERS_ENDPOINT + "/" + userId, user,null,null, AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertTrue(responsePUT.statusLine().contains("OK"));
        Assert.assertEquals(responsePUT.jsonPath().getString("id"),userId);
        Assert.assertEquals(responsePUT.jsonPath().getString("name"),"Nilesh18 Automation");
        Assert.assertEquals(responsePUT.jsonPath().getString("status"),"inactive");

        System.out.println("============= PUT PASSED==================");

        System.out.println("----------------------------------------------------------------------------");
        
        
        // 4. Get the user - Get - fetch the same userid
         getUserResponse = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
                AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertTrue(getUserResponse.statusLine().contains("OK"));
        Assert.assertEquals(getUserResponse.jsonPath().getString("id"), userId);
        Assert.assertEquals(getUserResponse.jsonPath().getString("name"),"Nilesh18 Automation");
        Assert.assertEquals(getUserResponse.jsonPath().getString("status"),"inactive");

        System.out.println("============= GET PASSED==================");

    }

}
