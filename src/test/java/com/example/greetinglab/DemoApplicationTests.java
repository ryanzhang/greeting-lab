package com.example.greetinglab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@LocalServerPort
	int port;
	@Test
	void contextLoads() {
	}
	
	@Test
	@DisplayName("访问/greeting 可以返回200")
	void greetingIsUp(){
		given().port(port)
			.when()
				.get("/greeting")
			.then()
			.statusCode(200);
			// .body("$", containsString("Zhang"));
	}


}
