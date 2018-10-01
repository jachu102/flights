package com.bsd.exampleapp.springboot.flights.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

@Entity
public class Pigeon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pigeon_generator")
	@SequenceGenerator(name="pigeon_generator", sequenceName = "pigeon_seq")
	private Long id;
	
	@Column
	@NotBlank
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
