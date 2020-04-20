package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.dto.OwnerDto;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "flight/pigeon/owner")
public class OwnersController {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ConversionService conversionService;

    @GetMapping(value = "getAll")
    @ResponseStatus(value = HttpStatus.OK)
    public List<OwnerDto> getAll() {
        return ownerRepository.findAll().stream()
                .map(owner -> conversionService.convert(owner, OwnerDto.class))
                .collect(Collectors.toList());
    }
}
