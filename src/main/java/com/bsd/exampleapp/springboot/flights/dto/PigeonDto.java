package com.bsd.exampleapp.springboot.flights.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Accessors(chain = true)
@Data
public class PigeonDto {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @Min(value = 1)
    private Long ownerId;
}
