package com.orderonline.frontend.web.rest;

import com.orderonline.frontend.OrderOnlineFrontEndApp;

import com.orderonline.frontend.domain.Offers;
import com.orderonline.frontend.repository.OffersRepository;
import com.orderonline.frontend.service.OffersService;
import com.orderonline.frontend.service.dto.OffersDTO;
import com.orderonline.frontend.service.mapper.OffersMapper;
import com.orderonline.frontend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.orderonline.frontend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OffersResource REST controller.
 *
 * @see OffersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderOnlineFrontEndApp.class)
public class OffersResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private OffersMapper offersMapper;

    @Autowired
    private OffersService offersService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOffersMockMvc;

    private Offers offers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OffersResource offersResource = new OffersResource(offersService);
        this.restOffersMockMvc = MockMvcBuilders.standaloneSetup(offersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offers createEntity(EntityManager em) {
        Offers offers = new Offers()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return offers;
    }

    @Before
    public void initTest() {
        offers = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffers() throws Exception {
        int databaseSizeBeforeCreate = offersRepository.findAll().size();

        // Create the Offers
        OffersDTO offersDTO = offersMapper.toDto(offers);
        restOffersMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offersDTO)))
            .andExpect(status().isCreated());

        // Validate the Offers in the database
        List<Offers> offersList = offersRepository.findAll();
        assertThat(offersList).hasSize(databaseSizeBeforeCreate + 1);
        Offers testOffers = offersList.get(offersList.size() - 1);
        assertThat(testOffers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffers.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOffers.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testOffers.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createOffersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offersRepository.findAll().size();

        // Create the Offers with an existing ID
        offers.setId(1L);
        OffersDTO offersDTO = offersMapper.toDto(offers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffersMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offers in the database
        List<Offers> offersList = offersRepository.findAll();
        assertThat(offersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = offersRepository.findAll().size();
        // set the field null
        offers.setName(null);

        // Create the Offers, which fails.
        OffersDTO offersDTO = offersMapper.toDto(offers);

        restOffersMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offersDTO)))
            .andExpect(status().isBadRequest());

        List<Offers> offersList = offersRepository.findAll();
        assertThat(offersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = offersRepository.findAll().size();
        // set the field null
        offers.setPrice(null);

        // Create the Offers, which fails.
        OffersDTO offersDTO = offersMapper.toDto(offers);

        restOffersMockMvc.perform(post("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offersDTO)))
            .andExpect(status().isBadRequest());

        List<Offers> offersList = offersRepository.findAll();
        assertThat(offersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffers() throws Exception {
        // Initialize the database
        offersRepository.saveAndFlush(offers);

        // Get all the offersList
        restOffersMockMvc.perform(get("/api/offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getOffers() throws Exception {
        // Initialize the database
        offersRepository.saveAndFlush(offers);

        // Get the offers
        restOffersMockMvc.perform(get("/api/offers/{id}", offers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingOffers() throws Exception {
        // Get the offers
        restOffersMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffers() throws Exception {
        // Initialize the database
        offersRepository.saveAndFlush(offers);

        int databaseSizeBeforeUpdate = offersRepository.findAll().size();

        // Update the offers
        Offers updatedOffers = offersRepository.findById(offers.getId()).get();
        // Disconnect from session so that the updates on updatedOffers are not directly saved in db
        em.detach(updatedOffers);
        updatedOffers
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        OffersDTO offersDTO = offersMapper.toDto(updatedOffers);

        restOffersMockMvc.perform(put("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offersDTO)))
            .andExpect(status().isOk());

        // Validate the Offers in the database
        List<Offers> offersList = offersRepository.findAll();
        assertThat(offersList).hasSize(databaseSizeBeforeUpdate);
        Offers testOffers = offersList.get(offersList.size() - 1);
        assertThat(testOffers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffers.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOffers.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testOffers.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOffers() throws Exception {
        int databaseSizeBeforeUpdate = offersRepository.findAll().size();

        // Create the Offers
        OffersDTO offersDTO = offersMapper.toDto(offers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffersMockMvc.perform(put("/api/offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offers in the database
        List<Offers> offersList = offersRepository.findAll();
        assertThat(offersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffers() throws Exception {
        // Initialize the database
        offersRepository.saveAndFlush(offers);

        int databaseSizeBeforeDelete = offersRepository.findAll().size();

        // Get the offers
        restOffersMockMvc.perform(delete("/api/offers/{id}", offers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Offers> offersList = offersRepository.findAll();
        assertThat(offersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offers.class);
        Offers offers1 = new Offers();
        offers1.setId(1L);
        Offers offers2 = new Offers();
        offers2.setId(offers1.getId());
        assertThat(offers1).isEqualTo(offers2);
        offers2.setId(2L);
        assertThat(offers1).isNotEqualTo(offers2);
        offers1.setId(null);
        assertThat(offers1).isNotEqualTo(offers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffersDTO.class);
        OffersDTO offersDTO1 = new OffersDTO();
        offersDTO1.setId(1L);
        OffersDTO offersDTO2 = new OffersDTO();
        assertThat(offersDTO1).isNotEqualTo(offersDTO2);
        offersDTO2.setId(offersDTO1.getId());
        assertThat(offersDTO1).isEqualTo(offersDTO2);
        offersDTO2.setId(2L);
        assertThat(offersDTO1).isNotEqualTo(offersDTO2);
        offersDTO1.setId(null);
        assertThat(offersDTO1).isNotEqualTo(offersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(offersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(offersMapper.fromId(null)).isNull();
    }
}
