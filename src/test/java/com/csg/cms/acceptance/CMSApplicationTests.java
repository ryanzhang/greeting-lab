package com.csg.cms.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@Slf4j
class CMSApplicationTests {

	@LocalServerPort
	int port;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("访问/api/greeting 可以返回200")
	void greetingIsUp() {
		given()
			.port(port)
		.when()
			.get("/api/greeting")
		.then()
			.statusCode(200)
			//使用"$", 返回对象
			.body("name", containsString("Zhang"));
	}

	@Test
	@DisplayName("访问/api/people, 可以保存数据到数据库")
	void saveDatatoDB(){
		given().contentType(ContentType.JSON).body("{\"title\": \"Mr.\",\"name\": \"Ryan\"}").port(port)
		.when()
			.post("/api/people")
		.then()
			.statusCode(200)
			.body("name", equalTo("Ryan"))
			// .body("id", equalTo(1));
			.body("id", greaterThanOrEqualTo(1));
	}


	@Test
	@DisplayName("访问/api/people,可以从数据库获取数据")
	void fetchDataFromDB(){
		given().contentType(ContentType.JSON).body("{\"title\": \"Mr.\",\"name\": \"Ryan\"}").port(port)
			.when()
				.post("/api/people")
			.then()
				.statusCode(200);
		given().port(port)
			.when()
				.get("/api/people")
			.then()	
				.statusCode(200)
				.body("name", hasItem("Ryan"))
				.body("title", hasItem("Mr."));
	}

	@Value("${logging.file}")
	String log_file_path;
	@Test
	@DisplayName("可以保存并且能够输出应用日志到文件")
	void applicationLogTraceable() throws IOException{
		String mark =  "red to read, green to great, perfectly safe!";
		log.info(mark);
		assertTrue(
			new String(Files.readAllBytes(Paths.get(log_file_path)), 
				Charset.defaultCharset()).contains(mark), "检测日志文件包含指定字符串失败!"
		);
	}

	@Test
	@DisplayName("访问/health 可以返回200")
	void healthCheckOK(){
		given().port(port)
			.when()
				.get("/health")
			.then()
			.statusCode(200);
	}	

}
