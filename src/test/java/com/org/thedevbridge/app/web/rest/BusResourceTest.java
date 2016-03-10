package com.org.thedevbridge.app.web.rest;

import com.org.thedevbridge.app.Application;
import com.org.thedevbridge.app.domain.Bus;
import com.org.thedevbridge.app.repository.BusRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BusResource REST controller.
 *
 * @see BusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BusResourceTest {

    private static final String DEFAULT_NOM_BUS = "AAAAA";
    private static final String UPDATED_NOM_BUS = "BBBBB";
    private static final String DEFAULT_IMMATRICULATION = "AAAAA";
    private static final String UPDATED_IMMATRICULATION = "BBBBB";
    private static final String DEFAULT_MARQUE = "AAAAA";
    private static final String UPDATED_MARQUE = "BBBBB";

    @Inject
    private BusRepository busRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBusMockMvc;

    private Bus bus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusResource busResource = new BusResource();
        ReflectionTestUtils.setField(busResource, "busRepository", busRepository);
        this.restBusMockMvc = MockMvcBuilders.standaloneSetup(busResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bus = new Bus();
        bus.setNom_bus(DEFAULT_NOM_BUS);
        bus.setImmatriculation(DEFAULT_IMMATRICULATION);
        bus.setMarque(DEFAULT_MARQUE);
    }

    @Test
    @Transactional
    public void createBus() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // Create the Bus

        restBusMockMvc.perform(post("/api/buss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bus)))
                .andExpect(status().isCreated());

        // Validate the Bus in the database
        List<Bus> buss = busRepository.findAll();
        assertThat(buss).hasSize(databaseSizeBeforeCreate + 1);
        Bus testBus = buss.get(buss.size() - 1);
        assertThat(testBus.getNom_bus()).isEqualTo(DEFAULT_NOM_BUS);
        assertThat(testBus.getImmatriculation()).isEqualTo(DEFAULT_IMMATRICULATION);
        assertThat(testBus.getMarque()).isEqualTo(DEFAULT_MARQUE);
    }

    @Test
    @Transactional
    public void getAllBuss() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the buss
        restBusMockMvc.perform(get("/api/buss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom_bus").value(hasItem(DEFAULT_NOM_BUS.toString())))
                .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())))
                .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE.toString())));
    }

    @Test
    @Transactional
    public void getBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get the bus
        restBusMockMvc.perform(get("/api/buss/{id}", bus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bus.getId().intValue()))
            .andExpect(jsonPath("$.nom_bus").value(DEFAULT_NOM_BUS.toString()))
            .andExpect(jsonPath("$.immatriculation").value(DEFAULT_IMMATRICULATION.toString()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBus() throws Exception {
        // Get the bus
        restBusMockMvc.perform(get("/api/buss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

		int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus
        bus.setNom_bus(UPDATED_NOM_BUS);
        bus.setImmatriculation(UPDATED_IMMATRICULATION);
        bus.setMarque(UPDATED_MARQUE);

        restBusMockMvc.perform(put("/api/buss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bus)))
                .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> buss = busRepository.findAll();
        assertThat(buss).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = buss.get(buss.size() - 1);
        assertThat(testBus.getNom_bus()).isEqualTo(UPDATED_NOM_BUS);
        assertThat(testBus.getImmatriculation()).isEqualTo(UPDATED_IMMATRICULATION);
        assertThat(testBus.getMarque()).isEqualTo(UPDATED_MARQUE);
    }

    @Test
    @Transactional
    public void deleteBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

		int databaseSizeBeforeDelete = busRepository.findAll().size();

        // Get the bus
        restBusMockMvc.perform(delete("/api/buss/{id}", bus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bus> buss = busRepository.findAll();
        assertThat(buss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
