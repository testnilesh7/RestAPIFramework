package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContact {

	
	private String firstName;
	private String lastName;
	private String birthdate;
	private String email;
	private String phone;

}
