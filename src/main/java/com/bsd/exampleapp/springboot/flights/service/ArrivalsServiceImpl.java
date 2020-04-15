package com.bsd.exampleapp.springboot.flights.service;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArrivalsServiceImpl implements ArrivalsService {
	
	@Autowired
	FlightRepository arrivedPigeons;

	@Override
	public Pigeon add(Pigeon arrivedPigeon) {
		return arrivedPigeons.save(arrivedPigeon);
	}
	
	@Override
	public void update(Pigeon arrivedPigeon) {
		if( arrivedPigeons.existsById(arrivedPigeon.getId()) ) {
			arrivedPigeons.save(arrivedPigeon);
			return;
		}
		throw new IllegalArgumentException("Expected id does not exist.");
	}

	@Override
	public void remove(Long pigeonId) {
		arrivedPigeons.deleteById(pigeonId);
	}

	@Override
	public Optional<Pigeon> get(Long id) {
		return arrivedPigeons.findById(id);
	}

	@Override
	public List<Pigeon> getAll() {
		Sort sort = new Sort(Direction.DESC, new String[]{"name"});
		return arrivedPigeons.findAll(sort);
	}

	@Override
	public List<Pigeon> findByName(String name) {
		return arrivedPigeons.findByName(name);
	}
}
