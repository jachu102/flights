package com.bsd.exampleapp.springboot.flights.converter;

import com.bsd.exampleapp.springboot.flights.dto.OwnerDto;
import com.bsd.exampleapp.springboot.flights.model.Owner;
import org.springframework.core.convert.converter.Converter;

public class OwnerDtoConverter implements Converter<Owner, OwnerDto> {

    @Override
    public OwnerDto convert(Owner owner) {
        return new OwnerDto().setId(owner.getId()).setName(owner.getName());
    }
}
