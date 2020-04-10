package com.bsd.exampleapp.springboot.flights.controller;

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
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Pigeon add(@Validated @RequestBody Pigeon arrivedPigeon) {
		return arrivalsService.add(arrivedPigeon);
	}
	
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Pigeon> getAll() {
		return arrivalsService.getAll();
	}
	
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Optional<Pigeon> get(@PathVariable(name="id") Long id) {
		return arrivalsService.get(id);
	}
	
	@RequestMapping(value = "findByName", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Pigeon> findByName(@RequestParam(name="name") String name) {
		return arrivalsService.findByName(name);
	}
	
	@RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void remove(@PathVariable(name="id") Long id) {
		arrivalsService.remove(id);
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public Long update(@PathVariable(name="id") Long id, @Validated @RequestBody Pigeon updatedData) {
		ensureIdIntegrity(id, updatedData);
		arrivalsService.update(updatedData);
		return id;
	}

	private void ensureIdIntegrity(@PathVariable(name = "id") Long id, @RequestBody @Validated Pigeon updatedData) {
		if (updatedData.getId()==null) {
			updatedData.setId(id);
		} else if ( ! id.equals(updatedData.getId())) {
			throw new DataIntegrityViolationException("URI id and id not match.");
		}
	}
}
