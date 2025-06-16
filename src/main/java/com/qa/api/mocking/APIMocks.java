package com.qa.api.mocking;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;


public class APIMocks {

	//*************************Get Mock/Stub for GET CALL******************//

	public static void defineGetUserMock() {
		// https://localhost:8089/api/users
		stubFor(get(urlEqualTo("/api/users"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withHeader("server-name", "bankserver")
						.withBody("{\r\n"
								+ "    \"_id\": 1,\r\n"
								+ "    \"name\": \"tom\",\r\n"
								+ "    \"age\": 30,\r\n"
								+ "    \"salary\": 20.5\r\n"
								+ "}")
						)
				);
	}
	
	public static void defineGetUserMockJsonFile() {
		// https://localhost:8089/api/users
		stubFor(get(urlEqualTo("/api/users"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withHeader("server-name", "bankserver")
						.withBodyFile("mockuser.json")
						)
				);
	}
	
	public static void defineGetUserMockQueryParam() {
		// https://localhost:8089/api/users?username=janedoe89
		stubFor(get(urlPathEqualTo("/api/users"))
				.withQueryParam("username", equalTo("janedoe89"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withHeader("server-name", "bankserver")
						.withBodyFile("mockuser.json")
						)
				);
	}


	//*************************Create Mock/Stub for GET CALL******************//

	public static void defineCreateUserMock(){

		stubFor(post(urlEqualTo("/api/users"))
						.withHeader("Content-Type",equalTo("application/json"))
						.willReturn(aResponse()
								.withStatus(201)
								.withHeader("Content-Type", "application/json")
								.withHeader("server-name", "bankserver")
								.withBody("{\r\n"
										+ "    \"_id\": 1,\r\n"
										+ "    \"name\": \"tom\",\r\n"
										+ "    \"age\": 30,\r\n"
										+ "    \"salary\": 20.5\r\n"
										+ "}")
						)
				);
	}

}
