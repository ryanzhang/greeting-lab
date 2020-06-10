package com.example.greetinglab.repository;

import com.example.greetinglab.domain.People;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends CrudRepository<People, Long> {
}