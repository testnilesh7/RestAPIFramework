package com.qa.api.contacts.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactCredentails;
import com.qa.api.pojo.CreateContact;
import com.qa.api.utils.StringUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteContactTest extends BaseTest {

    private String tokenId;
    private String contactId;
    CreateContact createContact;

    @BeforeMethod
    public void getToken() {
        // Login to get the token
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
        // POST
        createContact= CreateContact.builder().firstName("Delete").lastName("User")
                .birthdate("1990-01-01").email(StringUtils.getRandomEmailId())
                .phone(StringUtils.genrateRandomPhoneNumber()).build();

        Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, createContact, null, null,
                AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertEquals(response.getStatusCode(), 201);
        contactId = response.jsonPath().getString("_id");
        System.out.println("Print the Contacts ID ====> " + contactId);

    }

    @Test(priority = 2)
    public void deleteContactAPITest() {
        // DELETE
        Response response = restClient.delete(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
                AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("Contact deleted"));
        System.out.println("Contact Deleted Successfully");
    }

    @Test(priority = 3)
    public void getDeletedContactAPITest() {
        // GET
        Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactId, null, null,
                AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertTrue(response.getStatusLine().contains("Not Found"));
        System.out.println("Contact Not Found");
    }

}
