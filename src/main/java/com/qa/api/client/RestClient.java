package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import static io.restassured.RestAssured.expect;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.*;
import static org.hamcrest.Matchers.anyOf;

public class RestClient {

	// Define all the Response specs:
	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);

	private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(Matchers.equalTo(200), Matchers.equalTo(201)));
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(Matchers.equalTo(200), Matchers.equalTo(404)));
	private ResponseSpecification responseSpec200or204 = expect().statusCode(anyOf(Matchers.equalTo(200), Matchers.equalTo(204)));

	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {

		ChainTestListener.log("API Base URL : "+ baseUrl);
		ChainTestListener.log("Auth Type : "+ authType.toString());

		RequestSpecification request = RestAssured.given()
				.log().all()
				.baseUri(baseUrl).
				contentType(contentType);
		
			//	.accept(contentType);

		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("BearerToken"));
			break;
			
		case COOKIE_TOKEN:
			request.header("Cookie",ConfigManager.get("BearerToken"));
			break;

		case BASIC_AUTH:
			request.header("Authorization", "Basic " + genrateBasicAuth());
			break;

		case API_KEY:
			request.header("x-api-key ", "api key");
			break;

		case NO_AUTH:
			System.out.println("Auth not required");
			break;

		default:
			throw new APIException("======Invalid Auth=======");
		}

		return request;
	}
	
	
	private String genrateBasicAuth() {
	String credentials=ConfigManager.get("basicauhtusername") +  ":" + ConfigManager.get("basicauthpassword");
	// admin:admin ---> "YWRtaW46YWRtaW4="  (nase64 encoded value)
	
		return Base64.getEncoder().encodeToString(credentials.getBytes());
	}

	private void applyParams(RequestSpecification request, Map<String, String> queryParams,
			Map<String, String> pathParams) {

		if (queryParams != null) {
			request.queryParams(queryParams);
		}
		if (pathParams != null) {
			request.pathParams(pathParams);
		}
	}

	// CRUD
	// GET

	/**
	 * @param baseURl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	
	public Response get(String baseURl, String endPoint, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseURl, authType, contentType);
		applyParams(request, queryParams, pathParams);

		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();

		response.prettyPrint();

		return response;
	}

	// POST

	/**
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T> Response post(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();

		return response;

	}

	/**
	 * @param baseUrl
	 * @param endPoint
	 * @param file
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	// this post() will contains file as argument
	public Response post(String baseUrl, String endPoint, File file, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();

		return response;
	}
	
	
	public Response post(String baseUrl, String endPoint, String clientId, String clientSecret ,String grantType, ContentType contentType) {

		Response response= RestAssured.given()
					.contentType(contentType)
					.formParam("grant_type", grantType)
					.formParam("client_id", clientId)
					.formParam("client_secret", clientSecret)
					.when()
					.post(baseUrl+endPoint);
		
		response.prettyPrint();
		return response;
	}
	

	// PUT -update

	public <T>Response put(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
							 Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();

		return response;

	}

	// Patch
	public <T>Response patch(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
							Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).patch(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();

		return response;

	}

	// delete
	public Response delete(String baseUrl, String endPoint, Map<String, String> queryParams,
							  Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setupRequest(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.delete(endPoint).then().spec(responseSpec200or204).extract().response();
		response.prettyPrint();

		return response;

	}


}
