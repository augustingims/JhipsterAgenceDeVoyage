package com.org.thedevbridge.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.thedevbridge.app.domain.Voyage;
import com.org.thedevbridge.app.repository.VoyageRepository;
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
 * REST controller for managing Voyage.
 */
@RestController
@RequestMapping("/api")
public class VoyageResource {

    private final Logger log = LoggerFactory.getLogger(VoyageResource.class);

    @Inject
    private VoyageRepository voyageRepository;

    /**
     * POST  /voyages -> Create a new voyage.
     */
    @RequestMapping(value = "/voyages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voyage> createVoyage(@RequestBody Voyage voyage) throws URISyntaxException {
        log.debug("REST request to save Voyage : {}", voyage);
        if (voyage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new voyage cannot already have an ID").body(null);
        }
        Voyage result = voyageRepository.save(voyage);
        return ResponseEntity.created(new URI("/api/voyages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("voyage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voyages -> Updates an existing voyage.
     */
    @RequestMapping(value = "/voyages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voyage> updateVoyage(@RequestBody Voyage voyage) throws URISyntaxException {
        log.debug("REST request to update Voyage : {}", voyage);
        if (voyage.getId() == null) {
            return createVoyage(voyage);
        }
        Voyage result = voyageRepository.save(voyage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("voyage", voyage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voyages -> get all the voyages.
     */
    @RequestMapping(value = "/voyages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Voyage>> getAllVoyages(Pageable pageable)
        throws URISyntaxException {
        Page<Voyage> page = voyageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/voyages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /voyages/:id -> get the "id" voyage.
     */
    @RequestMapping(value = "/voyages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voyage> getVoyage(@PathVariable Long id) {
        log.debug("REST request to get Voyage : {}", id);
        return Optional.ofNullable(voyageRepository.findOne(id))
            .map(voyage -> new ResponseEntity<>(
                voyage,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /voyages/:id -> delete the "id" voyage.
     */
    @RequestMapping(value = "/voyages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVoyage(@PathVariable Long id) {
        log.debug("REST request to delete Voyage : {}", id);
        voyageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("voyage", id.toString())).build();
    }
    /**
     *
     */
    @RequestMapping(value = "/getplace/{nombus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public long getPlace(@PathVariable String nombus) {
        long place = voyageRepository.countByNombusLike(nombus);
        return place;
    }
}
