package com.orderonline.frontend.web.rest;

import com.orderonline.frontend.OrderOnlineFrontEndApp;

import com.orderonline.frontend.domain.Combos;
import com.orderonline.frontend.repository.CombosRepository;
import com.orderonline.frontend.service.CombosService;
import com.orderonline.frontend.service.dto.CombosDTO;
import com.orderonline.frontend.service.mapper.CombosMapper;
import com.orderonline.frontend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.orderonline.frontend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CombosResource REST controller.
 *
 * @see CombosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderOnlineFrontEndApp.class)
public class CombosResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    @Autowired
    private CombosRepository combosRepository;

    @Mock
    private CombosRepository combosRepositoryMock;

    @Autowired
    private CombosMapper combosMapper;

    @Mock
    private CombosService combosServiceMock;

    @Autowired
    private CombosService combosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCombosMockMvc;

    private Combos combos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CombosResource combosResource = new CombosResource(combosService);
        this.restCombosMockMvc = MockMvcBuilders.standaloneSetup(combosResource)
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
    public static Combos createEntity(EntityManager em) {
        Combos combos = new Combos()
            .name(DEFAULT_NAME)
            .available(DEFAULT_AVAILABLE);
        return combos;
    }

    @Before
    public void initTest() {
        combos = createEntity(em);
    }

    @Test
    @Transactional
    public void createCombos() throws Exception {
        int databaseSizeBeforeCreate = combosRepository.findAll().size();

        // Create the Combos
        CombosDTO combosDTO = combosMapper.toDto(combos);
        restCombosMockMvc.perform(post("/api/combos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combosDTO)))
            .andExpect(status().isCreated());

        // Validate the Combos in the database
        List<Combos> combosList = combosRepository.findAll();
        assertThat(combosList).hasSize(databaseSizeBeforeCreate + 1);
        Combos testCombos = combosList.get(combosList.size() - 1);
        assertThat(testCombos.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCombos.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
    }

    @Test
    @Transactional
    public void createCombosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = combosRepository.findAll().size();

        // Create the Combos with an existing ID
        combos.setId(1L);
        CombosDTO combosDTO = combosMapper.toDto(combos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCombosMockMvc.perform(post("/api/combos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Combos in the database
        List<Combos> combosList = combosRepository.findAll();
        assertThat(combosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = combosRepository.findAll().size();
        // set the field null
        combos.setName(null);

        // Create the Combos, which fails.
        CombosDTO combosDTO = combosMapper.toDto(combos);

        restCombosMockMvc.perform(post("/api/combos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combosDTO)))
            .andExpect(status().isBadRequest());

        List<Combos> combosList = combosRepository.findAll();
        assertThat(combosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = combosRepository.findAll().size();
        // set the field null
        combos.setAvailable(null);

        // Create the Combos, which fails.
        CombosDTO combosDTO = combosMapper.toDto(combos);

        restCombosMockMvc.perform(post("/api/combos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combosDTO)))
            .andExpect(status().isBadRequest());

        List<Combos> combosList = combosRepository.findAll();
        assertThat(combosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCombos() throws Exception {
        // Initialize the database
        combosRepository.saveAndFlush(combos);

        // Get all the combosList
        restCombosMockMvc.perform(get("/api/combos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(combos.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCombosWithEagerRelationshipsIsEnabled() throws Exception {
        CombosResource combosResource = new CombosResource(combosServiceMock);
        when(combosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCombosMockMvc = MockMvcBuilders.standaloneSetup(combosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCombosMockMvc.perform(get("/api/combos?eagerload=true"))
        .andExpect(status().isOk());

        verify(combosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCombosWithEagerRelationshipsIsNotEnabled() throws Exception {
        CombosResource combosResource = new CombosResource(combosServiceMock);
            when(combosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCombosMockMvc = MockMvcBuilders.standaloneSetup(combosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCombosMockMvc.perform(get("/api/combos?eagerload=true"))
        .andExpect(status().isOk());

            verify(combosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCombos() throws Exception {
        // Initialize the database
        combosRepository.saveAndFlush(combos);

        // Get the combos
        restCombosMockMvc.perform(get("/api/combos/{id}", combos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(combos.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCombos() throws Exception {
        // Get the combos
        restCombosMockMvc.perform(get("/api/combos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCombos() throws Exception {
        // Initialize the database
        combosRepository.saveAndFlush(combos);

        int databaseSizeBeforeUpdate = combosRepository.findAll().size();

        // Update the combos
        Combos updatedCombos = combosRepository.findById(combos.getId()).get();
        // Disconnect from session so that the updates on updatedCombos are not directly saved in db
        em.detach(updatedCombos);
        updatedCombos
            .name(UPDATED_NAME)
            .available(UPDATED_AVAILABLE);
        CombosDTO combosDTO = combosMapper.toDto(updatedCombos);

        restCombosMockMvc.perform(put("/api/combos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combosDTO)))
            .andExpect(status().isOk());

        // Validate the Combos in the database
        List<Combos> combosList = combosRepository.findAll();
        assertThat(combosList).hasSize(databaseSizeBeforeUpdate);
        Combos testCombos = combosList.get(combosList.size() - 1);
        assertThat(testCombos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCombos.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCombos() throws Exception {
        int databaseSizeBeforeUpdate = combosRepository.findAll().size();

        // Create the Combos
        CombosDTO combosDTO = combosMapper.toDto(combos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCombosMockMvc.perform(put("/api/combos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(combosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Combos in the database
        List<Combos> combosList = combosRepository.findAll();
        assertThat(combosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCombos() throws Exception {
        // Initialize the database
        combosRepository.saveAndFlush(combos);

        int databaseSizeBeforeDelete = combosRepository.findAll().size();

        // Get the combos
        restCombosMockMvc.perform(delete("/api/combos/{id}", combos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Combos> combosList = combosRepository.findAll();
        assertThat(combosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Combos.class);
        Combos combos1 = new Combos();
        combos1.setId(1L);
        Combos combos2 = new Combos();
        combos2.setId(combos1.getId());
        assertThat(combos1).isEqualTo(combos2);
        combos2.setId(2L);
        assertThat(combos1).isNotEqualTo(combos2);
        combos1.setId(null);
        assertThat(combos1).isNotEqualTo(combos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CombosDTO.class);
        CombosDTO combosDTO1 = new CombosDTO();
        combosDTO1.setId(1L);
        CombosDTO combosDTO2 = new CombosDTO();
        assertThat(combosDTO1).isNotEqualTo(combosDTO2);
        combosDTO2.setId(combosDTO1.getId());
        assertThat(combosDTO1).isEqualTo(combosDTO2);
        combosDTO2.setId(2L);
        assertThat(combosDTO1).isNotEqualTo(combosDTO2);
        combosDTO1.setId(null);
        assertThat(combosDTO1).isNotEqualTo(combosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(combosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(combosMapper.fromId(null)).isNull();
    }
}
