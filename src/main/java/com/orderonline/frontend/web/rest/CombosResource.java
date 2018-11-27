package com.orderonline.frontend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.orderonline.frontend.service.CombosService;
import com.orderonline.frontend.web.rest.errors.BadRequestAlertException;
import com.orderonline.frontend.web.rest.util.HeaderUtil;
import com.orderonline.frontend.web.rest.util.PaginationUtil;
import com.orderonline.frontend.service.dto.CombosDTO;
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
 * REST controller for managing Combos.
 */
@RestController
@RequestMapping("/api")
public class CombosResource {

    private final Logger log = LoggerFactory.getLogger(CombosResource.class);

    private static final String ENTITY_NAME = "combos";

    private final CombosService combosService;

    public CombosResource(CombosService combosService) {
        this.combosService = combosService;
    }

    /**
     * POST  /combos : Create a new combos.
     *
     * @param combosDTO the combosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new combosDTO, or with status 400 (Bad Request) if the combos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/combos")
    @Timed
    public ResponseEntity<CombosDTO> createCombos(@Valid @RequestBody CombosDTO combosDTO) throws URISyntaxException {
        log.debug("REST request to save Combos : {}", combosDTO);
        if (combosDTO.getId() != null) {
            throw new BadRequestAlertException("A new combos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CombosDTO result = combosService.save(combosDTO);
        return ResponseEntity.created(new URI("/api/combos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /combos : Updates an existing combos.
     *
     * @param combosDTO the combosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated combosDTO,
     * or with status 400 (Bad Request) if the combosDTO is not valid,
     * or with status 500 (Internal Server Error) if the combosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/combos")
    @Timed
    public ResponseEntity<CombosDTO> updateCombos(@Valid @RequestBody CombosDTO combosDTO) throws URISyntaxException {
        log.debug("REST request to update Combos : {}", combosDTO);
        if (combosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CombosDTO result = combosService.save(combosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, combosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /combos : get all the combos.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of combos in body
     */
    @GetMapping("/combos")
    @Timed
    public ResponseEntity<List<CombosDTO>> getAllCombos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Combos");
        Page<CombosDTO> page;
        if (eagerload) {
            page = combosService.findAllWithEagerRelationships(pageable);
        } else {
            page = combosService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/combos?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /combos/:id : get the "id" combos.
     *
     * @param id the id of the combosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the combosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/combos/{id}")
    @Timed
    public ResponseEntity<CombosDTO> getCombos(@PathVariable Long id) {
        log.debug("REST request to get Combos : {}", id);
        Optional<CombosDTO> combosDTO = combosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(combosDTO);
    }

    /**
     * DELETE  /combos/:id : delete the "id" combos.
     *
     * @param id the id of the combosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/combos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCombos(@PathVariable Long id) {
        log.debug("REST request to delete Combos : {}", id);
        combosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
