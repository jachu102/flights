package com.bsd.exampleapp.springboot.flights.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bsd.exampleapp.springboot.flights.controller.FlightsController;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FlightsController.class, secure = false)
public class FlightsControllerTest {
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private ArrivalsService service;
    
    @Before
    public void setUp() {
    }
	
    @Test
    public void add() throws Exception {
    	Pigeon newPigeon = new Pigeon();
    	newPigeon.setId(2L);
    	newPigeon.setName("test 2");
    	
    	Mockito.when( service.add(Mockito.any()) ).thenReturn(newPigeon);
    	
    	MockHttpServletResponse response = mvc.perform( post("/flight/add") 
			      										.contentType( MediaType.APPLICATION_JSON )
			      										.content( "{ \"name\" : \"test 2\" }" )
    												   ).andReturn().getResponse();
    	assertThat( response.getStatus() ).isEqualTo(HttpStatus.CREATED.value());
    }
    
    @Test
    public void addWithNotValidBody() throws Exception {
    	
    	MockHttpServletResponse response = mvc.perform( post("/flight/add") 
			      										.contentType( MediaType.APPLICATION_JSON )
			      										.content( "{ \"name\" : \"   \" }" )
    													).andReturn().getResponse();
    	assertThat( response.getStatus() ).isEqualTo(400);
    	assertThat( response.getContentAsString() ).contains("Validation Failed");
    }
    
    @Test
    public void update() throws Exception {
    	Pigeon newPigeon = new Pigeon();
    	newPigeon.setId(2L);
    	newPigeon.setName("test 2");
    	
    	MockHttpServletResponse response = mvc.perform( put("/flight/update/2") 
			      										.contentType( MediaType.APPLICATION_JSON )
			      										.content( "{ \"name\" : \"test 2 updated\" }" )
    												   ).andReturn().getResponse();
    	assertThat( response.getStatus() ).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void updateWithNotValidBody() throws Exception {
    	
    	MockHttpServletResponse response = mvc.perform( put("/flight/update/2") 
			      										.contentType( MediaType.APPLICATION_JSON )
			      										.content( "{  }" )
    													).andReturn().getResponse();
    	assertThat( response.getStatus() ).isEqualTo(HttpStatus.BAD_REQUEST.value());
    	assertThat( response.getContentAsString() ).contains("Validation Failed");
    }
}