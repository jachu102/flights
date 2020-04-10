package com.bsd.exampleapp.springboot.flights.controller;

import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;
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
    private MockMvc mvc;

    @MockBean
    private ArrivalsService arrivalsService;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldAddNewArrivedPigeon() throws Exception {
        Pigeon newPigeon = new Pigeon();
        newPigeon.setId(2L);
        newPigeon.setName("test 2");

        Mockito.when(arrivalsService.add(Mockito.any())).thenReturn(newPigeon);

        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"test 2\" }"))
				.andExpect(status().isCreated())
                .andExpect(jsonPath("$..name").value(newPigeon.getName()))
                .andExpect(jsonPath("$..id").value(newPigeon.getId().intValue()));
    }

    @Test
    public void shouldNotAddNewArrivedPigeon_whenEmptyFieldValue() throws Exception {
        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"   \" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not allowed value of input data field."))
                .andExpect(jsonPath("$.details").value("Field 'name' must not be blank."));
    }

    @Test
    public void shouldNotAddNewArrivedPigeon_whenNullFieldValue() throws Exception {
        mvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : null }"))
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
        Pigeon pigeon = new Pigeon();
        pigeon.setId(5L);
        pigeon.setName("test 5");

        Mockito.when(arrivalsService.getAll()).thenReturn(Arrays.asList(pigeon));
        mvc.perform(MockMvcRequestBuilders.get("/flight/getAll"))
                .andExpect(status().isOk())
                //FIXME .andExpect( jsonPath("$..lenght()").value(1) )
                .andExpect(jsonPath("$..[0].name").value(pigeon.getName()))
                .andExpect(jsonPath("$..[0].id").value(pigeon.getId().intValue()));
    }

    @Test
    public void shouldGetOneArrivedPigeon() throws Exception {
        Pigeon pigeon = new Pigeon();
        pigeon.setId(4L);
        pigeon.setName("test 4");

        Mockito.when(arrivalsService.get(pigeon.getId())).thenReturn(Optional.of(pigeon));
        mvc.perform(MockMvcRequestBuilders.get("/flight/get/" + pigeon.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name").value(pigeon.getName()))
                .andExpect(jsonPath("$..id").value(pigeon.getId().intValue()));
    }

    @Test
    public void shouldFindArrivedPigeonByName() throws Exception {
        Pigeon pigeon = new Pigeon();
        pigeon.setId(3L);
        pigeon.setName("test 3");

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
        mvc.perform(put("/flight/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\" : \"test 2 updated\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotUpdate_WhenDifferentId() throws Exception {
        mvc.perform(put("/flight/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\" : 999, \"name\" : \"test 2 updated\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No integrity of input data."))
                .andExpect(jsonPath("$.details").value("URI id and id not match."));
    }

    @Test
    public void shouldNotUpdate_whenNotFieldFilled() throws Exception {
        mvc.perform(put("/flight/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{  }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not allowed value of input data field."))
                .andExpect(jsonPath("$.details").value("Field 'name' must not be blank."));
    }

	@Test
	public void shouldNotUpdate_whenNotExist() throws Exception {
		Long id = 1L;
		Mockito.doThrow(new IllegalArgumentException("Expected id does not exist.")).when(arrivalsService).update(Mockito.any());
		mvc.perform(put("/flight/update/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\" : \"test 2 updated\" }"))
				.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Data not exists."))
                .andExpect(jsonPath("$.details").value("Expected id does not exist."));
	}
}
