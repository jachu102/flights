package com.bsd.exampleapp.springboot.flights.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

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

    @OneToMany(mappedBy = "owner")
    private Set<Pigeon> pigeons;

    public Owner() {
    }

    public Owner(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
