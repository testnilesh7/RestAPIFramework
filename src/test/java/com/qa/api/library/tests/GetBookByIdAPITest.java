package com.qa.api.library.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Book;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetBookByIdAPITest extends BaseTest {
	
	String bookID;
	int aisleNumber=StringUtils.generateFourDigitRandomNumber();

	@Test(priority = 1)
	public void createNewBookAPITest() {
		
	Book book=Book.builder().name("Rest Assured")
						.isbn("NB")
						.aisle(aisleNumber)
						.author("NBB")
						.build();
		
		
		Response bookResponse=restClient.post(BASE_URL_LIBRARY, ADD_BOOKS_ENDPOINT, book, null, null, AuthType.NO_AUTH, 
			ContentType.JSON);
	
		String message =bookResponse.jsonPath().getString("Msg");
		bookID=bookResponse.jsonPath().getString("ID");
		
		Assert.assertEquals(bookResponse.getStatusCode(), 200);
		Assert.assertEquals(message, "successfully added");	
		Assert.assertNotNull(bookResponse.jsonPath().getString("ID"));
		
		System.out.println("Print Book ID ====> " + bookID);
		
	}
	
	@Test(priority = 2)
	public void getBookByID() {
		
		Map<String , String> queryParams=new HashMap<String , String>();
		
		queryParams.put("ID", bookID);
		
		Response getResponse=restClient.get(BASE_URL_LIBRARY, GET_BOOK_ENDPOINT, queryParams, null, AuthType.NO_AUTH, ContentType.JSON);
		
		String author=getResponse.jsonPath().getString("author");
						
		Assert.assertEquals(getResponse.statusCode(), 200);
		Assert.assertNotNull(author);
		
		System.out.println("GET BOOK Response :- " + getResponse.getBody().asString());
	}
	

}
