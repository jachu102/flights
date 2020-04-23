package com.bsd.exampleapp.springboot.flights.integration;

import com.bsd.exampleapp.springboot.flights.model.Owner;
import com.bsd.exampleapp.springboot.flights.model.Pigeon;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import com.bsd.exampleapp.springboot.flights.service.ArrivalsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArrivalsTest {

    public static final long TEST_OWNER_ID = 1L;

    @Autowired
    private ArrivalsService arrivalsService;

    //TODO move repo to common service
    @Autowired
    private OwnerRepository ownerRepository;

    @MockBean
    private ConversionService conversionService;

    @BeforeEach
    void setUp() {
        ownerRepository.save(Owner.builder().name("testing owner").id(1L).build());
    }

    @Test
    void shouldAdd() {
        Pigeon actual = arrivalsService.add(
                Pigeon.builder()
                        .name("new pigeon")
                        .owner(ownerRepository.findById(TEST_OWNER_ID).get())
                        .build()
        );
        assertThat(actual).isNotNull();
    }

    @Test
    void shouldChangeName() {
        Pigeon stored = arrivalsService.add(
                Pigeon.builder()
                        .id(1L)
                        .name("new pigeon")
                        .owner(ownerRepository.findById(TEST_OWNER_ID).get())
                        .build()
        );
        final String changedName = "changed name";
        stored.setName(changedName);
        arrivalsService.update(stored);

        assertThat(arrivalsService.get(TEST_OWNER_ID).get().getName()).isEqualTo(changedName);
    }

    @Test
    void shouldRemove() {
        Pigeon stored = arrivalsService.add(
                Pigeon.builder()
                        .name("new pigeon")
                        .owner(ownerRepository.findById(TEST_OWNER_ID).get())
                        .build()
        );
        arrivalsService.remove(stored.getId());
    }
}
