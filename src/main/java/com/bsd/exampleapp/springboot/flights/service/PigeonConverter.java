package com.bsd.exampleapp.springboot.flights.service;

import com.bsd.exampleapp.springboot.flights.dto.PigeonDto;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PigeonConverter implements Converter<PigeonDto, Pigeon> {

    private OwnerRepository ownerRepository;

    @Autowired
    public PigeonConverter(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Pigeon convert(PigeonDto pigeonDto) {
        return new Pigeon(pigeonDto.getId(),
                pigeonDto.getName(),
                ownerRepository.findById(pigeonDto.getOwnerId()).get());
    }
}
