package com.bsd.exampleapp.springboot.flights.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightsController {
	
	String[] defaultValues = {"flight-1", "flight-2", "flight-3"};
	List<String> flights = new ArrayList<String>(Arrays.asList(defaultValues));
	
	@RequestMapping(path="/add")
	public String add(@RequestParam(value="name") String name) {
		flights.add(name);
		
		return "A new flight has been just added.";
	}

	@GetMapping(path="/getAll")
	public Iterable<String> getAll() {
		
		return flights;
	}
	
	@GetMapping(path="find/{index}")
	public String find(@PathVariable(name="index") Integer index) {
		if(--index<0 | index>=flights.size()) {
			return "Requested flight not exists.";
		}
		
		return flights.get(index);
	}
}
