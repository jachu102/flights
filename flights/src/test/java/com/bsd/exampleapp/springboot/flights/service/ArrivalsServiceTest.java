package com.bsd.exampleapp.springboot.flights.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.FlightRepository;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsServiceImpl;

@RunWith(SpringRunner.class)
public class ArrivalsServiceTest {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public ArrivalsService arrivalsService() {
            return new ArrivalsServiceImpl();
        }
    }
	
	@Autowired
	private ArrivalsService arrivalsService;
	
	@MockBean
	private FlightRepository flightRepository;
	
	private List<Pigeon> testPigeons = new ArrayList<Pigeon>();
	
	@Before
	public void setUp() {
		createTestData();
	 
	    Mockito.when(flightRepository.findByName(Mockito.any())).thenReturn(testPigeons);
	    Mockito.when(flightRepository.save(Mockito.any())).thenReturn(testPigeons.get(0));
	    Mockito.when(flightRepository.existsById(testPigeons.get(0).getId())).thenReturn(true);
	    Mockito.when(flightRepository.findById(Mockito.any())).thenReturn( Optional.of(testPigeons.get(0)) );
	    Mockito.doNothing().when(flightRepository).deleteById(Mockito.anyLong());
	}

	private void createTestData() {
		Pigeon newPigeon = new Pigeon();
	    newPigeon.setId(1L);
	    newPigeon.setName("test");
	    testPigeons.add(newPigeon);
	}
	
	@Test
	public void findByName() {
		assertThat( arrivalsService.findByName(testPigeons.get(0).getName()).get(0) )
		.isEqualToComparingFieldByField( testPigeons.get(0) );
	}

	@Test
	public void add() {
		assertThat( arrivalsService.add(testPigeons.get(0)) )
		.isEqualToComparingFieldByField( testPigeons.get(0) );
	}
	
	@Test 
	public void update() throws Exception {
		testPigeons.get(0).setName("updated test name");
		arrivalsService.update(testPigeons.get(0));
		
		assertThat( arrivalsService.get(testPigeons.get(0).getId()).get() )
		.isEqualToComparingFieldByField( testPigeons.get(0) );
	}
	
	@Test
	public void remove() {
		arrivalsService.remove(1L);
	}
}
