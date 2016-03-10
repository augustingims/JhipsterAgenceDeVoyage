package com.org.thedevbridge.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.thedevbridge.app.domain.Bus;
import com.org.thedevbridge.app.repository.BusRepository;
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
 * REST controller for managing Bus.
 */
@RestController
@RequestMapping("/api")
public class BusResource {

    private final Logger log = LoggerFactory.getLogger(BusResource.class);

    @Inject
    private BusRepository busRepository;

    /**
     * POST  /buss -> Create a new bus.
     */
    @RequestMapping(value = "/buss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) throws URISyntaxException {
        log.debug("REST request to save Bus : {}", bus);
        if (bus.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bus cannot already have an ID").body(null);
        }
        Bus result = busRepository.save(bus);
        return ResponseEntity.created(new URI("/api/buss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buss -> Updates an existing bus.
     */
    @RequestMapping(value = "/buss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bus> updateBus(@RequestBody Bus bus) throws URISyntaxException {
        log.debug("REST request to update Bus : {}", bus);
        if (bus.getId() == null) {
            return createBus(bus);
        }
        Bus result = busRepository.save(bus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bus", bus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buss -> get all the buss.
     */
    @RequestMapping(value = "/buss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bus>> getAllBuss(Pageable pageable)
        throws URISyntaxException {
        Page<Bus> page = busRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/buss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
   /**
     * GET  /bus -> get all the bus.
     */
    @RequestMapping(value = "/getAllBus",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Bus> getAll() {
        List<Bus> page = busRepository.findAll();
        return page;
    }

    /**
     * GET  /buss/:id -> get the "id" bus.
     */
    @RequestMapping(value = "/buss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        log.debug("REST request to get Bus : {}", id);
        return Optional.ofNullable(busRepository.findOne(id))
            .map(bus -> new ResponseEntity<>(
                bus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /buss/:id -> delete the "id" bus.
     */
    @RequestMapping(value = "/buss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        log.debug("REST request to delete Bus : {}", id);
        busRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bus", id.toString())).build();
    }
}
