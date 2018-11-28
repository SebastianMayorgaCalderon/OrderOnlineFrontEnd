package com.orderonline.frontend.service;

import com.orderonline.frontend.domain.PricePerProduct;
import com.orderonline.frontend.repository.PricePerProductRepository;
import com.orderonline.frontend.service.dto.PricePerProductDTO;
import com.orderonline.frontend.service.mapper.PricePerProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PricePerProduct.
 */
@Service
@Transactional
public class PricePerProductService {

    private final Logger log = LoggerFactory.getLogger(PricePerProductService.class);

    private final PricePerProductRepository pricePerProductRepository;

    private final PricePerProductMapper pricePerProductMapper;

    public PricePerProductService(PricePerProductRepository pricePerProductRepository, PricePerProductMapper pricePerProductMapper) {
        this.pricePerProductRepository = pricePerProductRepository;
        this.pricePerProductMapper = pricePerProductMapper;
    }

    /**
     * Save a pricePerProduct.
     *
     * @param pricePerProductDTO the entity to save
     * @return the persisted entity
     */
    public PricePerProductDTO save(PricePerProductDTO pricePerProductDTO) {
        log.debug("Request to save PricePerProduct : {}", pricePerProductDTO);

        PricePerProduct pricePerProduct = pricePerProductMapper.toEntity(pricePerProductDTO);
        pricePerProduct = pricePerProductRepository.save(pricePerProduct);
        return pricePerProductMapper.toDto(pricePerProduct);
    }

    /**
     * Get all the pricePerProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PricePerProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PricePerProducts");
        return pricePerProductRepository.findAll(pageable)
            .map(pricePerProductMapper::toDto);
    }


    /**
     * Get one pricePerProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PricePerProductDTO> findOne(Long id) {
        log.debug("Request to get PricePerProduct : {}", id);
        return pricePerProductRepository.findById(id)
            .map(pricePerProductMapper::toDto);
    }

    /**
     * Delete the pricePerProduct by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PricePerProduct : {}", id);
        pricePerProductRepository.deleteById(id);
    }
}
