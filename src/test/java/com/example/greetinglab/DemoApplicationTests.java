package com.example.greetinglab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class DemoApplicationTests {

	@LocalServerPort
	int port;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("访问/api/greeting 可以返回200")
	void greetingIsUp() {
		given().port(port).when().get("/api/greeting").then().statusCode(200);
		// .body("$", containsString("Zhang"));
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
			//judge if id is a number
			//Todo fix this, because id could be any number
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

	@Test
	@DisplayName("输入错误调用，返回结构化错误")
	void returnStructureErrorCode1WhenRequestInWrongWay(){
		//输入非json数据
		given().contentType(ContentType.JSON).body("Ryan").port(port)
			.when()
				.post("/api/people")
			.then()
				.statusCode(400)
				.body("status", equalTo("BAD_REQUEST"))
				.body("message", equalTo("Json格式不正确!"));
	}
	@Test
	@DisplayName("当后端程序抛出异常，返回结构化错误")
	void returnStructureErrorCode1WhenThrowException(){
		
		given().contentType(ContentType.JSON).port(port)
			.when()
				.get("/api/chaosmonkey")
			.then()
				.statusCode(500)
				.body("status", equalTo("INTERNAL_SERVER_ERROR"))
				.body("message", equalTo("No one is perfect!"));
	}

	@Test
	@DisplayName("输入错误url，返回结构化错误")
	void returnStructureError2WhenRequestInWrongWay(){
		given().port(port)
			.when()
				.get("/api/wrongurl")
			.then()
				.statusCode(404)
				.body("status", equalTo("NOT_FOUND"))
				.body("message", equalTo("您访问的页面不存在!"));
	}

	@Value("${logging.file}")
	String log_file_path;
	@Test
	@DisplayName("可以保存并且能够输出应用日志到文件")
	void applicationLogTraceable() throws IOException{
		String mark =  "red to read, green to great, perfectly safe!";
		log.info("");
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
