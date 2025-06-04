package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	protected RestClient restClient;
	
	// ********* API BASE URLS********//
	// protected final static String BASE_URL_GOREST ="https://gorest.co.in";
		protected static String BASE_URL_GOREST;
		protected static String BASE_URL_REQRES;
		protected static String BASE_URL_CONTACTS;
		protected  static String BASE_URL_BASIC_AUTH;
		protected static String BASE_URL_BOOKING;
		protected static String BASE_URL_PRODUCTS;
		protected static String BASE_URL_OAUTH2_AMADEUS;
		protected static String BASE_URL_EARGAST_AMADEUS;
		protected static String BASE_URL_LIBRARY;
		

	
	

	
		
	// ********* API ENDPoints********//
	protected final static String GOREST_USERS_ENDPOINT ="/public/v2/users";
	protected final static String CONTACTS_LOGIN_ENDPOINT ="/users/login";
	protected final static String CONTACTS_ENDPOINT ="/contacts";
	protected final static String REQRES_ENDPOINT ="/api/users";
	protected final static String BASIC_AUTH_ENDPOINT ="/basic_auth";
	protected final static String BOOKING_AUTH_ENDPOINT ="/auth";
	protected final static String BOOKING_ENDPOINT ="/booking";
	protected final static String PRODUCTS_ENDPOINT ="/products";
	protected final static String AMADEUS_OAUTH2_TOKEN_ENDPOINT ="/v1/security/oauth2/token";
	protected final static String AMADEUS_FLIGHT_DEST_ENDPOINT ="v1/shopping/flight-destinations";
	protected final static String ERGAST_ENDPOINT ="/api/f1/2017/circuits.xml";
	protected final static String ADD_BOOKS_ENDPOINT="Library/Addbook.php";
	protected final static String GET_BOOK_ENDPOINT="/Library/GetBook.php";


	@BeforeSuite
	public void initSetup() {
		
		RestAssured.filters(new AllureRestAssured());
		
		BASE_URL_GOREST = ConfigManager.get("baseurl.gorest").trim();
		BASE_URL_REQRES = ConfigManager.get("baseurl.reqres").trim();
		BASE_URL_CONTACTS = ConfigManager.get("baseurl.contacts").trim();
		BASE_URL_BASIC_AUTH = ConfigManager.get("baseurl.basicAuth").trim();
		BASE_URL_BOOKING = ConfigManager.get("baseurl.booking").trim();
		BASE_URL_PRODUCTS = ConfigManager.get("baseurl.products").trim();
		BASE_URL_OAUTH2_AMADEUS = ConfigManager.get("baseurl.amadeus").trim();
		BASE_URL_EARGAST_AMADEUS = ConfigManager.get("baseurl.eargast").trim();
		BASE_URL_LIBRARY = ConfigManager.get("baseurl.library").trim();
		
		
		
	}
	
	@BeforeTest
	public void setup() {
		
		restClient=new RestClient();
		
	}

}
