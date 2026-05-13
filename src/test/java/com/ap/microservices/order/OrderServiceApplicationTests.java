package com.ap.microservices.order;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.mysql.MySQLContainer;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");
	
	@LocalServerPort
	private Integer port;
	
	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	static{
		mySQLContainer.start();
	}
	
	@Test
	void shouldCreateOrder() {

		String requestBody = """
	            {
				    "skuCode":"Iphone 17",
				    "price":"150000",
				    "quantity":1
				}
	            """;
			
			RestAssured.given().contentType("application/json").body(requestBody)
			.when().post("/api/order").then().statusCode(201)
			.body("id", Matchers.notNullValue())
			.body("skuCode", Matchers.equalTo("Iphone 17"))
			.body("quantity", Matchers.equalTo(1))
			.body("price", Matchers.equalTo("150000"));
	}

}
