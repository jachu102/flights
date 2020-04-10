package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="flight")
public class FlightsController {
	
	@Autowired
	ArrivalsService arrivalsService;
	
	@PostMapping(path="/add")
	public ResponseEntity<Object> add(@Validated @RequestBody Pigeon arrivedPigeon) {
		Pigeon savedPigeon =  arrivalsService.add(arrivedPigeon);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		        		.buildAndExpand(savedPigeon.getId()).toUri();
		return ResponseEntity.created(location).body(savedPigeon);
	}
	
	@GetMapping(path="/getAll")
	public List<Pigeon> getAll() {
		return arrivalsService.getAll();
	}
	
	@GetMapping(path="get/{id}")
	public Optional<Pigeon> get(@PathVariable(name="id") Long id) {
		return arrivalsService.get(id);
	}
	
	@GetMapping(path="findByName")
	public List<Pigeon> findByName(@RequestParam(name="name") String name) {
		return arrivalsService.findByName(name);
	}
	
	@DeleteMapping(path="remove/{id}")
	public ResponseEntity<Object> remove(@PathVariable(name="id") Long id) {
		arrivalsService.remove(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping(path="update/{id}")
	public ResponseEntity<Long> update(@PathVariable(name="id") Long id, @Validated @RequestBody Pigeon updatedData) {
		ensureIdIntegrity(id, updatedData);
		arrivalsService.update(updatedData);
		return ResponseEntity.ok(id);
	}

	private void ensureIdIntegrity(@PathVariable(name = "id") Long id, @RequestBody @Validated Pigeon updatedData) {
		if (updatedData.getId()==null) {
			updatedData.setId(id);
		} else if ( ! id.equals(updatedData.getId())) {
			throw new DataIntegrityViolationException("URI id and id not match.");
		}
	}
}
