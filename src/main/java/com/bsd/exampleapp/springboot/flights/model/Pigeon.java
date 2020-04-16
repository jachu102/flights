package com.bsd.exampleapp.springboot.flights.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "pigeon")
public class Pigeon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pigeon_generator")
    @SequenceGenerator(name = "pigeon_generator", sequenceName = "pigeon_seq")
    private Long id;

    @Setter
    @Column
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "ID")
	private Owner owner;
}
