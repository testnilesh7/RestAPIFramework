package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITest extends BaseTest {

	// https://fakestoreapi.com/products
	
	@Test
	public void getAllProductsTest() {
		
		Response response=restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		
		Product[] product=JsonUtils.deserialize(response, Product[].class);
				
		for (Product p: product) {
			System.out.println("id:" + p.getId());
			System.out.println("title:" + p.getTitle());
			System.out.println("description:" + p.getDescription());
			System.out.println("category:" + p.getCategory());
			System.out.println("image:" + p.getImage());
			
			// this coming from inner pojo class inside created in product
			System.out.println("rate:" + p.getRating().getRate());
			System.out.println("count:" + p.getRating().getCount());


		}
	}
}
