package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.service.PigeonConverter;
import com.bsd.exampleapp.springboot.flights.dto.PigeonDto;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="flight")
public class FlightsController {
	
	@Autowired
	ArrivalsService arrivalsService;

	@Autowired
	PigeonConverter pigeonConverter;

	@PostMapping(value = "add")
	@ResponseStatus(HttpStatus.CREATED)
	public Pigeon add(@Validated @RequestBody PigeonDto pigeonDto) {
		Pigeon arrivedPigeon = pigeonConverter.convert(pigeonDto);
		return arrivalsService.add(arrivedPigeon);
	}
	
	@GetMapping(value = "getAll")
	@ResponseStatus(HttpStatus.OK)
	public List<Pigeon> getAll() {
		return arrivalsService.getAll();
	}
	
	@GetMapping(value = "get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<Pigeon> get(@PathVariable(name="id") Long id) {
		return arrivalsService.get(id);
	}
	
	@GetMapping(value = "findByName")
	@ResponseStatus(HttpStatus.OK)
	public List<Pigeon> findByName(@RequestParam(name="name") String name) {
		return arrivalsService.findByName(name);
	}
	
	@DeleteMapping(value = "remove/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void remove(@PathVariable(name="id") Long id) {
		arrivalsService.remove(id);
	}
	
	@PutMapping(value = "update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Long update(@PathVariable(name="id") Long id, @Validated @RequestBody PigeonDto updatedData) {
		ensureIdIntegrity(id, updatedData);
		Pigeon convertedPigeon = pigeonConverter.convert(updatedData);
		arrivalsService.update(convertedPigeon);
		return id;
	}

	private void ensureIdIntegrity(Long id, PigeonDto updatedData) {
		if (updatedData.getId()==null) {
			updatedData.setId(id);
		} else if ( ! id.equals(updatedData.getId())) {
			throw new DataIntegrityViolationException("URI id and id not match.");
		}
	}
}
