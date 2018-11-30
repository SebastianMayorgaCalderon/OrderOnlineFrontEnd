package com.orderonline.frontend.service;

import com.orderonline.frontend.domain.Offers;
import com.orderonline.frontend.repository.OffersRepository;
import com.orderonline.frontend.service.dto.OffersDTO;
import com.orderonline.frontend.service.mapper.OffersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Offers.
 */
@Service
@Transactional
public class OffersService {

    private final Logger log = LoggerFactory.getLogger(OffersService.class);

    private final OffersRepository offersRepository;

    private final OffersMapper offersMapper;

    public OffersService(OffersRepository offersRepository, OffersMapper offersMapper) {
        this.offersRepository = offersRepository;
        this.offersMapper = offersMapper;
    }

    /**
     * Save a offers.
     *
     * @param offersDTO the entity to save
     * @return the persisted entity
     */
    public OffersDTO save(OffersDTO offersDTO) {
        log.debug("Request to save Offers : {}", offersDTO);

        Offers offers = offersMapper.toEntity(offersDTO);
        offers = offersRepository.save(offers);
        return offersMapper.toDto(offers);
    }

    /**
     * Get all the offers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OffersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        return offersRepository.findAll(pageable)
            .map(offersMapper::toDto);
    }


    /**
     * Get one offers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OffersDTO> findOne(Long id) {
        log.debug("Request to get Offers : {}", id);
        return offersRepository.findById(id)
            .map(offersMapper::toDto);
    }

    /**
     * Delete the offers by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Offers : {}", id);
        offersRepository.deleteById(id);
    }
}
