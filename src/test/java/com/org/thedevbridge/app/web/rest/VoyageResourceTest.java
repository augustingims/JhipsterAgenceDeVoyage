package com.org.thedevbridge.app.web.rest;

import com.org.thedevbridge.app.Application;
import com.org.thedevbridge.app.domain.Voyage;
import com.org.thedevbridge.app.repository.VoyageRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the VoyageResource REST controller.
 *
 * @see VoyageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VoyageResourceTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_VILLE_DEPART = "AAAAA";
    private static final String UPDATED_VILLE_DEPART = "BBBBB";
    private static final String DEFAULT_VILLE_ARRIVEE = "AAAAA";
    private static final String UPDATED_VILLE_ARRIVEE = "BBBBB";
    private static final String DEFAULT_HEURE_DEPART = "AAAAA";
    private static final String UPDATED_HEURE_DEPART = "BBBBB";
    private static final String DEFAULT_HEURE_ARRIVEE = "AAAAA";
    private static final String UPDATED_HEURE_ARRIVEE = "BBBBB";

    @Inject
    private VoyageRepository voyageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVoyageMockMvc;

    private Voyage voyage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VoyageResource voyageResource = new VoyageResource();
        ReflectionTestUtils.setField(voyageResource, "voyageRepository", voyageRepository);
        this.restVoyageMockMvc = MockMvcBuilders.standaloneSetup(voyageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        voyage = new Voyage();
        voyage.setDate(DEFAULT_DATE);
        voyage.setVille_depart(DEFAULT_VILLE_DEPART);
        voyage.setVille_arrivee(DEFAULT_VILLE_ARRIVEE);
        voyage.setHeure_depart(DEFAULT_HEURE_DEPART);
        voyage.setHeure_arrivee(DEFAULT_HEURE_ARRIVEE);
    }

    @Test
    @Transactional
    public void createVoyage() throws Exception {
        int databaseSizeBeforeCreate = voyageRepository.findAll().size();

        // Create the Voyage

        restVoyageMockMvc.perform(post("/api/voyages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(voyage)))
                .andExpect(status().isCreated());

        // Validate the Voyage in the database
        List<Voyage> voyages = voyageRepository.findAll();
        assertThat(voyages).hasSize(databaseSizeBeforeCreate + 1);
        Voyage testVoyage = voyages.get(voyages.size() - 1);
        assertThat(testVoyage.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testVoyage.getVille_depart()).isEqualTo(DEFAULT_VILLE_DEPART);
        assertThat(testVoyage.getVille_arrivee()).isEqualTo(DEFAULT_VILLE_ARRIVEE);
        assertThat(testVoyage.getHeure_depart()).isEqualTo(DEFAULT_HEURE_DEPART);
        assertThat(testVoyage.getHeure_arrivee()).isEqualTo(DEFAULT_HEURE_ARRIVEE);
    }

    @Test
    @Transactional
    public void getAllVoyages() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get all the voyages
        restVoyageMockMvc.perform(get("/api/voyages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(voyage.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].ville_depart").value(hasItem(DEFAULT_VILLE_DEPART.toString())))
                .andExpect(jsonPath("$.[*].ville_arrivee").value(hasItem(DEFAULT_VILLE_ARRIVEE.toString())))
                .andExpect(jsonPath("$.[*].heure_depart").value(hasItem(DEFAULT_HEURE_DEPART.toString())))
                .andExpect(jsonPath("$.[*].heure_arrivee").value(hasItem(DEFAULT_HEURE_ARRIVEE.toString())));
    }

    @Test
    @Transactional
    public void getVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

        // Get the voyage
        restVoyageMockMvc.perform(get("/api/voyages/{id}", voyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(voyage.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.ville_depart").value(DEFAULT_VILLE_DEPART.toString()))
            .andExpect(jsonPath("$.ville_arrivee").value(DEFAULT_VILLE_ARRIVEE.toString()))
            .andExpect(jsonPath("$.heure_depart").value(DEFAULT_HEURE_DEPART.toString()))
            .andExpect(jsonPath("$.heure_arrivee").value(DEFAULT_HEURE_ARRIVEE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoyage() throws Exception {
        // Get the voyage
        restVoyageMockMvc.perform(get("/api/voyages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

		int databaseSizeBeforeUpdate = voyageRepository.findAll().size();

        // Update the voyage
        voyage.setDate(UPDATED_DATE);
        voyage.setVille_depart(UPDATED_VILLE_DEPART);
        voyage.setVille_arrivee(UPDATED_VILLE_ARRIVEE);
        voyage.setHeure_depart(UPDATED_HEURE_DEPART);
        voyage.setHeure_arrivee(UPDATED_HEURE_ARRIVEE);

        restVoyageMockMvc.perform(put("/api/voyages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(voyage)))
                .andExpect(status().isOk());

        // Validate the Voyage in the database
        List<Voyage> voyages = voyageRepository.findAll();
        assertThat(voyages).hasSize(databaseSizeBeforeUpdate);
        Voyage testVoyage = voyages.get(voyages.size() - 1);
        assertThat(testVoyage.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testVoyage.getVille_depart()).isEqualTo(UPDATED_VILLE_DEPART);
        assertThat(testVoyage.getVille_arrivee()).isEqualTo(UPDATED_VILLE_ARRIVEE);
        assertThat(testVoyage.getHeure_depart()).isEqualTo(UPDATED_HEURE_DEPART);
        assertThat(testVoyage.getHeure_arrivee()).isEqualTo(UPDATED_HEURE_ARRIVEE);
    }

    @Test
    @Transactional
    public void deleteVoyage() throws Exception {
        // Initialize the database
        voyageRepository.saveAndFlush(voyage);

		int databaseSizeBeforeDelete = voyageRepository.findAll().size();

        // Get the voyage
        restVoyageMockMvc.perform(delete("/api/voyages/{id}", voyage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Voyage> voyages = voyageRepository.findAll();
        assertThat(voyages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
