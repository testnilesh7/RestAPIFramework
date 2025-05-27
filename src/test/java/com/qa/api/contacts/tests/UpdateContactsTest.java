package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactCredentails;
import com.qa.api.pojo.CreateContact;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateContactsTest extends BaseTest {
	
	private String tokenId;
	private String contactId;
	CreateContact createContact;

	@BeforeMethod
	public void getToken() {

		ContactCredentails creds = ContactCredentails.builder().email("nilesh@example.com").password("Test@123")
				.build();

		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, creds, null, null,
				AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);

		tokenId = response.jsonPath().getString("token");
		System.out.println("Contacts Login JWT Token ====> " + tokenId);

		ConfigManager.set("BearerToken", tokenId);
	}

	@Test(priority = 1)
	public void createContactsAPITest() {

		 createContact= CreateContact.builder().firstName("Nilesh").lastName("Bhujang")
				.birthdate("1990-01-01").email(StringUtils.getRandomEmailId())
				.phone(StringUtils.genrateRandomPhoneNumber()).build();

		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, createContact, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.getStatusCode(), 201);
		contactId = response.jsonPath().getString("_id");
		System.out.println("Print the Contacts ID ====> " + contactId);
		
	}

	@Test(priority = 2)
	public void updateContactAPITest(){

		// PUT
		createContact.setFirstName("N");
		createContact.setLastName("B");
		createContact.setBirthdate("2025-05-05");

		Response updateResponse=restClient.put(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, createContact, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(updateResponse.getStatusCode(), 200);
		Assert.assertEquals(updateResponse.jsonPath().getString("lastName"), "B");

	}

	@Test(priority = 3)
	public void patchContactAPITest() {

		// PATCH
		createContact.setFirstName("Shiv");
		createContact.setLastName("bhujang");

		Response updateResponse=restClient.patch(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, createContact, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(updateResponse.getStatusCode(), 200);
		Assert.assertEquals(updateResponse.jsonPath().getString("lastName"), "bhujang");
	}

	@Test(priority = 4)
	public void getContactAfterPatchAPITest() {

		Response getResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(getResponse.getStatusCode(), 200);
		Assert.assertEquals(getResponse.jsonPath().getString("firstName"), "Shiv");

		Assert.assertEquals(getResponse.jsonPath().getString("lastName"), "bhujang");
		System.out.println("Get Updated Contact Response ====> " + getResponse.asString());

	}
	

}
