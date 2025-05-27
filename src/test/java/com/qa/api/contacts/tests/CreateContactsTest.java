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

public class CreateContactsTest extends BaseTest {

	private String tokenId;
	private String contactId;

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

	@Test
	public void createContactsAPITest() {

		CreateContact createContact = CreateContact.builder().firstName("Nilesh").lastName("Bhujang")
				.birthdate("1990-01-01").email(StringUtils.getRandomEmailId())
				.phone(StringUtils.genrateRandomPhoneNumber()).build();

		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, createContact, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON);

		Assert.assertEquals(response.getStatusCode(), 201);
		contactId = response.jsonPath().getString("_id");
		System.out.println("Print the Contacts ID ====> " + contactId);
	}

	@Test
	public void getContactsByIDAPITest() {

		Response getResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
				AuthType.BEARER_TOKEN, ContentType.ANY);
		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertEquals(getResponse.jsonPath().getString("firstName"), "Nilesh");
		// Fetching new created contact id
		Assert.assertEquals(getResponse.jsonPath().getString("_id"), contactId);

	}
	
	@Test
	public void getAllContactsAPITest() {
		
		Response allContactResponse = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null,
				AuthType.BEARER_TOKEN, ContentType.ANY);
		Assert.assertEquals(allContactResponse.statusCode(), 200);
		

	}

}
