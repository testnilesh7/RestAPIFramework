package com.qa.api.booking.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.BookingCredentails;
import com.qa.api.pojo.CreateBooking;
import com.qa.api.pojo.CreateBooking.BookingDates;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBookingTest extends BaseTest {

	private String tokenId;
	private String bookingId;
	CreateBooking createBooking;
	
	@BeforeMethod
	public void getToken() {

		BookingCredentails creds = BookingCredentails.builder().username("admin").password("password123").build();

		Response response = restClient.post(BASE_URL_BOOKING, BOOKING_AUTH_ENDPOINT, creds, null, null,
				AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);

		tokenId = response.jsonPath().getString("token");
		System.out.println("Contacts Login Token ====> " + tokenId);

		ConfigManager.set("BearerToken", tokenId);
	}

	@Test(priority = 1)
	public void createBookingAPITest() {

		BookingDates dates = BookingDates.builder().checkin("2025-01-01").checkout("2019-05-05").build();

		 createBooking = CreateBooking.builder().firstname("Nilesh").lastname("Bhuajng").totalprice(500.55)
				.depositpaid(true).bookingdates(dates).additionalneeds("Dinner").build();

		Response respone = restClient.post(BASE_URL_BOOKING, BOOKING_ENDPOINT, createBooking, null, null,
				AuthType.NO_AUTH, ContentType.JSON);

		bookingId = respone.jsonPath().getString("bookingid");
		System.out.println("Print the New Created Booking ID : " + bookingId);
		Assert.assertEquals(respone.statusCode(), 200);

	}
	
	@Test(priority = 2)
	public void updateBookingAPITest() {
		
		createBooking.setFirstname("NNN");
		createBooking.setLastname("BBB");
		
	Response response=restClient.put(BASE_URL_BOOKING, BOOKING_ENDPOINT +"/"+bookingId, createBooking, null, null, AuthType.COOKIE_TOKEN, ContentType.JSON);
		
	String update=response.getBody().asString();
	System.out.println("Print Update Response : " + update);
	
	}

}
