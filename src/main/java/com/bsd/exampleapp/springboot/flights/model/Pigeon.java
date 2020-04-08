package com.bsd.exampleapp.springboot.flights.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Pigeon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pigeon_generator")
	@SequenceGenerator(name="pigeon_generator", sequenceName = "pigeon_seq")
	private Long id;
	
	@Column
	@NotBlank
	@Size(min=3, max=30)
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
