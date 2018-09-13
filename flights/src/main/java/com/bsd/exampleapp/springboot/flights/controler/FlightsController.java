package com.bsd.exampleapp.springboot.flights.controler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightsController {
	
	List<String> flights = new ArrayList<String>();
	
	@RequestMapping(path="/add")
	public String add(@RequestParam(value="name") String name) {
		flights.add(name);
		
		return "A new flight has been just added.";
	}

	@GetMapping(path="/getAll")
	public Iterable<String> getAll() {
		
		return flights;
	}
	
}
