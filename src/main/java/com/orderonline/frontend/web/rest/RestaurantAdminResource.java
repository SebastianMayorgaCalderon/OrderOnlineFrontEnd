package com.orderonline.frontend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.orderonline.frontend.service.RestaurantAdminService;
import com.orderonline.frontend.web.rest.errors.BadRequestAlertException;
import com.orderonline.frontend.web.rest.util.HeaderUtil;
import com.orderonline.frontend.web.rest.util.PaginationUtil;
import com.orderonline.frontend.service.dto.RestaurantAdminDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RestaurantAdmin.
 */
@RestController
@RequestMapping("/api")
public class RestaurantAdminResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantAdminResource.class);

    private static final String ENTITY_NAME = "restaurantAdmin";

    private final RestaurantAdminService restaurantAdminService;

    public RestaurantAdminResource(RestaurantAdminService restaurantAdminService) {
        this.restaurantAdminService = restaurantAdminService;
    }

    /**
     * POST  /restaurant-admins : Create a new restaurantAdmin.
     *
     * @param restaurantAdminDTO the restaurantAdminDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restaurantAdminDTO, or with status 400 (Bad Request) if the restaurantAdmin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restaurant-admins")
    @Timed
    public ResponseEntity<RestaurantAdminDTO> createRestaurantAdmin(@RequestBody RestaurantAdminDTO restaurantAdminDTO) throws URISyntaxException {
        log.debug("REST request to save RestaurantAdmin : {}", restaurantAdminDTO);
        if (restaurantAdminDTO.getId() != null) {
            throw new BadRequestAlertException("A new restaurantAdmin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestaurantAdminDTO result = restaurantAdminService.save(restaurantAdminDTO);
        return ResponseEntity.created(new URI("/api/restaurant-admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restaurant-admins : Updates an existing restaurantAdmin.
     *
     * @param restaurantAdminDTO the restaurantAdminDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restaurantAdminDTO,
     * or with status 400 (Bad Request) if the restaurantAdminDTO is not valid,
     * or with status 500 (Internal Server Error) if the restaurantAdminDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restaurant-admins")
    @Timed
    public ResponseEntity<RestaurantAdminDTO> updateRestaurantAdmin(@RequestBody RestaurantAdminDTO restaurantAdminDTO) throws URISyntaxException {
        log.debug("REST request to update RestaurantAdmin : {}", restaurantAdminDTO);
        if (restaurantAdminDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RestaurantAdminDTO result = restaurantAdminService.save(restaurantAdminDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restaurantAdminDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restaurant-admins : get all the restaurantAdmins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of restaurantAdmins in body
     */
    @GetMapping("/restaurant-admins")
    @Timed
    public ResponseEntity<List<RestaurantAdminDTO>> getAllRestaurantAdmins(Pageable pageable) {
        log.debug("REST request to get a page of RestaurantAdmins");
        Page<RestaurantAdminDTO> page = restaurantAdminService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/restaurant-admins");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /restaurant-admins/:id : get the "id" restaurantAdmin.
     *
     * @param id the id of the restaurantAdminDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restaurantAdminDTO, or with status 404 (Not Found)
     */
    @GetMapping("/restaurant-admins/{id}")
    @Timed
    public ResponseEntity<RestaurantAdminDTO> getRestaurantAdmin(@PathVariable Long id) {
        log.debug("REST request to get RestaurantAdmin : {}", id);
        Optional<RestaurantAdminDTO> restaurantAdminDTO = restaurantAdminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restaurantAdminDTO);
    }

    /**
     * DELETE  /restaurant-admins/:id : delete the "id" restaurantAdmin.
     *
     * @param id the id of the restaurantAdminDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restaurant-admins/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestaurantAdmin(@PathVariable Long id) {
        log.debug("REST request to delete RestaurantAdmin : {}", id);
        restaurantAdminService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
