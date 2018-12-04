package com.orderonline.frontend.web.rest;

import com.orderonline.frontend.OrderOnlineFrontEndApp;

import com.orderonline.frontend.domain.Dishes;
import com.orderonline.frontend.repository.DishesRepository;
import com.orderonline.frontend.service.DishesService;
import com.orderonline.frontend.service.dto.DishesDTO;
import com.orderonline.frontend.service.mapper.DishesMapper;
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
 * Test class for the DishesResource REST controller.
 *
 * @see DishesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderOnlineFrontEndApp.class)
public class DishesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private DishesRepository dishesRepository;

    @Autowired
    private DishesMapper dishesMapper;

    @Autowired
    private DishesService dishesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDishesMockMvc;

    private Dishes dishes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DishesResource dishesResource = new DishesResource(dishesService);
        this.restDishesMockMvc = MockMvcBuilders.standaloneSetup(dishesResource)
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
    public static Dishes createEntity(EntityManager em) {
        Dishes dishes = new Dishes()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .available(DEFAULT_AVAILABLE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return dishes;
    }

    @Before
    public void initTest() {
        dishes = createEntity(em);
    }

    @Test
    @Transactional
    public void createDishes() throws Exception {
        int databaseSizeBeforeCreate = dishesRepository.findAll().size();

        // Create the Dishes
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);
        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isCreated());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeCreate + 1);
        Dishes testDishes = dishesList.get(dishesList.size() - 1);
        assertThat(testDishes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDishes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDishes.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testDishes.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDishes.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDishesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dishesRepository.findAll().size();

        // Create the Dishes with an existing ID
        dishes.setId(1L);
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dishesRepository.findAll().size();
        // set the field null
        dishes.setName(null);

        // Create the Dishes, which fails.
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dishesRepository.findAll().size();
        // set the field null
        dishes.setDescription(null);

        // Create the Dishes, which fails.
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = dishesRepository.findAll().size();
        // set the field null
        dishes.setAvailable(null);

        // Create the Dishes, which fails.
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        // Get all the dishesList
        restDishesMockMvc.perform(get("/api/dishes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dishes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        // Get the dishes
        restDishesMockMvc.perform(get("/api/dishes/{id}", dishes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dishes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingDishes() throws Exception {
        // Get the dishes
        restDishesMockMvc.perform(get("/api/dishes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        int databaseSizeBeforeUpdate = dishesRepository.findAll().size();

        // Update the dishes
        Dishes updatedDishes = dishesRepository.findById(dishes.getId()).get();
        // Disconnect from session so that the updates on updatedDishes are not directly saved in db
        em.detach(updatedDishes);
        updatedDishes
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .available(UPDATED_AVAILABLE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        DishesDTO dishesDTO = dishesMapper.toDto(updatedDishes);

        restDishesMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isOk());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeUpdate);
        Dishes testDishes = dishesList.get(dishesList.size() - 1);
        assertThat(testDishes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDishes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDishes.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testDishes.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDishes.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDishes() throws Exception {
        int databaseSizeBeforeUpdate = dishesRepository.findAll().size();

        // Create the Dishes
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDishesMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDishes() throws Exception {
        // Initialize the database
        dishesRepository.saveAndFlush(dishes);

        int databaseSizeBeforeDelete = dishesRepository.findAll().size();

        // Get the dishes
        restDishesMockMvc.perform(delete("/api/dishes/{id}", dishes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dishes.class);
        Dishes dishes1 = new Dishes();
        dishes1.setId(1L);
        Dishes dishes2 = new Dishes();
        dishes2.setId(dishes1.getId());
        assertThat(dishes1).isEqualTo(dishes2);
        dishes2.setId(2L);
        assertThat(dishes1).isNotEqualTo(dishes2);
        dishes1.setId(null);
        assertThat(dishes1).isNotEqualTo(dishes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DishesDTO.class);
        DishesDTO dishesDTO1 = new DishesDTO();
        dishesDTO1.setId(1L);
        DishesDTO dishesDTO2 = new DishesDTO();
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
        dishesDTO2.setId(dishesDTO1.getId());
        assertThat(dishesDTO1).isEqualTo(dishesDTO2);
        dishesDTO2.setId(2L);
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
        dishesDTO1.setId(null);
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dishesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dishesMapper.fromId(null)).isNull();
    }
}
