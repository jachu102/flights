package com.bsd.exampleapp.springboot.flights.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Accessors(chain = true)
@Data
public class OwnerDto {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 30)
    private String name;
}
