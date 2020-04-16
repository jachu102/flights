package com.bsd.exampleapp.springboot.flights.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_generator")
    @SequenceGenerator(name = "owner_generator", sequenceName = "owner_seq")
    private Long id;

    @Column
    @NotBlank
    @Size(min = 5, max = 30)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private Set<Pigeon> pigeons;
}
