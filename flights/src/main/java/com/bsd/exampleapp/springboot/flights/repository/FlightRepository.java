package com.bsd.exampleapp.springboot.flights.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;

public interface FlightRepository extends JpaRepository<Pigeon, Long> {
	
	public List<Pigeon> findByName(String name);
	
}
