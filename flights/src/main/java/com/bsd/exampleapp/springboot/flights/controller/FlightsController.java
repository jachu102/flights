package com.bsd.exampleapp.springboot.flights.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="flight")
public class FlightsController {
	
	String[] defaultValues = {"gołąb 1", "gołąb 2", "gołąb 3"};
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
			return HttpStatus.NOT_FOUND.toString();
		}
		
		return flights.get(index);
	}
	
	@DeleteMapping(path="remove/{index}")
	public HttpStatus remove(@PathVariable(name="index") Integer index) {
		try {
			flights.remove((int) index);
		} catch(Exception e)
		{
			return HttpStatus.NOT_FOUND;
		}
		
		return HttpStatus.OK;
	}
	
}
