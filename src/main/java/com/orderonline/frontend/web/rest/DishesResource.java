package com.orderonline.frontend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.orderonline.frontend.service.DishesService;
import com.orderonline.frontend.web.rest.errors.BadRequestAlertException;
import com.orderonline.frontend.web.rest.util.HeaderUtil;
import com.orderonline.frontend.web.rest.util.PaginationUtil;
import com.orderonline.frontend.service.dto.DishesDTO;
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
 * REST controller for managing Dishes.
 */
@RestController
@RequestMapping("/api")
public class DishesResource {

    private final Logger log = LoggerFactory.getLogger(DishesResource.class);

    private static final String ENTITY_NAME = "dishes";

    private final DishesService dishesService;

    public DishesResource(DishesService dishesService) {
        this.dishesService = dishesService;
    }

    /**
     * POST  /dishes : Create a new dishes.
     *
     * @param dishesDTO the dishesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dishesDTO, or with status 400 (Bad Request) if the dishes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dishes")
    @Timed
    public ResponseEntity<DishesDTO> createDishes(@Valid @RequestBody DishesDTO dishesDTO) throws URISyntaxException {
        log.debug("REST request to save Dishes : {}", dishesDTO);
        if (dishesDTO.getId() != null) {
            throw new BadRequestAlertException("A new dishes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DishesDTO result = dishesService.save(dishesDTO);
        return ResponseEntity.created(new URI("/api/dishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dishes : Updates an existing dishes.
     *
     * @param dishesDTO the dishesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dishesDTO,
     * or with status 400 (Bad Request) if the dishesDTO is not valid,
     * or with status 500 (Internal Server Error) if the dishesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dishes")
    @Timed
    public ResponseEntity<DishesDTO> updateDishes(@Valid @RequestBody DishesDTO dishesDTO) throws URISyntaxException {
        log.debug("REST request to update Dishes : {}", dishesDTO);
        if (dishesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DishesDTO result = dishesService.save(dishesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dishesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dishes : get all the dishes.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of dishes in body
     */
    @GetMapping("/dishes")
    @Timed
    public ResponseEntity<List<DishesDTO>> getAllDishes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Dishes");
        Page<DishesDTO> page;
        if (eagerload) {
            page = dishesService.findAllWithEagerRelationships(pageable);
        } else {
            page = dishesService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/dishes?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dishes/:id : get the "id" dishes.
     *
     * @param id the id of the dishesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dishesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dishes/{id}")
    @Timed
    public ResponseEntity<DishesDTO> getDishes(@PathVariable Long id) {
        log.debug("REST request to get Dishes : {}", id);
        Optional<DishesDTO> dishesDTO = dishesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dishesDTO);
    }

    /**
     * DELETE  /dishes/:id : delete the "id" dishes.
     *
     * @param id the id of the dishesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dishes/{id}")
    @Timed
    public ResponseEntity<Void> deleteDishes(@PathVariable Long id) {
        log.debug("REST request to delete Dishes : {}", id);
        dishesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
