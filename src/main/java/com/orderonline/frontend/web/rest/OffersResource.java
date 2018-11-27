package com.orderonline.frontend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.orderonline.frontend.service.OffersService;
import com.orderonline.frontend.web.rest.errors.BadRequestAlertException;
import com.orderonline.frontend.web.rest.util.HeaderUtil;
import com.orderonline.frontend.web.rest.util.PaginationUtil;
import com.orderonline.frontend.service.dto.OffersDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Offers.
 */
@RestController
@RequestMapping("/api")
public class OffersResource {

    private final Logger log = LoggerFactory.getLogger(OffersResource.class);

    private static final String ENTITY_NAME = "offers";

    private final OffersService offersService;

    public OffersResource(OffersService offersService) {
        this.offersService = offersService;
    }

    /**
     * POST  /offers : Create a new offers.
     *
     * @param offersDTO the offersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offersDTO, or with status 400 (Bad Request) if the offers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offers")
    @Timed
    public ResponseEntity<OffersDTO> createOffers(@Valid @RequestBody OffersDTO offersDTO) throws URISyntaxException {
        log.debug("REST request to save Offers : {}", offersDTO);
        if (offersDTO.getId() != null) {
            throw new BadRequestAlertException("A new offers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OffersDTO result = offersService.save(offersDTO);
        return ResponseEntity.created(new URI("/api/offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offers : Updates an existing offers.
     *
     * @param offersDTO the offersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offersDTO,
     * or with status 400 (Bad Request) if the offersDTO is not valid,
     * or with status 500 (Internal Server Error) if the offersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offers")
    @Timed
    public ResponseEntity<OffersDTO> updateOffers(@Valid @RequestBody OffersDTO offersDTO) throws URISyntaxException {
        log.debug("REST request to update Offers : {}", offersDTO);
        if (offersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OffersDTO result = offersService.save(offersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offers : get all the offers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offers in body
     */
    @GetMapping("/offers")
    @Timed
    public ResponseEntity<List<OffersDTO>> getAllOffers(Pageable pageable) {
        log.debug("REST request to get a page of Offers");
        Page<OffersDTO> page = offersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /offers/:id : get the "id" offers.
     *
     * @param id the id of the offersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/offers/{id}")
    @Timed
    public ResponseEntity<OffersDTO> getOffers(@PathVariable Long id) {
        log.debug("REST request to get Offers : {}", id);
        Optional<OffersDTO> offersDTO = offersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offersDTO);
    }

    /**
     * DELETE  /offers/:id : delete the "id" offers.
     *
     * @param id the id of the offersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteOffers(@PathVariable Long id) {
        log.debug("REST request to delete Offers : {}", id);
        offersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
