package com.org.thedevbridge.app.web.rest;

import com.org.thedevbridge.app.Application;
import com.org.thedevbridge.app.domain.Responsable;
import com.org.thedevbridge.app.repository.ResponsableRepository;

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
 * Test class for the ResponsableResource REST controller.
 *
 * @see ResponsableResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResponsableResourceTest {

    private static final String DEFAULT_NOM_RESPONSABLE = "AAAAA";
    private static final String UPDATED_NOM_RESPONSABLE = "BBBBB";
    private static final String DEFAULT_PRENOM_RESPONSABLE = "AAAAA";
    private static final String UPDATED_PRENOM_RESPONSABLE = "BBBBB";

    private static final Long DEFAULT_TEL_RESPONSABLE = 1L;
    private static final Long UPDATED_TEL_RESPONSABLE = 2L;
    private static final String DEFAULT_ADRESSE_RESPONSABLE = "AAAAA";
    private static final String UPDATED_ADRESSE_RESPONSABLE = "BBBBB";

    @Inject
    private ResponsableRepository responsableRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResponsableMockMvc;

    private Responsable responsable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResponsableResource responsableResource = new ResponsableResource();
        ReflectionTestUtils.setField(responsableResource, "responsableRepository", responsableRepository);
        this.restResponsableMockMvc = MockMvcBuilders.standaloneSetup(responsableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        responsable = new Responsable();
        responsable.setNom_responsable(DEFAULT_NOM_RESPONSABLE);
        responsable.setPrenom_responsable(DEFAULT_PRENOM_RESPONSABLE);
        responsable.setTel_responsable(DEFAULT_TEL_RESPONSABLE);
        responsable.setAdresse_responsable(DEFAULT_ADRESSE_RESPONSABLE);
    }

    @Test
    @Transactional
    public void createResponsable() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // Create the Responsable

        restResponsableMockMvc.perform(post("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isCreated());

        // Validate the Responsable in the database
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeCreate + 1);
        Responsable testResponsable = responsables.get(responsables.size() - 1);
        assertThat(testResponsable.getNom_responsable()).isEqualTo(DEFAULT_NOM_RESPONSABLE);
        assertThat(testResponsable.getPrenom_responsable()).isEqualTo(DEFAULT_PRENOM_RESPONSABLE);
        assertThat(testResponsable.getTel_responsable()).isEqualTo(DEFAULT_TEL_RESPONSABLE);
        assertThat(testResponsable.getAdresse_responsable()).isEqualTo(DEFAULT_ADRESSE_RESPONSABLE);
    }

    @Test
    @Transactional
    public void getAllResponsables() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsables
        restResponsableMockMvc.perform(get("/api/responsables"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(responsable.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom_responsable").value(hasItem(DEFAULT_NOM_RESPONSABLE.toString())))
                .andExpect(jsonPath("$.[*].prenom_responsable").value(hasItem(DEFAULT_PRENOM_RESPONSABLE.toString())))
                .andExpect(jsonPath("$.[*].tel_responsable").value(hasItem(DEFAULT_TEL_RESPONSABLE.intValue())))
                .andExpect(jsonPath("$.[*].adresse_responsable").value(hasItem(DEFAULT_ADRESSE_RESPONSABLE.toString())));
    }

    @Test
    @Transactional
    public void getResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", responsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(responsable.getId().intValue()))
            .andExpect(jsonPath("$.nom_responsable").value(DEFAULT_NOM_RESPONSABLE.toString()))
            .andExpect(jsonPath("$.prenom_responsable").value(DEFAULT_PRENOM_RESPONSABLE.toString()))
            .andExpect(jsonPath("$.tel_responsable").value(DEFAULT_TEL_RESPONSABLE.intValue()))
            .andExpect(jsonPath("$.adresse_responsable").value(DEFAULT_ADRESSE_RESPONSABLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsable() throws Exception {
        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

		int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable
        responsable.setNom_responsable(UPDATED_NOM_RESPONSABLE);
        responsable.setPrenom_responsable(UPDATED_PRENOM_RESPONSABLE);
        responsable.setTel_responsable(UPDATED_TEL_RESPONSABLE);
        responsable.setAdresse_responsable(UPDATED_ADRESSE_RESPONSABLE);

        restResponsableMockMvc.perform(put("/api/responsables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(responsable)))
                .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsables.get(responsables.size() - 1);
        assertThat(testResponsable.getNom_responsable()).isEqualTo(UPDATED_NOM_RESPONSABLE);
        assertThat(testResponsable.getPrenom_responsable()).isEqualTo(UPDATED_PRENOM_RESPONSABLE);
        assertThat(testResponsable.getTel_responsable()).isEqualTo(UPDATED_TEL_RESPONSABLE);
        assertThat(testResponsable.getAdresse_responsable()).isEqualTo(UPDATED_ADRESSE_RESPONSABLE);
    }

    @Test
    @Transactional
    public void deleteResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

		int databaseSizeBeforeDelete = responsableRepository.findAll().size();

        // Get the responsable
        restResponsableMockMvc.perform(delete("/api/responsables/{id}", responsable.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Responsable> responsables = responsableRepository.findAll();
        assertThat(responsables).hasSize(databaseSizeBeforeDelete - 1);
    }
}
