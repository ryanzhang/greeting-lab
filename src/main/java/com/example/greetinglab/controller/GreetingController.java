package com.example.greetinglab.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	@GetMapping("/api/greeting")
	public HashMap<String, String> greeting() {
		return new HashMap<String, String>() {
			{
				put("title", "Mr.");
				put("name", "Zhang");
			}
		};
	}

	@GetMapping("/api/chaosmonkey")
	public Object throwError() {
		throw new RuntimeException("No one is perfect!");
	}
}