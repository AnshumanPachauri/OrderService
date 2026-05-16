package com.ap.microservices.order;

import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;
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
			.when().post("/api/order").then().statusCode(201).log().all().extract().body().asString();
			
			assertThat(requestBody, Matchers.is("Order Placed Successfully"));
	}

}
