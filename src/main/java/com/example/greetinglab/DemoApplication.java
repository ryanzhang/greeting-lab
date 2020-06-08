package com.example.greetinglab;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
@RestController
class HelloCSG {
	@GetMapping("/greeting")
	public HashMap<String, String> greeting(){
		return new HashMap<String, String>(){{
			put("title", "Mr.");
			put("lastname", "Zhang");
		}};
	}
}
