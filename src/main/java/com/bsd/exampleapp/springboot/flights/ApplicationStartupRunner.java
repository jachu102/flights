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

    private void storeInitData() {
        Owner admin = ownerRepository.save(Owner.builder().id(1L).name("admin").build());
        flightRepository.save(Pigeon.builder().name("pigeon 2").owner(admin).build());
        flightRepository.save(Pigeon.builder().name("pigeon 1").owner(admin).build());
        flightRepository.save(Pigeon.builder().name("pigeon 3").owner(admin).build());
    }
}
