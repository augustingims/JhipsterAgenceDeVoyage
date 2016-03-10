package com.org.thedevbridge.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.thedevbridge.app.domain.Secretaire;
import com.org.thedevbridge.app.repository.SecretaireRepository;
import com.org.thedevbridge.app.web.rest.util.HeaderUtil;
import com.org.thedevbridge.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Secretaire.
 */
@RestController
@RequestMapping("/api")
public class SecretaireResource {

    private final Logger log = LoggerFactory.getLogger(SecretaireResource.class);

    @Inject
    private SecretaireRepository secretaireRepository;

    /**
     * POST  /secretaires -> Create a new secretaire.
     */
    @RequestMapping(value = "/secretaires",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Secretaire> createSecretaire(@RequestBody Secretaire secretaire) throws URISyntaxException {
        log.debug("REST request to save Secretaire : {}", secretaire);
        if (secretaire.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new secretaire cannot already have an ID").body(null);
        }
        Secretaire result = secretaireRepository.save(secretaire);
        return ResponseEntity.created(new URI("/api/secretaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("secretaire", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /secretaires -> Updates an existing secretaire.
     */
    @RequestMapping(value = "/secretaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Secretaire> updateSecretaire(@RequestBody Secretaire secretaire) throws URISyntaxException {
        log.debug("REST request to update Secretaire : {}", secretaire);
        if (secretaire.getId() == null) {
            return createSecretaire(secretaire);
        }
        Secretaire result = secretaireRepository.save(secretaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("secretaire", secretaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /secretaires -> get all the secretaires.
     */
    @RequestMapping(value = "/secretaires",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Secretaire>> getAllSecretaires(Pageable pageable)
        throws URISyntaxException {
        Page<Secretaire> page = secretaireRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/secretaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /secretaires/:id -> get the "id" secretaire.
     */
    @RequestMapping(value = "/secretaires/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Secretaire> getSecretaire(@PathVariable Long id) {
        log.debug("REST request to get Secretaire : {}", id);
        return Optional.ofNullable(secretaireRepository.findOne(id))
            .map(secretaire -> new ResponseEntity<>(
                secretaire,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /secretaires/:id -> delete the "id" secretaire.
     */
    @RequestMapping(value = "/secretaires/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSecretaire(@PathVariable Long id) {
        log.debug("REST request to delete Secretaire : {}", id);
        secretaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("secretaire", id.toString())).build();
    }
}
