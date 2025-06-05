package com.microservices.order_service;
import com.microservices.order_service.stubs.InventoryClientStubs;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder(){
		//flow of wiremock and rest assured
//		1. WireMock stub is registered (InventoryClientStubs.stubInventoryCall)
//		2. RestAssured triggers the actual API call
//		3. Order service uses Feign to call Inventory
//		4. WireMock intercepts the Feign call
//		5. WireMock returns the mocked response
//		6. Order flow continues and response is validated

		String request = """
				{
				    "skuCode":"iphone_15",
				    "price":30000,
				    "quantity":5
				}
				""";
		InventoryClientStubs.stubInventoryCall("iphone_15",5);
		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(request)
				.when()
				.post("api/v1/orders")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

				assertThat(responseBodyString, Matchers.is("Order placed successfully"));


	}

}



