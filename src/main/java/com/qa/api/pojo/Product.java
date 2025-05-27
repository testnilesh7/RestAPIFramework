package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	private Integer id;
	private String Title;
	private Double price;
	private String description;
	private String category;
	private String image;
	
	// below created for access the inside class variables 
	private Rating rating;

			@Builder
			@Data
			@NoArgsConstructor
			@AllArgsConstructor
			public static class Rating{
				
				private Double rate;
				private Integer count;
			}

}
