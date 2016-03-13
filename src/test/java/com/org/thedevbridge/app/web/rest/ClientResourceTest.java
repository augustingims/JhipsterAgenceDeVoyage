package com.org.thedevbridge.app.web.rest;

import com.org.thedevbridge.app.Application;
import com.org.thedevbridge.app.domain.Client;
import com.org.thedevbridge.app.repository.ClientRepository;

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
 * Test class for the ClientResource REST controller.
 *
 * @see ClientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClientResourceTest {


    private static final Long DEFAULT_NUM_CNI = 1L;
    private static final Long UPDATED_NUM_CNI = 2L;
    private static final String DEFAULT_NOM_CLIENT = "AAAAA";
    private static final String UPDATED_NOM_CLIENT = "BBBBB";
    private static final String DEFAULT_PRENOM_CLIENT = "AAAAA";
    private static final String UPDATED_PRENOM_CLIENT = "BBBBB";
    private static final String DEFAULT_PROFESSION = "AAAAA";
    private static final String UPDATED_PROFESSION = "BBBBB";

    private static final String DEFAULT_DATE_DELIVRANCE = "AAAAA";
    private static final String UPDATED_DATE_DELIVRANCE = "BBBBB";

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClientMockMvc;

    private Client client;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientResource clientResource = new ClientResource();
        ReflectionTestUtils.setField(clientResource, "clientRepository", clientRepository);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        client = new Client();
        client.setNum_cni(DEFAULT_NUM_CNI);
        client.setNom_client(DEFAULT_NOM_CLIENT);
        client.setPrenom_client(DEFAULT_PRENOM_CLIENT);
        client.setProfession(DEFAULT_PROFESSION);
        client.setDate_delivrance(DEFAULT_DATE_DELIVRANCE);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client

        restClientMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(client)))
                .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clients.get(clients.size() - 1);
        assertThat(testClient.getNum_cni()).isEqualTo(DEFAULT_NUM_CNI);
        assertThat(testClient.getNom_client()).isEqualTo(DEFAULT_NOM_CLIENT);
        assertThat(testClient.getPrenom_client()).isEqualTo(DEFAULT_PRENOM_CLIENT);
        assertThat(testClient.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testClient.getDate_delivrance()).isEqualTo(DEFAULT_DATE_DELIVRANCE);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clients
        restClientMockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
                .andExpect(jsonPath("$.[*].num_cni").value(hasItem(DEFAULT_NUM_CNI.intValue())))
                .andExpect(jsonPath("$.[*].nom_client").value(hasItem(DEFAULT_NOM_CLIENT.toString())))
                .andExpect(jsonPath("$.[*].prenom_client").value(hasItem(DEFAULT_PRENOM_CLIENT.toString())))
                .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
                .andExpect(jsonPath("$.[*].date_delivrance").value(hasItem(DEFAULT_DATE_DELIVRANCE.toString())));
    }

    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.num_cni").value(DEFAULT_NUM_CNI.intValue()))
            .andExpect(jsonPath("$.nom_client").value(DEFAULT_NOM_CLIENT.toString()))
            .andExpect(jsonPath("$.prenom_client").value(DEFAULT_PRENOM_CLIENT.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.date_delivrance").value(DEFAULT_DATE_DELIVRANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

		int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        client.setNum_cni(UPDATED_NUM_CNI);
        client.setNom_client(UPDATED_NOM_CLIENT);
        client.setPrenom_client(UPDATED_PRENOM_CLIENT);
        client.setProfession(UPDATED_PROFESSION);
        client.setDate_delivrance(UPDATED_DATE_DELIVRANCE);

        restClientMockMvc.perform(put("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(client)))
                .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clients.get(clients.size() - 1);
        assertThat(testClient.getNum_cni()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testClient.getNom_client()).isEqualTo(UPDATED_NOM_CLIENT);
        assertThat(testClient.getPrenom_client()).isEqualTo(UPDATED_PRENOM_CLIENT);
        assertThat(testClient.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testClient.getDate_delivrance()).isEqualTo(UPDATED_DATE_DELIVRANCE);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

		int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Get the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeDelete - 1);
    }
}
