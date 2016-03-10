package com.org.thedevbridge.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.thedevbridge.app.domain.Chauffeur;
import com.org.thedevbridge.app.repository.ChauffeurRepository;
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
 * REST controller for managing Chauffeur.
 */
@RestController
@RequestMapping("/api")
public class ChauffeurResource {

    private final Logger log = LoggerFactory.getLogger(ChauffeurResource.class);

    @Inject
    private ChauffeurRepository chauffeurRepository;

    /**
     * POST  /chauffeurs -> Create a new chauffeur.
     */
    @RequestMapping(value = "/chauffeurs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Chauffeur> createChauffeur(@RequestBody Chauffeur chauffeur) throws URISyntaxException {
        log.debug("REST request to save Chauffeur : {}", chauffeur);
        if (chauffeur.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new chauffeur cannot already have an ID").body(null);
        }
        Chauffeur result = chauffeurRepository.save(chauffeur);
        return ResponseEntity.created(new URI("/api/chauffeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chauffeur", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chauffeurs -> Updates an existing chauffeur.
     */
    @RequestMapping(value = "/chauffeurs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Chauffeur> updateChauffeur(@RequestBody Chauffeur chauffeur) throws URISyntaxException {
        log.debug("REST request to update Chauffeur : {}", chauffeur);
        if (chauffeur.getId() == null) {
            return createChauffeur(chauffeur);
        }
        Chauffeur result = chauffeurRepository.save(chauffeur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chauffeur", chauffeur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chauffeurs -> get all the chauffeurs.
     */
    @RequestMapping(value = "/chauffeurs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Chauffeur>> getAllChauffeurs(Pageable pageable)
        throws URISyntaxException {
        Page<Chauffeur> page = chauffeurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chauffeurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /chauffeur -> get all the chauffeur.
     */
    @RequestMapping(value = "/getAllChauffeur",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Chauffeur> getAll() {
        List<Chauffeur> page = chauffeurRepository.findAll();
        return page;
    }
    /**
     * GET  /chauffeurs/:id -> get the "id" chauffeur.
     */
    @RequestMapping(value = "/chauffeurs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Chauffeur> getChauffeur(@PathVariable Long id) {
        log.debug("REST request to get Chauffeur : {}", id);
        return Optional.ofNullable(chauffeurRepository.findOne(id))
            .map(chauffeur -> new ResponseEntity<>(
                chauffeur,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chauffeurs/:id -> delete the "id" chauffeur.
     */
    @RequestMapping(value = "/chauffeurs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteChauffeur(@PathVariable Long id) {
        log.debug("REST request to delete Chauffeur : {}", id);
        chauffeurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chauffeur", id.toString())).build();
    }
}
