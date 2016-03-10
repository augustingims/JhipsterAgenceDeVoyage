package com.org.thedevbridge.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.thedevbridge.app.domain.Imprime;
import com.org.thedevbridge.app.repository.ImprimeRepository;
import com.org.thedevbridge.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Ticket.
 */
@RestController
@RequestMapping("/api")
public class ImprimeResource {

    private final Logger log = LoggerFactory.getLogger(ImprimeResource.class);

    @Inject
    private ImprimeRepository imprimeRepository;

    /**
     * POST  /tickets -> Create a new ticket.
     */
    @RequestMapping(value = "/imprime",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Imprime> createImprime(@RequestBody Imprime imprime) throws URISyntaxException {
        log.debug("REST request to save imprime : {}", imprime);
        if (imprime.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new imprime cannot already have an ID").body(null);
        }
        Imprime result = imprimeRepository.save(imprime);
        return ResponseEntity.created(new URI("/api/imprime/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("imprime", result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imprime -> get all the tickets.
     */
    @RequestMapping(value = "/imprime",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Imprime> getAllTickets() {
        List<Imprime> page = imprimeRepository.findAll();
        return page;
    }

    /**
     *
     */
    @RequestMapping(value = "/imprime/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Imprime> getImprime(@PathVariable Long id) {
        return Optional.ofNullable(imprimeRepository.findOne(id))
            .map(imprime -> new ResponseEntity<>(
                imprime,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
