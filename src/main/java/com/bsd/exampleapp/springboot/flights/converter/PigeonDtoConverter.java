package com.bsd.exampleapp.springboot.flights.converter;

import com.bsd.exampleapp.springboot.flights.dto.PigeonDto;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class PigeonDtoConverter implements Converter<Pigeon, PigeonDto> {

    @Override
    public PigeonDto convert(Pigeon pigeon) {
        return new PigeonDto()
                .setId(pigeon.getId())
                .setName(pigeon.getName())
                .setOwnerId(pigeon.getOwner().getId());
    }
}
