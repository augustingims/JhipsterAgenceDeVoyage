package com.org.thedevbridge.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.thedevbridge.app.domain.Responsable;
import com.org.thedevbridge.app.repository.ResponsableRepository;
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
 * REST controller for managing Responsable.
 */
@RestController
@RequestMapping("/api")
public class ResponsableResource {

    private final Logger log = LoggerFactory.getLogger(ResponsableResource.class);

    @Inject
    private ResponsableRepository responsableRepository;

    /**
     * POST  /responsables -> Create a new responsable.
     */
    @RequestMapping(value = "/responsables",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Responsable> createResponsable(@RequestBody Responsable responsable) throws URISyntaxException {
        log.debug("REST request to save Responsable : {}", responsable);
        if (responsable.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new responsable cannot already have an ID").body(null);
        }
        Responsable result = responsableRepository.save(responsable);
        return ResponseEntity.created(new URI("/api/responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("responsable", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /responsables -> Updates an existing responsable.
     */
    @RequestMapping(value = "/responsables",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Responsable> updateResponsable(@RequestBody Responsable responsable) throws URISyntaxException {
        log.debug("REST request to update Responsable : {}", responsable);
        if (responsable.getId() == null) {
            return createResponsable(responsable);
        }
        Responsable result = responsableRepository.save(responsable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("responsable", responsable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /responsables -> get all the responsables.
     */
    @RequestMapping(value = "/responsables",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Responsable>> getAllResponsables(Pageable pageable)
        throws URISyntaxException {
        Page<Responsable> page = responsableRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/responsables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /responsables/:id -> get the "id" responsable.
     */
    @RequestMapping(value = "/responsables/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Responsable> getResponsable(@PathVariable Long id) {
        log.debug("REST request to get Responsable : {}", id);
        return Optional.ofNullable(responsableRepository.findOne(id))
            .map(responsable -> new ResponseEntity<>(
                responsable,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /responsables/:id -> delete the "id" responsable.
     */
    @RequestMapping(value = "/responsables/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResponsable(@PathVariable Long id) {
        log.debug("REST request to delete Responsable : {}", id);
        responsableRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("responsable", id.toString())).build();
    }
}
