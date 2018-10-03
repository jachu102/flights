package com.bsd.exampleapp.springboot.flights.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;

@RestController
@RequestMapping(path="flight")
public class FlightsController {
	
	@Autowired
	ArrivalsService arrivals;
	
	@PostMapping(path="/add")
	public ResponseEntity<Object> add(@Valid @RequestBody Pigeon arrivedPigeon) {
		Pigeon savedPigeon =  arrivals.add(arrivedPigeon);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		        		.buildAndExpand(savedPigeon.getId()).toUri();
		
		return ResponseEntity.created(location).body(savedPigeon);
	}
	
	@GetMapping(path="/getAll")
	public List<Pigeon> getAll() {
		
		return arrivals.getAll();
	}
	
	@GetMapping(path="get/{id}")
	public Optional<Pigeon> get(@PathVariable(name="id") Long id) {
		
		return arrivals.get(id);
	}
	
	@GetMapping(path="findByName")
	public List<Pigeon> findByName(@RequestParam(name="name") String name) {
		
		return arrivals.findByName(name);
	}
	
	@DeleteMapping(path="remove/{id}")
	public HttpStatus remove(@PathVariable(name="id") Long id) {
		try {
			arrivals.remove(id);
		} catch(Exception e)
		{
			return HttpStatus.NOT_FOUND;
		}
		
		return HttpStatus.OK;
	}
	
	@PutMapping(path="update/{id}")
	public ResponseEntity<Long> update(@PathVariable(name="id") Long id, @Valid @RequestBody Pigeon updatedData) {
			updatedData.setId(id);
			arrivals.update(updatedData);
			
			return ResponseEntity.ok(id);
	}
}
