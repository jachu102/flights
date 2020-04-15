package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.dto.PigeonDto;
import com.bsd.exampleapp.springboot.flights.model.Owner;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.FlightRepository;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;
import com.bsd.exampleapp.springboot.flights.service.PigeonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FlightsController.class, secure = false)
public class FlightsControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ArrivalsService arrivalsService;

    @MockBean
    PigeonConverter pigeonConverter;

    @MockBean
    OwnerRepository ownerRepository;

    @MockBean
    FlightRepository flightRepository;

    private Owner owner;

    private ObjectWriter ow;

    @Before
    public void setUp() throws JsonProcessingException {
        owner = new Owner(1L, "admin");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Test
    public void shouldAddNewArrivedPigeon() throws Exception {
        PigeonDto dto = new PigeonDto();
        dto.setName("test 2");
        dto.setOwnerId(1L);

        Pigeon savedPigeon = new Pigeon(2L, "test 2", owner);

        Mockito.when(arrivalsService.add(Mockito.any())).thenReturn(savedPigeon);

        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        //FIXME when fields names are not unique
//                .andExpect(jsonPath("$..name").value(savedPigeon.getName()))
//                .andExpect(jsonPath("$..id").value(savedPigeon.getId().intValue()));
    }

    @Test
    public void shouldNotAddNewArrivedPigeon_whenEmptyFieldValue() throws Exception {
        PigeonDto dto = new PigeonDto();
        dto.setName("   ");
        dto.setOwnerId(1L);

        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not allowed value of input data field."))
                .andExpect(jsonPath("$.details").value("Field 'name' must not be blank."));
    }

    @Test
    public void shouldNotAddNewArrivedPigeon_whenNullFieldValue() throws Exception {
        PigeonDto dto = new PigeonDto();
        dto.setName(null);
        dto.setOwnerId(1L);

        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not allowed value of input data field."))
                .andExpect(jsonPath("$.details").value("Field 'name' must not be blank."));
    }

    @Test
    public void shouldNotAddNewArrivedPigeon_whenNotValidFieldKey() throws Exception {
        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"wrongName\" : \"test 1\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not relevant input data."));
    }

    @Test
    public void shouldNotAddNewArrivedPigeon_whenAddedNotValidField() throws Exception {
        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"test 1\", \"wrongName\" : \"test 2\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not relevant input data."));
    }

    @Test
    public void shouldGetAllArrivedPigeons() throws Exception {
        Pigeon pigeon = new Pigeon(5L, "test 5", null);

        Mockito.when(arrivalsService.getAll()).thenReturn(Arrays.asList(pigeon));
        mvc.perform(MockMvcRequestBuilders.get("/flight/getAll"))
                .andExpect(status().isOk())
                //FIXME .andExpect( jsonPath("$..lenght()").value(1) )
                .andExpect(jsonPath("$..[0].name").value(pigeon.getName()))
                .andExpect(jsonPath("$..[0].id").value(pigeon.getId().intValue()));
    }

    @Test
    public void shouldGetOneArrivedPigeon() throws Exception {
        Pigeon pigeon = new Pigeon(4L, "test 4", null);

        Mockito.when(arrivalsService.get(pigeon.getId())).thenReturn(Optional.of(pigeon));
        mvc.perform(MockMvcRequestBuilders.get("/flight/get/" + pigeon.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name").value(pigeon.getName()))
                .andExpect(jsonPath("$..id").value(pigeon.getId().intValue()));
    }

    @Test
    public void shouldFindArrivedPigeonByName() throws Exception {
        Pigeon pigeon = new Pigeon(3L, "test 3", null);

        Mockito.when(arrivalsService.findByName(pigeon.getName())).thenReturn(Arrays.asList(pigeon));
        mvc.perform(MockMvcRequestBuilders.get("/flight/findByName").param("name", pigeon.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[0].name").value(pigeon.getName()))
                .andExpect(jsonPath("$..[0].id").value(pigeon.getId().intValue()));
    }

    @Test
    public void shouldRemoveExistedArrivedPigeon() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(arrivalsService).remove(id);
        mvc.perform(delete("/flight/remove/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotRemove_whenNotExist() throws Exception {
        Long id = 1L;
        Mockito.doThrow(new IllegalArgumentException("Expected id does not exist.")).when(arrivalsService).remove(id);
        mvc.perform(delete("/flight/remove/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Data not exists."))
                .andExpect(jsonPath("$.details").value("Expected id does not exist."));
    }

    @Test
    public void shouldUpdate() throws Exception {
        PigeonDto dto = new PigeonDto();
        dto.setName("test 2 updated");
        dto.setOwnerId(1L);

        mvc.perform(put("/flight/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotUpdate_WhenDifferentId() throws Exception {
        PigeonDto dto = new PigeonDto();
        dto.setId(999L);
        dto.setName("test 2 updated");
        dto.setOwnerId(1L);

        mvc.perform(put("/flight/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No integrity of input data."))
                .andExpect(jsonPath("$.details").value("URI id and id not match."));
    }

    @Test
    public void shouldNotUpdate_whenNotFieldFilled() throws Exception {
        PigeonDto dto = new PigeonDto();
        dto.setName("test");

        mvc.perform(put("/flight/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not allowed value of input data field."))
                .andExpect(jsonPath("$.details").value("Field 'ownerId' must not be null."));
    }

    @Test
    public void shouldNotUpdate_whenNotExist() throws Exception {
        PigeonDto dto = new PigeonDto();
        dto.setId(999L);
        dto.setName("test 2 updated");
        dto.setOwnerId(1L);
        Mockito.doThrow(new IllegalArgumentException("Expected id does not exist.")).when(arrivalsService).update(Mockito.any());

        mvc.perform(put("/flight/update/" + dto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Data not exists."))
                .andExpect(jsonPath("$.details").value("Expected id does not exist."));
    }
}
