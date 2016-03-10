package com.org.thedevbridge.app.web.rest;

import com.org.thedevbridge.app.Application;
import com.org.thedevbridge.app.domain.Chauffeur;
import com.org.thedevbridge.app.repository.ChauffeurRepository;

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
 * Test class for the ChauffeurResource REST controller.
 *
 * @see ChauffeurResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ChauffeurResourceTest {

    private static final String DEFAULT_NOM_CHAUFFEUR = "AAAAA";
    private static final String UPDATED_NOM_CHAUFFEUR = "BBBBB";
    private static final String DEFAULT_PRENOM_CHAUFFEUR = "AAAAA";
    private static final String UPDATED_PRENOM_CHAUFFEUR = "BBBBB";

    private static final Long DEFAULT_TEL_CHAUFFEUR = 1L;
    private static final Long UPDATED_TEL_CHAUFFEUR = 2L;
    private static final String DEFAULT_ADRESSE_CHAUFFEUR = "AAAAA";
    private static final String UPDATED_ADRESSE_CHAUFFEUR = "BBBBB";

    @Inject
    private ChauffeurRepository chauffeurRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restChauffeurMockMvc;

    private Chauffeur chauffeur;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChauffeurResource chauffeurResource = new ChauffeurResource();
        ReflectionTestUtils.setField(chauffeurResource, "chauffeurRepository", chauffeurRepository);
        this.restChauffeurMockMvc = MockMvcBuilders.standaloneSetup(chauffeurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        chauffeur = new Chauffeur();
        chauffeur.setNom_chauffeur(DEFAULT_NOM_CHAUFFEUR);
        chauffeur.setPrenom_chauffeur(DEFAULT_PRENOM_CHAUFFEUR);
        chauffeur.setTel_chauffeur(DEFAULT_TEL_CHAUFFEUR);
        chauffeur.setAdresse_chauffeur(DEFAULT_ADRESSE_CHAUFFEUR);
    }

    @Test
    @Transactional
    public void createChauffeur() throws Exception {
        int databaseSizeBeforeCreate = chauffeurRepository.findAll().size();

        // Create the Chauffeur

        restChauffeurMockMvc.perform(post("/api/chauffeurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
                .andExpect(status().isCreated());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurs = chauffeurRepository.findAll();
        assertThat(chauffeurs).hasSize(databaseSizeBeforeCreate + 1);
        Chauffeur testChauffeur = chauffeurs.get(chauffeurs.size() - 1);
        assertThat(testChauffeur.getNom_chauffeur()).isEqualTo(DEFAULT_NOM_CHAUFFEUR);
        assertThat(testChauffeur.getPrenom_chauffeur()).isEqualTo(DEFAULT_PRENOM_CHAUFFEUR);
        assertThat(testChauffeur.getTel_chauffeur()).isEqualTo(DEFAULT_TEL_CHAUFFEUR);
        assertThat(testChauffeur.getAdresse_chauffeur()).isEqualTo(DEFAULT_ADRESSE_CHAUFFEUR);
    }

    @Test
    @Transactional
    public void getAllChauffeurs() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get all the chauffeurs
        restChauffeurMockMvc.perform(get("/api/chauffeurs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(chauffeur.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom_chauffeur").value(hasItem(DEFAULT_NOM_CHAUFFEUR.toString())))
                .andExpect(jsonPath("$.[*].prenom_chauffeur").value(hasItem(DEFAULT_PRENOM_CHAUFFEUR.toString())))
                .andExpect(jsonPath("$.[*].tel_chauffeur").value(hasItem(DEFAULT_TEL_CHAUFFEUR.intValue())))
                .andExpect(jsonPath("$.[*].adresse_chauffeur").value(hasItem(DEFAULT_ADRESSE_CHAUFFEUR.toString())));
    }

    @Test
    @Transactional
    public void getChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

        // Get the chauffeur
        restChauffeurMockMvc.perform(get("/api/chauffeurs/{id}", chauffeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(chauffeur.getId().intValue()))
            .andExpect(jsonPath("$.nom_chauffeur").value(DEFAULT_NOM_CHAUFFEUR.toString()))
            .andExpect(jsonPath("$.prenom_chauffeur").value(DEFAULT_PRENOM_CHAUFFEUR.toString()))
            .andExpect(jsonPath("$.tel_chauffeur").value(DEFAULT_TEL_CHAUFFEUR.intValue()))
            .andExpect(jsonPath("$.adresse_chauffeur").value(DEFAULT_ADRESSE_CHAUFFEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChauffeur() throws Exception {
        // Get the chauffeur
        restChauffeurMockMvc.perform(get("/api/chauffeurs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

		int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Update the chauffeur
        chauffeur.setNom_chauffeur(UPDATED_NOM_CHAUFFEUR);
        chauffeur.setPrenom_chauffeur(UPDATED_PRENOM_CHAUFFEUR);
        chauffeur.setTel_chauffeur(UPDATED_TEL_CHAUFFEUR);
        chauffeur.setAdresse_chauffeur(UPDATED_ADRESSE_CHAUFFEUR);

        restChauffeurMockMvc.perform(put("/api/chauffeurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chauffeur)))
                .andExpect(status().isOk());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurs = chauffeurRepository.findAll();
        assertThat(chauffeurs).hasSize(databaseSizeBeforeUpdate);
        Chauffeur testChauffeur = chauffeurs.get(chauffeurs.size() - 1);
        assertThat(testChauffeur.getNom_chauffeur()).isEqualTo(UPDATED_NOM_CHAUFFEUR);
        assertThat(testChauffeur.getPrenom_chauffeur()).isEqualTo(UPDATED_PRENOM_CHAUFFEUR);
        assertThat(testChauffeur.getTel_chauffeur()).isEqualTo(UPDATED_TEL_CHAUFFEUR);
        assertThat(testChauffeur.getAdresse_chauffeur()).isEqualTo(UPDATED_ADRESSE_CHAUFFEUR);
    }

    @Test
    @Transactional
    public void deleteChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.saveAndFlush(chauffeur);

		int databaseSizeBeforeDelete = chauffeurRepository.findAll().size();

        // Get the chauffeur
        restChauffeurMockMvc.perform(delete("/api/chauffeurs/{id}", chauffeur.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Chauffeur> chauffeurs = chauffeurRepository.findAll();
        assertThat(chauffeurs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
