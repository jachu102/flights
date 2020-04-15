package com.bsd.exampleapp.springboot.flights;

import com.bsd.exampleapp.springboot.flights.model.Owner;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.FlightRepository;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    FlightRepository flightRepository;

    @Override
    public void run(String... args) {
        storeInitData();
    }

    //TODO run for dev profile only
    private void storeInitData() {
        Owner admin = ownerRepository.save(new Owner(1L, "admin"));
        flightRepository.save( new Pigeon("pigeon 2", admin));
        flightRepository.save( new Pigeon("pigeon 1", admin));
        flightRepository.save( new Pigeon("pigeon 3", admin));
    }
}
