package com.org.thedevbridge.app.web.rest;

import com.org.thedevbridge.app.Application;
import com.org.thedevbridge.app.domain.Secretaire;
import com.org.thedevbridge.app.repository.SecretaireRepository;

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
 * Test class for the SecretaireResource REST controller.
 *
 * @see SecretaireResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SecretaireResourceTest {

    private static final String DEFAULT_NOM_SECRETAIRE = "AAAAA";
    private static final String UPDATED_NOM_SECRETAIRE = "BBBBB";
    private static final String DEFAULT_PRENOM_SECRETAIRE = "AAAAA";
    private static final String UPDATED_PRENOM_SECRETAIRE = "BBBBB";

    private static final Long DEFAULT_TEL_SECRETAIRE = 1L;
    private static final Long UPDATED_TEL_SECRETAIRE = 2L;
    private static final String DEFAULT_ADRESSE_SECRETAIRE = "AAAAA";
    private static final String UPDATED_ADRESSE_SECRETAIRE = "BBBBB";

    @Inject
    private SecretaireRepository secretaireRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSecretaireMockMvc;

    private Secretaire secretaire;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SecretaireResource secretaireResource = new SecretaireResource();
        ReflectionTestUtils.setField(secretaireResource, "secretaireRepository", secretaireRepository);
        this.restSecretaireMockMvc = MockMvcBuilders.standaloneSetup(secretaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        secretaire = new Secretaire();
        secretaire.setNom_secretaire(DEFAULT_NOM_SECRETAIRE);
        secretaire.setPrenom_secretaire(DEFAULT_PRENOM_SECRETAIRE);
        secretaire.setTel_secretaire(DEFAULT_TEL_SECRETAIRE);
        secretaire.setAdresse_secretaire(DEFAULT_ADRESSE_SECRETAIRE);
    }

    @Test
    @Transactional
    public void createSecretaire() throws Exception {
        int databaseSizeBeforeCreate = secretaireRepository.findAll().size();

        // Create the Secretaire

        restSecretaireMockMvc.perform(post("/api/secretaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(secretaire)))
                .andExpect(status().isCreated());

        // Validate the Secretaire in the database
        List<Secretaire> secretaires = secretaireRepository.findAll();
        assertThat(secretaires).hasSize(databaseSizeBeforeCreate + 1);
        Secretaire testSecretaire = secretaires.get(secretaires.size() - 1);
        assertThat(testSecretaire.getNom_secretaire()).isEqualTo(DEFAULT_NOM_SECRETAIRE);
        assertThat(testSecretaire.getPrenom_secretaire()).isEqualTo(DEFAULT_PRENOM_SECRETAIRE);
        assertThat(testSecretaire.getTel_secretaire()).isEqualTo(DEFAULT_TEL_SECRETAIRE);
        assertThat(testSecretaire.getAdresse_secretaire()).isEqualTo(DEFAULT_ADRESSE_SECRETAIRE);
    }

    @Test
    @Transactional
    public void getAllSecretaires() throws Exception {
        // Initialize the database
        secretaireRepository.saveAndFlush(secretaire);

        // Get all the secretaires
        restSecretaireMockMvc.perform(get("/api/secretaires"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(secretaire.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom_secretaire").value(hasItem(DEFAULT_NOM_SECRETAIRE.toString())))
                .andExpect(jsonPath("$.[*].prenom_secretaire").value(hasItem(DEFAULT_PRENOM_SECRETAIRE.toString())))
                .andExpect(jsonPath("$.[*].tel_secretaire").value(hasItem(DEFAULT_TEL_SECRETAIRE.intValue())))
                .andExpect(jsonPath("$.[*].adresse_secretaire").value(hasItem(DEFAULT_ADRESSE_SECRETAIRE.toString())));
    }

    @Test
    @Transactional
    public void getSecretaire() throws Exception {
        // Initialize the database
        secretaireRepository.saveAndFlush(secretaire);

        // Get the secretaire
        restSecretaireMockMvc.perform(get("/api/secretaires/{id}", secretaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(secretaire.getId().intValue()))
            .andExpect(jsonPath("$.nom_secretaire").value(DEFAULT_NOM_SECRETAIRE.toString()))
            .andExpect(jsonPath("$.prenom_secretaire").value(DEFAULT_PRENOM_SECRETAIRE.toString()))
            .andExpect(jsonPath("$.tel_secretaire").value(DEFAULT_TEL_SECRETAIRE.intValue()))
            .andExpect(jsonPath("$.adresse_secretaire").value(DEFAULT_ADRESSE_SECRETAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecretaire() throws Exception {
        // Get the secretaire
        restSecretaireMockMvc.perform(get("/api/secretaires/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecretaire() throws Exception {
        // Initialize the database
        secretaireRepository.saveAndFlush(secretaire);

		int databaseSizeBeforeUpdate = secretaireRepository.findAll().size();

        // Update the secretaire
        secretaire.setNom_secretaire(UPDATED_NOM_SECRETAIRE);
        secretaire.setPrenom_secretaire(UPDATED_PRENOM_SECRETAIRE);
        secretaire.setTel_secretaire(UPDATED_TEL_SECRETAIRE);
        secretaire.setAdresse_secretaire(UPDATED_ADRESSE_SECRETAIRE);

        restSecretaireMockMvc.perform(put("/api/secretaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(secretaire)))
                .andExpect(status().isOk());

        // Validate the Secretaire in the database
        List<Secretaire> secretaires = secretaireRepository.findAll();
        assertThat(secretaires).hasSize(databaseSizeBeforeUpdate);
        Secretaire testSecretaire = secretaires.get(secretaires.size() - 1);
        assertThat(testSecretaire.getNom_secretaire()).isEqualTo(UPDATED_NOM_SECRETAIRE);
        assertThat(testSecretaire.getPrenom_secretaire()).isEqualTo(UPDATED_PRENOM_SECRETAIRE);
        assertThat(testSecretaire.getTel_secretaire()).isEqualTo(UPDATED_TEL_SECRETAIRE);
        assertThat(testSecretaire.getAdresse_secretaire()).isEqualTo(UPDATED_ADRESSE_SECRETAIRE);
    }

    @Test
    @Transactional
    public void deleteSecretaire() throws Exception {
        // Initialize the database
        secretaireRepository.saveAndFlush(secretaire);

		int databaseSizeBeforeDelete = secretaireRepository.findAll().size();

        // Get the secretaire
        restSecretaireMockMvc.perform(delete("/api/secretaires/{id}", secretaire.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Secretaire> secretaires = secretaireRepository.findAll();
        assertThat(secretaires).hasSize(databaseSizeBeforeDelete - 1);
    }
}
