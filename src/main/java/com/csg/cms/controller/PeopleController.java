package com.csg.cms.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import com.csg.cms.domain.People;
import com.csg.cms.repository.PeopleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PeopleController {
	final PeopleRepository repository;

	@GetMapping("/people")
	public List<People> listPeople() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@PostMapping("/people")
	public People savePeople(@RequestBody People people) {
		repository.save(people);
		return people;

	}
}