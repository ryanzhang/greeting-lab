package com.example.greetinglab.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
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
	
	@PostMapping(value="/fileupload")
	public void postMethodName(@RequestParam Long projectId, @RequestParam("file") MultipartFile multipartFile) {
		log.info(multipartFile.getOriginalFilename());
		log.info(projectId.toString());
		System.out.println(multipartFile.getOriginalFilename());
	}
}