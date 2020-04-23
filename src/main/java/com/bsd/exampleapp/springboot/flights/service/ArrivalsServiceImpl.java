package com.bsd.exampleapp.springboot.flights.service;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("arrivalsService")
public class ArrivalsServiceImpl implements ArrivalsService {

	@Autowired
	FlightRepository flightRepository;

	@Override
	public Pigeon add(Pigeon arrivedPigeon) {
		return flightRepository.save(arrivedPigeon);
	}
	
	@Override
	public void update(Pigeon arrivedPigeon) {
		if (!flightRepository.existsById(arrivedPigeon.getId())) {
			String message = String.format("No class %s entity with id %s exists!", Pigeon.class.getName(), arrivedPigeon.getId());
			throw new IllegalArgumentException(message);
		}
		flightRepository.save(arrivedPigeon);
	}

	@Override
	public void remove(Long pigeonId) {
		flightRepository.deleteById(pigeonId);
	}

	@Override
	public Optional<Pigeon> get(Long id) {
		return flightRepository.findById(id);
	}

	@Override
	public List<Pigeon> getAll() {
		return flightRepository.findAll(Sort.by(Direction.DESC, "name"));
	}

	@Override
	public List<Pigeon> findByName(String name) {
		return flightRepository.findByName(name);
	}
}
