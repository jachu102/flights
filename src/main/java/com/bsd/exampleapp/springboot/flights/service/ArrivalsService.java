package com.bsd.exampleapp.springboot.flights.service;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;

import java.util.List;
import java.util.Optional;

public interface ArrivalsService {
	
	Pigeon add(Pigeon arrivedPigeon);
	
	void update(Pigeon updatedPigeon);
	
	void remove(Long pigeonId);
	
	Optional<Pigeon> get(Long id);
	
	List<Pigeon> getAll();
	
	List<Pigeon> findByName(String name);

}
