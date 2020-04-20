package com.bsd.exampleapp.springboot.flights.converter;

import com.bsd.exampleapp.springboot.flights.dto.PigeonDto;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class PigeonConverter implements Converter<PigeonDto, Pigeon> {

    private OwnerRepository ownerRepository;

    @Override
    public Pigeon convert(PigeonDto pigeonDto) {
        return Pigeon.builder()
                .id(pigeonDto.getId())
                .name(pigeonDto.getName())
                .owner(ownerRepository.findById(pigeonDto.getOwnerId())
                        .orElseThrow(() -> new IllegalArgumentException("OwnerId does not exists.")))
                .build();
    }
}
