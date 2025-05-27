package com.qa.api.booking.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.CreateBooking;
import com.qa.api.pojo.CreateBooking.BookingDates;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetBookingTest extends BaseTest {

	private String bookingId;

	@Test(priority = 1)
	public void createBookingAPITest() {

		BookingDates dates = BookingDates.builder().checkin("2025-01-01").checkout("2019-05-05").build();

		CreateBooking createBooking = CreateBooking.builder().firstname("Nilesh").lastname("Bhuajng").totalprice(500.55)
				.depositpaid(true).bookingdates(dates).additionalneeds("Dinner").build();

		Response respone = restClient.post(BASE_URL_BOOKING, BOOKING_ENDPOINT, createBooking, null, null,
				AuthType.NO_AUTH, ContentType.JSON);

		bookingId = respone.jsonPath().getString("bookingid");
		System.out.println("Print the New Created Booking ID : " + bookingId);
		Assert.assertEquals(respone.statusCode(), 200);

	}
	
	@Test(priority = 2)
	public void getBookingByIdTest() {
		
	Response response=restClient.get(BASE_URL_BOOKING, BOOKING_ENDPOINT+"/"+bookingId, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
	String booking=response.getBody().asString();
	System.out.println("Fetching Newly Created Booking Id : " + booking);
	}
	
	@Test(priority = 3)
	public void getAllBookingTest() {
		
	Response allResponse=restClient.get(BASE_URL_BOOKING, BOOKING_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(allResponse.getStatusCode(), 200);
	String booking=allResponse.getBody().asString();
	System.out.println("Fetching Newly Created Booking Id : " + booking);
	}

}
