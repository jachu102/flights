package com.bsd.exampleapp.springboot.flights.service;

import java.util.List;
import java.util.Optional;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;

public interface ArrivalsService {
	
	public Pigeon add(Pigeon arrivedPigeon);
	
	public void remove(Long pigeonId);
	
	public Optional<Pigeon> get(Long id);
	
	public List<Pigeon> getAll();
	
	public List<Pigeon> findByName(String name);

}
