package com.orderonline.frontend.web.rest;

import com.orderonline.frontend.OrderOnlineFrontEndApp;

import com.orderonline.frontend.domain.RestaurantAdmin;
import com.orderonline.frontend.repository.RestaurantAdminRepository;
import com.orderonline.frontend.service.RestaurantAdminService;
import com.orderonline.frontend.service.dto.RestaurantAdminDTO;
import com.orderonline.frontend.service.mapper.RestaurantAdminMapper;
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
import java.util.List;


import static com.orderonline.frontend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RestaurantAdminResource REST controller.
 *
 * @see RestaurantAdminResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderOnlineFrontEndApp.class)
public class RestaurantAdminResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RestaurantAdminRepository restaurantAdminRepository;

    @Autowired
    private RestaurantAdminMapper restaurantAdminMapper;

    @Autowired
    private RestaurantAdminService restaurantAdminService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRestaurantAdminMockMvc;

    private RestaurantAdmin restaurantAdmin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestaurantAdminResource restaurantAdminResource = new RestaurantAdminResource(restaurantAdminService);
        this.restRestaurantAdminMockMvc = MockMvcBuilders.standaloneSetup(restaurantAdminResource)
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
    public static RestaurantAdmin createEntity(EntityManager em) {
        RestaurantAdmin restaurantAdmin = new RestaurantAdmin()
            .name(DEFAULT_NAME);
        return restaurantAdmin;
    }

    @Before
    public void initTest() {
        restaurantAdmin = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestaurantAdmin() throws Exception {
        int databaseSizeBeforeCreate = restaurantAdminRepository.findAll().size();

        // Create the RestaurantAdmin
        RestaurantAdminDTO restaurantAdminDTO = restaurantAdminMapper.toDto(restaurantAdmin);
        restRestaurantAdminMockMvc.perform(post("/api/restaurant-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantAdminDTO)))
            .andExpect(status().isCreated());

        // Validate the RestaurantAdmin in the database
        List<RestaurantAdmin> restaurantAdminList = restaurantAdminRepository.findAll();
        assertThat(restaurantAdminList).hasSize(databaseSizeBeforeCreate + 1);
        RestaurantAdmin testRestaurantAdmin = restaurantAdminList.get(restaurantAdminList.size() - 1);
        assertThat(testRestaurantAdmin.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRestaurantAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restaurantAdminRepository.findAll().size();

        // Create the RestaurantAdmin with an existing ID
        restaurantAdmin.setId(1L);
        RestaurantAdminDTO restaurantAdminDTO = restaurantAdminMapper.toDto(restaurantAdmin);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantAdminMockMvc.perform(post("/api/restaurant-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantAdminDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantAdmin in the database
        List<RestaurantAdmin> restaurantAdminList = restaurantAdminRepository.findAll();
        assertThat(restaurantAdminList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRestaurantAdmins() throws Exception {
        // Initialize the database
        restaurantAdminRepository.saveAndFlush(restaurantAdmin);

        // Get all the restaurantAdminList
        restRestaurantAdminMockMvc.perform(get("/api/restaurant-admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurantAdmin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRestaurantAdmin() throws Exception {
        // Initialize the database
        restaurantAdminRepository.saveAndFlush(restaurantAdmin);

        // Get the restaurantAdmin
        restRestaurantAdminMockMvc.perform(get("/api/restaurant-admins/{id}", restaurantAdmin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restaurantAdmin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurantAdmin() throws Exception {
        // Get the restaurantAdmin
        restRestaurantAdminMockMvc.perform(get("/api/restaurant-admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurantAdmin() throws Exception {
        // Initialize the database
        restaurantAdminRepository.saveAndFlush(restaurantAdmin);

        int databaseSizeBeforeUpdate = restaurantAdminRepository.findAll().size();

        // Update the restaurantAdmin
        RestaurantAdmin updatedRestaurantAdmin = restaurantAdminRepository.findById(restaurantAdmin.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurantAdmin are not directly saved in db
        em.detach(updatedRestaurantAdmin);
        updatedRestaurantAdmin
            .name(UPDATED_NAME);
        RestaurantAdminDTO restaurantAdminDTO = restaurantAdminMapper.toDto(updatedRestaurantAdmin);

        restRestaurantAdminMockMvc.perform(put("/api/restaurant-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantAdminDTO)))
            .andExpect(status().isOk());

        // Validate the RestaurantAdmin in the database
        List<RestaurantAdmin> restaurantAdminList = restaurantAdminRepository.findAll();
        assertThat(restaurantAdminList).hasSize(databaseSizeBeforeUpdate);
        RestaurantAdmin testRestaurantAdmin = restaurantAdminList.get(restaurantAdminList.size() - 1);
        assertThat(testRestaurantAdmin.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRestaurantAdmin() throws Exception {
        int databaseSizeBeforeUpdate = restaurantAdminRepository.findAll().size();

        // Create the RestaurantAdmin
        RestaurantAdminDTO restaurantAdminDTO = restaurantAdminMapper.toDto(restaurantAdmin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantAdminMockMvc.perform(put("/api/restaurant-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantAdminDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantAdmin in the database
        List<RestaurantAdmin> restaurantAdminList = restaurantAdminRepository.findAll();
        assertThat(restaurantAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRestaurantAdmin() throws Exception {
        // Initialize the database
        restaurantAdminRepository.saveAndFlush(restaurantAdmin);

        int databaseSizeBeforeDelete = restaurantAdminRepository.findAll().size();

        // Get the restaurantAdmin
        restRestaurantAdminMockMvc.perform(delete("/api/restaurant-admins/{id}", restaurantAdmin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RestaurantAdmin> restaurantAdminList = restaurantAdminRepository.findAll();
        assertThat(restaurantAdminList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestaurantAdmin.class);
        RestaurantAdmin restaurantAdmin1 = new RestaurantAdmin();
        restaurantAdmin1.setId(1L);
        RestaurantAdmin restaurantAdmin2 = new RestaurantAdmin();
        restaurantAdmin2.setId(restaurantAdmin1.getId());
        assertThat(restaurantAdmin1).isEqualTo(restaurantAdmin2);
        restaurantAdmin2.setId(2L);
        assertThat(restaurantAdmin1).isNotEqualTo(restaurantAdmin2);
        restaurantAdmin1.setId(null);
        assertThat(restaurantAdmin1).isNotEqualTo(restaurantAdmin2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestaurantAdminDTO.class);
        RestaurantAdminDTO restaurantAdminDTO1 = new RestaurantAdminDTO();
        restaurantAdminDTO1.setId(1L);
        RestaurantAdminDTO restaurantAdminDTO2 = new RestaurantAdminDTO();
        assertThat(restaurantAdminDTO1).isNotEqualTo(restaurantAdminDTO2);
        restaurantAdminDTO2.setId(restaurantAdminDTO1.getId());
        assertThat(restaurantAdminDTO1).isEqualTo(restaurantAdminDTO2);
        restaurantAdminDTO2.setId(2L);
        assertThat(restaurantAdminDTO1).isNotEqualTo(restaurantAdminDTO2);
        restaurantAdminDTO1.setId(null);
        assertThat(restaurantAdminDTO1).isNotEqualTo(restaurantAdminDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(restaurantAdminMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(restaurantAdminMapper.fromId(null)).isNull();
    }
}
