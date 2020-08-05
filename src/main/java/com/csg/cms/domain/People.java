package com.csg.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class People {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	private String title;
	private String name;
}