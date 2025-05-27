package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPath extends BaseTest {
	
	@Test
	public void getProductsTest() {
		
		Response response=restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		
				
		Double minPrice=JsonPathValidatorUtils.read(response, "min($[*].price)");
		System.out.println("Min Price : "+minPrice);
		
		List<Number> prices = JsonPathValidatorUtils.readList(response, "$[?(@.price > 50)].price");	
		System.out.println(prices);
		
		List<Number> ids = JsonPathValidatorUtils.readList(response, "$[?(@.price > 50)].id");	
		System.out.println(ids);
		
		List<Number> rates = JsonPathValidatorUtils.readList(response, "$[?(@.price > 50)].rating.rate");	
		System.out.println(rates);

		List<Integer> countList = JsonPathValidatorUtils.readList(response, "$[?(@.price > 50)].rating.count");	
		System.out.println(countList);

		List<Map<String, Object>> idTitleList=JsonPathValidatorUtils.readListOfMaps(response, "$.[*].['id','title']");
		System.out.println(idTitleList);
	
		List<Map<String, Object>> idTitleCategoryList=JsonPathValidatorUtils.readListOfMaps(response, "$.[*].['id','title','category']");
		System.out.println(idTitleCategoryList);

		List<Map<String, Object>> jewlIdTitle=JsonPathValidatorUtils.readListOfMaps(response, "$.[?(@.category == 'jewelery' )].['id','title']");
		System.out.println(jewlIdTitle);
		
		
	
	}

}
