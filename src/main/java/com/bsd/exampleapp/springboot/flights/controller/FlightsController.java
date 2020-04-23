package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.dto.PigeonDto;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="flight")
public class FlightsController {
	
	@Autowired
	ArrivalsService arrivalsService;

	@Autowired
	ConversionService conversionService;

	@PostMapping(value = "add")
	@ResponseStatus(HttpStatus.CREATED)
	public PigeonDto add(@Validated @RequestBody PigeonDto pigeonDto) {
		Pigeon pigeon = conversionService.convert(pigeonDto, Pigeon.class);
		pigeon = arrivalsService.add(pigeon);
		return conversionService.convert(pigeon, PigeonDto.class);
	}

	@GetMapping(value = "getAll")
	@ResponseStatus(HttpStatus.OK)
	public List<PigeonDto> getAll() {
		return arrivalsService.getAll().stream()
				.map(pigeon -> conversionService.convert(pigeon, PigeonDto.class))
				.collect(Collectors.toList());
	}

	@GetMapping(value = "get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PigeonDto get(@PathVariable(name = "id") Long id) {
		return conversionService.convert(arrivalsService.get(id).get(), PigeonDto.class);
	}

	@GetMapping(value = "findByName")
	@ResponseStatus(HttpStatus.OK)
	public List<PigeonDto> findByName(@RequestParam(name = "name") String name) {
		return arrivalsService.findByName(name).stream()
				.map(pigeon -> conversionService.convert(pigeon, PigeonDto.class))
				.collect(Collectors.toList());
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
		Pigeon convertedPigeon = conversionService.convert(updatedData, Pigeon.class);
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
