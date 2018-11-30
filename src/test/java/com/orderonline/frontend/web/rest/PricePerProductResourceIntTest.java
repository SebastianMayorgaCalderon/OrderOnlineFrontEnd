package com.orderonline.frontend.web.rest;

import com.orderonline.frontend.OrderOnlineFrontEndApp;

import com.orderonline.frontend.domain.PricePerProduct;
import com.orderonline.frontend.repository.PricePerProductRepository;
import com.orderonline.frontend.service.PricePerProductService;
import com.orderonline.frontend.service.dto.PricePerProductDTO;
import com.orderonline.frontend.service.mapper.PricePerProductMapper;
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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.orderonline.frontend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PricePerProductResource REST controller.
 *
 * @see PricePerProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderOnlineFrontEndApp.class)
public class PricePerProductResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PricePerProductRepository pricePerProductRepository;

    @Autowired
    private PricePerProductMapper pricePerProductMapper;

    @Autowired
    private PricePerProductService pricePerProductService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPricePerProductMockMvc;

    private PricePerProduct pricePerProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PricePerProductResource pricePerProductResource = new PricePerProductResource(pricePerProductService);
        this.restPricePerProductMockMvc = MockMvcBuilders.standaloneSetup(pricePerProductResource)
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
    public static PricePerProduct createEntity(EntityManager em) {
        PricePerProduct pricePerProduct = new PricePerProduct()
            .price(DEFAULT_PRICE)
            .date(DEFAULT_DATE);
        return pricePerProduct;
    }

    @Before
    public void initTest() {
        pricePerProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createPricePerProduct() throws Exception {
        int databaseSizeBeforeCreate = pricePerProductRepository.findAll().size();

        // Create the PricePerProduct
        PricePerProductDTO pricePerProductDTO = pricePerProductMapper.toDto(pricePerProduct);
        restPricePerProductMockMvc.perform(post("/api/price-per-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePerProductDTO)))
            .andExpect(status().isCreated());

        // Validate the PricePerProduct in the database
        List<PricePerProduct> pricePerProductList = pricePerProductRepository.findAll();
        assertThat(pricePerProductList).hasSize(databaseSizeBeforeCreate + 1);
        PricePerProduct testPricePerProduct = pricePerProductList.get(pricePerProductList.size() - 1);
        assertThat(testPricePerProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPricePerProduct.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createPricePerProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pricePerProductRepository.findAll().size();

        // Create the PricePerProduct with an existing ID
        pricePerProduct.setId(1L);
        PricePerProductDTO pricePerProductDTO = pricePerProductMapper.toDto(pricePerProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricePerProductMockMvc.perform(post("/api/price-per-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePerProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PricePerProduct in the database
        List<PricePerProduct> pricePerProductList = pricePerProductRepository.findAll();
        assertThat(pricePerProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricePerProductRepository.findAll().size();
        // set the field null
        pricePerProduct.setPrice(null);

        // Create the PricePerProduct, which fails.
        PricePerProductDTO pricePerProductDTO = pricePerProductMapper.toDto(pricePerProduct);

        restPricePerProductMockMvc.perform(post("/api/price-per-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePerProductDTO)))
            .andExpect(status().isBadRequest());

        List<PricePerProduct> pricePerProductList = pricePerProductRepository.findAll();
        assertThat(pricePerProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricePerProductRepository.findAll().size();
        // set the field null
        pricePerProduct.setDate(null);

        // Create the PricePerProduct, which fails.
        PricePerProductDTO pricePerProductDTO = pricePerProductMapper.toDto(pricePerProduct);

        restPricePerProductMockMvc.perform(post("/api/price-per-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePerProductDTO)))
            .andExpect(status().isBadRequest());

        List<PricePerProduct> pricePerProductList = pricePerProductRepository.findAll();
        assertThat(pricePerProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPricePerProducts() throws Exception {
        // Initialize the database
        pricePerProductRepository.saveAndFlush(pricePerProduct);

        // Get all the pricePerProductList
        restPricePerProductMockMvc.perform(get("/api/price-per-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricePerProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPricePerProduct() throws Exception {
        // Initialize the database
        pricePerProductRepository.saveAndFlush(pricePerProduct);

        // Get the pricePerProduct
        restPricePerProductMockMvc.perform(get("/api/price-per-products/{id}", pricePerProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pricePerProduct.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPricePerProduct() throws Exception {
        // Get the pricePerProduct
        restPricePerProductMockMvc.perform(get("/api/price-per-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePricePerProduct() throws Exception {
        // Initialize the database
        pricePerProductRepository.saveAndFlush(pricePerProduct);

        int databaseSizeBeforeUpdate = pricePerProductRepository.findAll().size();

        // Update the pricePerProduct
        PricePerProduct updatedPricePerProduct = pricePerProductRepository.findById(pricePerProduct.getId()).get();
        // Disconnect from session so that the updates on updatedPricePerProduct are not directly saved in db
        em.detach(updatedPricePerProduct);
        updatedPricePerProduct
            .price(UPDATED_PRICE)
            .date(UPDATED_DATE);
        PricePerProductDTO pricePerProductDTO = pricePerProductMapper.toDto(updatedPricePerProduct);

        restPricePerProductMockMvc.perform(put("/api/price-per-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePerProductDTO)))
            .andExpect(status().isOk());

        // Validate the PricePerProduct in the database
        List<PricePerProduct> pricePerProductList = pricePerProductRepository.findAll();
        assertThat(pricePerProductList).hasSize(databaseSizeBeforeUpdate);
        PricePerProduct testPricePerProduct = pricePerProductList.get(pricePerProductList.size() - 1);
        assertThat(testPricePerProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPricePerProduct.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPricePerProduct() throws Exception {
        int databaseSizeBeforeUpdate = pricePerProductRepository.findAll().size();

        // Create the PricePerProduct
        PricePerProductDTO pricePerProductDTO = pricePerProductMapper.toDto(pricePerProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricePerProductMockMvc.perform(put("/api/price-per-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePerProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PricePerProduct in the database
        List<PricePerProduct> pricePerProductList = pricePerProductRepository.findAll();
        assertThat(pricePerProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePricePerProduct() throws Exception {
        // Initialize the database
        pricePerProductRepository.saveAndFlush(pricePerProduct);

        int databaseSizeBeforeDelete = pricePerProductRepository.findAll().size();

        // Get the pricePerProduct
        restPricePerProductMockMvc.perform(delete("/api/price-per-products/{id}", pricePerProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PricePerProduct> pricePerProductList = pricePerProductRepository.findAll();
        assertThat(pricePerProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricePerProduct.class);
        PricePerProduct pricePerProduct1 = new PricePerProduct();
        pricePerProduct1.setId(1L);
        PricePerProduct pricePerProduct2 = new PricePerProduct();
        pricePerProduct2.setId(pricePerProduct1.getId());
        assertThat(pricePerProduct1).isEqualTo(pricePerProduct2);
        pricePerProduct2.setId(2L);
        assertThat(pricePerProduct1).isNotEqualTo(pricePerProduct2);
        pricePerProduct1.setId(null);
        assertThat(pricePerProduct1).isNotEqualTo(pricePerProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricePerProductDTO.class);
        PricePerProductDTO pricePerProductDTO1 = new PricePerProductDTO();
        pricePerProductDTO1.setId(1L);
        PricePerProductDTO pricePerProductDTO2 = new PricePerProductDTO();
        assertThat(pricePerProductDTO1).isNotEqualTo(pricePerProductDTO2);
        pricePerProductDTO2.setId(pricePerProductDTO1.getId());
        assertThat(pricePerProductDTO1).isEqualTo(pricePerProductDTO2);
        pricePerProductDTO2.setId(2L);
        assertThat(pricePerProductDTO1).isNotEqualTo(pricePerProductDTO2);
        pricePerProductDTO1.setId(null);
        assertThat(pricePerProductDTO1).isNotEqualTo(pricePerProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pricePerProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pricePerProductMapper.fromId(null)).isNull();
    }
}
