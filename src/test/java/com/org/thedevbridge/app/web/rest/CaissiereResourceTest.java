package com.org.thedevbridge.app.web.rest;

import com.org.thedevbridge.app.Application;
import com.org.thedevbridge.app.domain.Caissiere;
import com.org.thedevbridge.app.repository.CaissiereRepository;

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
 * Test class for the CaissiereResource REST controller.
 *
 * @see CaissiereResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CaissiereResourceTest {

    private static final String DEFAULT_NOM_CAISSIERE = "AAAAA";
    private static final String UPDATED_NOM_CAISSIERE = "BBBBB";
    private static final String DEFAULT_PRENOM_CAISSIERE = "AAAAA";
    private static final String UPDATED_PRENOM_CAISSIERE = "BBBBB";

    private static final Long DEFAULT_TEL_CAISSIERE = 1L;
    private static final Long UPDATED_TEL_CAISSIERE = 2L;
    private static final String DEFAULT_ADRESSE_CAISSIERE = "AAAAA";
    private static final String UPDATED_ADRESSE_CAISSIERE = "BBBBB";

    @Inject
    private CaissiereRepository caissiereRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCaissiereMockMvc;

    private Caissiere caissiere;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CaissiereResource caissiereResource = new CaissiereResource();
        ReflectionTestUtils.setField(caissiereResource, "caissiereRepository", caissiereRepository);
        this.restCaissiereMockMvc = MockMvcBuilders.standaloneSetup(caissiereResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        caissiere = new Caissiere();
        caissiere.setNom_caissiere(DEFAULT_NOM_CAISSIERE);
        caissiere.setPrenom_caissiere(DEFAULT_PRENOM_CAISSIERE);
        caissiere.setTel_caissiere(DEFAULT_TEL_CAISSIERE);
        caissiere.setAdresse_caissiere(DEFAULT_ADRESSE_CAISSIERE);
    }

    @Test
    @Transactional
    public void createCaissiere() throws Exception {
        int databaseSizeBeforeCreate = caissiereRepository.findAll().size();

        // Create the Caissiere

        restCaissiereMockMvc.perform(post("/api/caissieres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(caissiere)))
                .andExpect(status().isCreated());

        // Validate the Caissiere in the database
        List<Caissiere> caissieres = caissiereRepository.findAll();
        assertThat(caissieres).hasSize(databaseSizeBeforeCreate + 1);
        Caissiere testCaissiere = caissieres.get(caissieres.size() - 1);
        assertThat(testCaissiere.getNom_caissiere()).isEqualTo(DEFAULT_NOM_CAISSIERE);
        assertThat(testCaissiere.getPrenom_caissiere()).isEqualTo(DEFAULT_PRENOM_CAISSIERE);
        assertThat(testCaissiere.getTel_caissiere()).isEqualTo(DEFAULT_TEL_CAISSIERE);
        assertThat(testCaissiere.getAdresse_caissiere()).isEqualTo(DEFAULT_ADRESSE_CAISSIERE);
    }

    @Test
    @Transactional
    public void getAllCaissieres() throws Exception {
        // Initialize the database
        caissiereRepository.saveAndFlush(caissiere);

        // Get all the caissieres
        restCaissiereMockMvc.perform(get("/api/caissieres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(caissiere.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom_caissiere").value(hasItem(DEFAULT_NOM_CAISSIERE.toString())))
                .andExpect(jsonPath("$.[*].prenom_caissiere").value(hasItem(DEFAULT_PRENOM_CAISSIERE.toString())))
                .andExpect(jsonPath("$.[*].tel_caissiere").value(hasItem(DEFAULT_TEL_CAISSIERE.intValue())))
                .andExpect(jsonPath("$.[*].adresse_caissiere").value(hasItem(DEFAULT_ADRESSE_CAISSIERE.toString())));
    }

    @Test
    @Transactional
    public void getCaissiere() throws Exception {
        // Initialize the database
        caissiereRepository.saveAndFlush(caissiere);

        // Get the caissiere
        restCaissiereMockMvc.perform(get("/api/caissieres/{id}", caissiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(caissiere.getId().intValue()))
            .andExpect(jsonPath("$.nom_caissiere").value(DEFAULT_NOM_CAISSIERE.toString()))
            .andExpect(jsonPath("$.prenom_caissiere").value(DEFAULT_PRENOM_CAISSIERE.toString()))
            .andExpect(jsonPath("$.tel_caissiere").value(DEFAULT_TEL_CAISSIERE.intValue()))
            .andExpect(jsonPath("$.adresse_caissiere").value(DEFAULT_ADRESSE_CAISSIERE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaissiere() throws Exception {
        // Get the caissiere
        restCaissiereMockMvc.perform(get("/api/caissieres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaissiere() throws Exception {
        // Initialize the database
        caissiereRepository.saveAndFlush(caissiere);

		int databaseSizeBeforeUpdate = caissiereRepository.findAll().size();

        // Update the caissiere
        caissiere.setNom_caissiere(UPDATED_NOM_CAISSIERE);
        caissiere.setPrenom_caissiere(UPDATED_PRENOM_CAISSIERE);
        caissiere.setTel_caissiere(UPDATED_TEL_CAISSIERE);
        caissiere.setAdresse_caissiere(UPDATED_ADRESSE_CAISSIERE);

        restCaissiereMockMvc.perform(put("/api/caissieres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(caissiere)))
                .andExpect(status().isOk());

        // Validate the Caissiere in the database
        List<Caissiere> caissieres = caissiereRepository.findAll();
        assertThat(caissieres).hasSize(databaseSizeBeforeUpdate);
        Caissiere testCaissiere = caissieres.get(caissieres.size() - 1);
        assertThat(testCaissiere.getNom_caissiere()).isEqualTo(UPDATED_NOM_CAISSIERE);
        assertThat(testCaissiere.getPrenom_caissiere()).isEqualTo(UPDATED_PRENOM_CAISSIERE);
        assertThat(testCaissiere.getTel_caissiere()).isEqualTo(UPDATED_TEL_CAISSIERE);
        assertThat(testCaissiere.getAdresse_caissiere()).isEqualTo(UPDATED_ADRESSE_CAISSIERE);
    }

    @Test
    @Transactional
    public void deleteCaissiere() throws Exception {
        // Initialize the database
        caissiereRepository.saveAndFlush(caissiere);

		int databaseSizeBeforeDelete = caissiereRepository.findAll().size();

        // Get the caissiere
        restCaissiereMockMvc.perform(delete("/api/caissieres/{id}", caissiere.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Caissiere> caissieres = caissiereRepository.findAll();
        assertThat(caissieres).hasSize(databaseSizeBeforeDelete - 1);
    }
}
