package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.model.Owner;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "flight/pigeon/owner")
public class OwnersController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping(value = "getAll")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }
}
