package com.bsd.exampleapp.springboot.flights.repository;

import com.bsd.exampleapp.springboot.flights.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

	Optional<Owner> findById(Long aLong);
}
