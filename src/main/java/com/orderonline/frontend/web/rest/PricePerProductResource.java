package com.orderonline.frontend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.orderonline.frontend.service.PricePerProductService;
import com.orderonline.frontend.web.rest.errors.BadRequestAlertException;
import com.orderonline.frontend.web.rest.util.HeaderUtil;
import com.orderonline.frontend.web.rest.util.PaginationUtil;
import com.orderonline.frontend.service.dto.PricePerProductDTO;
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
 * REST controller for managing PricePerProduct.
 */
@RestController
@RequestMapping("/api")
public class PricePerProductResource {

    private final Logger log = LoggerFactory.getLogger(PricePerProductResource.class);

    private static final String ENTITY_NAME = "pricePerProduct";

    private final PricePerProductService pricePerProductService;

    public PricePerProductResource(PricePerProductService pricePerProductService) {
        this.pricePerProductService = pricePerProductService;
    }

    /**
     * POST  /price-per-products : Create a new pricePerProduct.
     *
     * @param pricePerProductDTO the pricePerProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pricePerProductDTO, or with status 400 (Bad Request) if the pricePerProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-per-products")
    @Timed
    public ResponseEntity<PricePerProductDTO> createPricePerProduct(@Valid @RequestBody PricePerProductDTO pricePerProductDTO) throws URISyntaxException {
        log.debug("REST request to save PricePerProduct : {}", pricePerProductDTO);
        if (pricePerProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new pricePerProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PricePerProductDTO result = pricePerProductService.save(pricePerProductDTO);
        return ResponseEntity.created(new URI("/api/price-per-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-per-products : Updates an existing pricePerProduct.
     *
     * @param pricePerProductDTO the pricePerProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pricePerProductDTO,
     * or with status 400 (Bad Request) if the pricePerProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the pricePerProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-per-products")
    @Timed
    public ResponseEntity<PricePerProductDTO> updatePricePerProduct(@Valid @RequestBody PricePerProductDTO pricePerProductDTO) throws URISyntaxException {
        log.debug("REST request to update PricePerProduct : {}", pricePerProductDTO);
        if (pricePerProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PricePerProductDTO result = pricePerProductService.save(pricePerProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pricePerProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-per-products : get all the pricePerProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pricePerProducts in body
     */
    @GetMapping("/price-per-products")
    @Timed
    public ResponseEntity<List<PricePerProductDTO>> getAllPricePerProducts(Pageable pageable) {
        log.debug("REST request to get a page of PricePerProducts");
        Page<PricePerProductDTO> page = pricePerProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/price-per-products");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /price-per-products/:id : get the "id" pricePerProduct.
     *
     * @param id the id of the pricePerProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pricePerProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/price-per-products/{id}")
    @Timed
    public ResponseEntity<PricePerProductDTO> getPricePerProduct(@PathVariable Long id) {
        log.debug("REST request to get PricePerProduct : {}", id);
        Optional<PricePerProductDTO> pricePerProductDTO = pricePerProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pricePerProductDTO);
    }

    /**
     * DELETE  /price-per-products/:id : delete the "id" pricePerProduct.
     *
     * @param id the id of the pricePerProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-per-products/{id}")
    @Timed
    public ResponseEntity<Void> deletePricePerProduct(@PathVariable Long id) {
        log.debug("REST request to delete PricePerProduct : {}", id);
        pricePerProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
