package com.bsd.exampleapp.springboot.flights.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pigeon")
public class Pigeon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pigeon_generator")
    @SequenceGenerator(name = "pigeon_generator", sequenceName = "pigeon_seq")
    private Long id;

    @Column
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "ID")
	private Owner owner;

    public Pigeon() {
    }

    public Pigeon(Long id, String name, Owner owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Pigeon(String name, Owner owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
