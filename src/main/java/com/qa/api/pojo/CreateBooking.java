package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBooking {

	private String firstname;
	private String lastname;
	private Number totalprice;
	private Boolean depositpaid;
	private String additionalneeds;
	public BookingDates bookingdates;
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BookingDates{
		
		private String checkin;
		private String checkout;

	}

}
