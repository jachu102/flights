package com.bsd.exampleapp.springboot.flights.repository;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Pigeon, Long> {
	
	List<Pigeon> findByName(String name);
}
