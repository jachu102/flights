package com.bsd.exampleapp.springboot.flights.service;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.FlightRepository;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ArrivalsServiceTest {
	
	@TestConfiguration
    static class TestContextConfiguration {
        @Bean
        public ArrivalsService arrivalsService() {
            return new ArrivalsServiceImpl();
        }
    }
	
	@Autowired
	private ArrivalsService arrivalsService;
	
	@MockBean
	private FlightRepository flightRepository;

	@MockBean
	private OwnerRepository ownerRepository;
	
	private List<Pigeon> storedPigeons = new ArrayList<Pigeon>();
	
	@Before
	public void setUp() {
		createTestData();
	 
	    Mockito.when(flightRepository.findByName(Mockito.any()))
				.thenReturn(storedPigeons);
	    Mockito.when(flightRepository.save(Mockito.any()))
				.thenReturn(storedPigeons.get(0));
	    Mockito.when(flightRepository.existsById(storedPigeons.get(0).getId()))
				.thenReturn(true);
	    Mockito.when(flightRepository.findById(Mockito.any()))
				.thenReturn( Optional.of(storedPigeons.get(0)) );
	    Mockito.doNothing().when(flightRepository)
				.deleteById(Mockito.anyLong());
	    Mockito.when(flightRepository.findAll(Sort.by(Direction.DESC, "name")))
				.thenReturn(storedPigeons);
	}

	private void createTestData() {
	    storedPigeons.add(new Pigeon(1L, "test", null));
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void shouldAdd() {
		Pigeon newPigeon = new Pigeon("test", null);

		assertThat( arrivalsService.add(newPigeon) )
				.satisfies( result -> {
					assertThat(result.getId()).isPositive();
					assertThat(result.getName()).isEqualTo(storedPigeons.get(0).getName());
				});
	}
	
	@Test 
	public void shouldUpdate_whenExists() {
		storedPigeons.get(0).setName("updated test name");
		arrivalsService.update(storedPigeons.get(0));
		
		assertThat( arrivalsService.get(storedPigeons.get(0).getId()).get() )
		.isEqualToComparingFieldByField( storedPigeons.get(0) );
	}

	@Test
	public void shouldThrowException_whenUpdateNotExisting() {
		Pigeon changedPigeon = new Pigeon(9L, "test 2", null);

		Mockito.when(flightRepository.existsById(storedPigeons.get(0).getId()))
				.thenReturn(false);

		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("Expected id does not exist.");
		arrivalsService.update(changedPigeon);
	}
	
	@Test
	public void shouldRemove() {
		arrivalsService.remove(1L);
	}
	
	@Test
	public void shouldGet() {
		assertThat( arrivalsService.get( storedPigeons.get(0).getId()).get() )
		.isEqualToComparingFieldByField( storedPigeons.get(0) );
	}
	
	@Test
	public void shouldGetAll() {
		assertThat( arrivalsService.getAll().size() )
				.isEqualTo( storedPigeons.size() );
	}
	
	@Test
	public void shouldFindByName() {
		assertThat( arrivalsService.findByName(storedPigeons.get(0).getName()).get(0) )
		.isEqualToComparingFieldByField( storedPigeons.get(0) );
	}
	
}
