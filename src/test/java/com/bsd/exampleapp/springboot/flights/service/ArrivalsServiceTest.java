package com.bsd.exampleapp.springboot.flights.service;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.FlightRepository;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class ArrivalsServiceTest {

    @Autowired
    private ArrivalsService arrivalsService;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private OwnerRepository ownerRepository;

    private List<Pigeon> storedPigeons = new ArrayList<Pigeon>();

    @BeforeEach
    void setUp() {
        storedPigeons.add(Pigeon.builder().id(1L).name("test").build());

        Mockito.when(flightRepository.findByName(Mockito.any()))
                .thenReturn(storedPigeons);
        Mockito.when(flightRepository.save(Mockito.any()))
                .thenReturn(storedPigeons.get(0));
        Mockito.when(flightRepository.existsById(storedPigeons.get(0).getId()))
                .thenReturn(true);
        Mockito.when(flightRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(storedPigeons.get(0)));
        Mockito.doNothing().when(flightRepository)
                .deleteById(Mockito.anyLong());
        Mockito.when(flightRepository.findAll(Sort.by(Sort.Direction.DESC, "name")))
                .thenReturn(storedPigeons);
    }

    @Test
    public void shouldAdd() {
        Pigeon newPigeon = Pigeon.builder().name("test").build();

        assertThat(arrivalsService.add(newPigeon))
                .satisfies(result -> {
                    assertThat(result.getId()).isPositive();
                    assertThat(result.getName()).isEqualTo(storedPigeons.get(0).getName());
                });
    }

    @Test
    public void shouldUpdate_whenExists() {
        storedPigeons.get(0).setName("updated test name");
        arrivalsService.update(storedPigeons.get(0));

        assertThat(arrivalsService.get(storedPigeons.get(0).getId()).get())
                .isEqualToComparingFieldByField(storedPigeons.get(0));
    }

    @Test
    public void shouldThrowException_whenUpdateNotExisting() {
        Pigeon changedPigeon = Pigeon.builder().id(9L).name("test 2").build();
        final String expectedMesssage = "No class com.bsd.exampleapp.springboot.flights.model.Pigeon entity with id "
                + changedPigeon.getId() + " exists!";
        Mockito.when(flightRepository.existsById(storedPigeons.get(0).getId()))
                .thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> arrivalsService.update(changedPigeon));
        assertTrue(exception.getMessage().equals(expectedMesssage));
    }

    @Test
    public void shouldRemove() {
        arrivalsService.remove(1L);
    }

    @Test
    public void shouldGet() {
        assertThat(arrivalsService.get(storedPigeons.get(0).getId()).get())
                .isEqualToComparingFieldByField(storedPigeons.get(0));
    }

    @Test
    public void shouldGetAll() {
        assertThat(arrivalsService.getAll().size())
                .isEqualTo(storedPigeons.size());
    }

    @Test
    public void shouldFindByName() {
        assertThat(arrivalsService.findByName(storedPigeons.get(0).getName()).get(0))
                .isEqualToComparingFieldByField(storedPigeons.get(0));
    }
}