package pl.edu.pwr.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import pl.edu.pwr.PowierzeniaApp;
import pl.edu.pwr.config.TestSecurityConfiguration;
import pl.edu.pwr.domain.FieldOfStudy;
import pl.edu.pwr.repository.FieldOfStudyRepository;
import pl.edu.pwr.service.FieldOfStudyService;
import pl.edu.pwr.service.dto.FieldOfStudyDTO;
import pl.edu.pwr.service.mapper.FieldOfStudyMapper;
import pl.edu.pwr.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link FieldOfStudyResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
public class FieldOfStudyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Autowired
    private FieldOfStudyService fieldOfStudyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFieldOfStudyMockMvc;

    private FieldOfStudy fieldOfStudy;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldOfStudy createEntity(EntityManager em) {
        FieldOfStudy fieldOfStudy = new FieldOfStudy()
            .name(DEFAULT_NAME);
        return fieldOfStudy;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldOfStudy createUpdatedEntity(EntityManager em) {
        FieldOfStudy fieldOfStudy = new FieldOfStudy()
            .name(UPDATED_NAME);
        return fieldOfStudy;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FieldOfStudyResource fieldOfStudyResource = new FieldOfStudyResource(fieldOfStudyService);
        this.restFieldOfStudyMockMvc = MockMvcBuilders.standaloneSetup(fieldOfStudyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        fieldOfStudy = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldOfStudy() throws Exception {
        int databaseSizeBeforeCreate = fieldOfStudyRepository.findAll().size();

        // Create the FieldOfStudy
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);
        restFieldOfStudyMockMvc.perform(post("/api/field-of-studies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isCreated());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeCreate + 1);
        FieldOfStudy testFieldOfStudy = fieldOfStudyList.get(fieldOfStudyList.size() - 1);
        assertThat(testFieldOfStudy.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFieldOfStudyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldOfStudyRepository.findAll().size();

        // Create the FieldOfStudy with an existing ID
        fieldOfStudy.setId(1L);
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldOfStudyMockMvc.perform(post("/api/field-of-studies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFieldOfStudies() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        // Get all the fieldOfStudyList
        restFieldOfStudyMockMvc.perform(get("/api/field-of-studies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldOfStudy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getFieldOfStudy() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        // Get the fieldOfStudy
        restFieldOfStudyMockMvc.perform(get("/api/field-of-studies/{id}", fieldOfStudy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fieldOfStudy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingFieldOfStudy() throws Exception {
        // Get the fieldOfStudy
        restFieldOfStudyMockMvc.perform(get("/api/field-of-studies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldOfStudy() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        int databaseSizeBeforeUpdate = fieldOfStudyRepository.findAll().size();

        // Update the fieldOfStudy
        FieldOfStudy updatedFieldOfStudy = fieldOfStudyRepository.findById(fieldOfStudy.getId()).get();
        // Disconnect from session so that the updates on updatedFieldOfStudy are not directly saved in db
        em.detach(updatedFieldOfStudy);
        updatedFieldOfStudy
            .name(UPDATED_NAME);
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(updatedFieldOfStudy);

        restFieldOfStudyMockMvc.perform(put("/api/field-of-studies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isOk());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeUpdate);
        FieldOfStudy testFieldOfStudy = fieldOfStudyList.get(fieldOfStudyList.size() - 1);
        assertThat(testFieldOfStudy.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldOfStudy() throws Exception {
        int databaseSizeBeforeUpdate = fieldOfStudyRepository.findAll().size();

        // Create the FieldOfStudy
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldOfStudyMockMvc.perform(put("/api/field-of-studies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFieldOfStudy() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        int databaseSizeBeforeDelete = fieldOfStudyRepository.findAll().size();

        // Delete the fieldOfStudy
        restFieldOfStudyMockMvc.perform(delete("/api/field-of-studies/{id}", fieldOfStudy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
