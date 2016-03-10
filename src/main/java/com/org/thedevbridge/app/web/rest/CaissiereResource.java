package com.org.thedevbridge.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.thedevbridge.app.domain.Caissiere;
import com.org.thedevbridge.app.repository.CaissiereRepository;
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
 * REST controller for managing Caissiere.
 */
@RestController
@RequestMapping("/api")
public class CaissiereResource {

    private final Logger log = LoggerFactory.getLogger(CaissiereResource.class);

    @Inject
    private CaissiereRepository caissiereRepository;

    /**
     * POST  /caissieres -> Create a new caissiere.
     */
    @RequestMapping(value = "/caissieres",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Caissiere> createCaissiere(@RequestBody Caissiere caissiere) throws URISyntaxException {
        log.debug("REST request to save Caissiere : {}", caissiere);
        if (caissiere.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new caissiere cannot already have an ID").body(null);
        }
        Caissiere result = caissiereRepository.save(caissiere);
        return ResponseEntity.created(new URI("/api/caissieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("caissiere", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caissieres -> Updates an existing caissiere.
     */
    @RequestMapping(value = "/caissieres",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Caissiere> updateCaissiere(@RequestBody Caissiere caissiere) throws URISyntaxException {
        log.debug("REST request to update Caissiere : {}", caissiere);
        if (caissiere.getId() == null) {
            return createCaissiere(caissiere);
        }
        Caissiere result = caissiereRepository.save(caissiere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("caissiere", caissiere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caissieres -> get all the caissieres.
     */
    @RequestMapping(value = "/caissieres",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Caissiere>> getAllCaissieres(Pageable pageable)
        throws URISyntaxException {
        Page<Caissiere> page = caissiereRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caissieres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /caissieres/:id -> get the "id" caissiere.
     */
    @RequestMapping(value = "/caissieres/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Caissiere> getCaissiere(@PathVariable Long id) {
        log.debug("REST request to get Caissiere : {}", id);
        return Optional.ofNullable(caissiereRepository.findOne(id))
            .map(caissiere -> new ResponseEntity<>(
                caissiere,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /caissieres/:id -> delete the "id" caissiere.
     */
    @RequestMapping(value = "/caissieres/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCaissiere(@PathVariable Long id) {
        log.debug("REST request to delete Caissiere : {}", id);
        caissiereRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("caissiere", id.toString())).build();
    }
}
