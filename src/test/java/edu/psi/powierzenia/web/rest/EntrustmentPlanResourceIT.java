package edu.psi.powierzenia.web.rest;

import edu.psi.powierzenia.RedisTestContainerExtension;
import edu.psi.powierzenia.PowierzeniaApp;
import edu.psi.powierzenia.config.TestSecurityConfiguration;
import edu.psi.powierzenia.domain.EntrustmentPlan;
import edu.psi.powierzenia.repository.EntrustmentPlanRepository;
import edu.psi.powierzenia.service.EntrustmentPlanService;
import edu.psi.powierzenia.service.dto.EntrustmentPlanDTO;
import edu.psi.powierzenia.service.mapper.EntrustmentPlanMapper;
import edu.psi.powierzenia.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import javax.persistence.EntityManager;
import java.util.List;

import static edu.psi.powierzenia.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.psi.powierzenia.domain.enumeration.SemesterType;
/**
 * Integration tests for the {@link EntrustmentPlanResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
@ExtendWith(RedisTestContainerExtension.class)
public class EntrustmentPlanResourceIT {

    private static final Integer DEFAULT_ACADEMIC_YEAR = 1;
    private static final Integer UPDATED_ACADEMIC_YEAR = 2;

    private static final SemesterType DEFAULT_SEMESTER_TYPE = SemesterType.SUMMER;
    private static final SemesterType UPDATED_SEMESTER_TYPE = SemesterType.WINTER;

    @Autowired
    private EntrustmentPlanRepository entrustmentPlanRepository;

    @Autowired
    private EntrustmentPlanMapper entrustmentPlanMapper;

    @Autowired
    private EntrustmentPlanService entrustmentPlanService;

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

    private MockMvc restEntrustmentPlanMockMvc;

    private EntrustmentPlan entrustmentPlan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntrustmentPlanResource entrustmentPlanResource = new EntrustmentPlanResource(entrustmentPlanService);
        this.restEntrustmentPlanMockMvc = MockMvcBuilders.standaloneSetup(entrustmentPlanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntrustmentPlan createEntity(EntityManager em) {
        EntrustmentPlan entrustmentPlan = new EntrustmentPlan()
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .semesterType(DEFAULT_SEMESTER_TYPE);
        return entrustmentPlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntrustmentPlan createUpdatedEntity(EntityManager em) {
        EntrustmentPlan entrustmentPlan = new EntrustmentPlan()
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .semesterType(UPDATED_SEMESTER_TYPE);
        return entrustmentPlan;
    }

    @BeforeEach
    public void initTest() {
        entrustmentPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntrustmentPlan() throws Exception {
        int databaseSizeBeforeCreate = entrustmentPlanRepository.findAll().size();

        // Create the EntrustmentPlan
        EntrustmentPlanDTO entrustmentPlanDTO = entrustmentPlanMapper.toDto(entrustmentPlan);
        restEntrustmentPlanMockMvc.perform(post("/api/entrustment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the EntrustmentPlan in the database
        List<EntrustmentPlan> entrustmentPlanList = entrustmentPlanRepository.findAll();
        assertThat(entrustmentPlanList).hasSize(databaseSizeBeforeCreate + 1);
        EntrustmentPlan testEntrustmentPlan = entrustmentPlanList.get(entrustmentPlanList.size() - 1);
        assertThat(testEntrustmentPlan.getAcademicYear()).isEqualTo(DEFAULT_ACADEMIC_YEAR);
        assertThat(testEntrustmentPlan.getSemesterType()).isEqualTo(DEFAULT_SEMESTER_TYPE);
    }

    @Test
    @Transactional
    public void createEntrustmentPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entrustmentPlanRepository.findAll().size();

        // Create the EntrustmentPlan with an existing ID
        entrustmentPlan.setId(1L);
        EntrustmentPlanDTO entrustmentPlanDTO = entrustmentPlanMapper.toDto(entrustmentPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrustmentPlanMockMvc.perform(post("/api/entrustment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntrustmentPlan in the database
        List<EntrustmentPlan> entrustmentPlanList = entrustmentPlanRepository.findAll();
        assertThat(entrustmentPlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntrustmentPlans() throws Exception {
        // Initialize the database
        entrustmentPlanRepository.saveAndFlush(entrustmentPlan);

        // Get all the entrustmentPlanList
        restEntrustmentPlanMockMvc.perform(get("/api/entrustment-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrustmentPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].semesterType").value(hasItem(DEFAULT_SEMESTER_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getEntrustmentPlan() throws Exception {
        // Initialize the database
        entrustmentPlanRepository.saveAndFlush(entrustmentPlan);

        // Get the entrustmentPlan
        restEntrustmentPlanMockMvc.perform(get("/api/entrustment-plans/{id}", entrustmentPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrustmentPlan.getId().intValue()))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.semesterType").value(DEFAULT_SEMESTER_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntrustmentPlan() throws Exception {
        // Get the entrustmentPlan
        restEntrustmentPlanMockMvc.perform(get("/api/entrustment-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrustmentPlan() throws Exception {
        // Initialize the database
        entrustmentPlanRepository.saveAndFlush(entrustmentPlan);

        int databaseSizeBeforeUpdate = entrustmentPlanRepository.findAll().size();

        // Update the entrustmentPlan
        EntrustmentPlan updatedEntrustmentPlan = entrustmentPlanRepository.findById(entrustmentPlan.getId()).get();
        // Disconnect from session so that the updates on updatedEntrustmentPlan are not directly saved in db
        em.detach(updatedEntrustmentPlan);
        updatedEntrustmentPlan
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .semesterType(UPDATED_SEMESTER_TYPE);
        EntrustmentPlanDTO entrustmentPlanDTO = entrustmentPlanMapper.toDto(updatedEntrustmentPlan);

        restEntrustmentPlanMockMvc.perform(put("/api/entrustment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentPlanDTO)))
            .andExpect(status().isOk());

        // Validate the EntrustmentPlan in the database
        List<EntrustmentPlan> entrustmentPlanList = entrustmentPlanRepository.findAll();
        assertThat(entrustmentPlanList).hasSize(databaseSizeBeforeUpdate);
        EntrustmentPlan testEntrustmentPlan = entrustmentPlanList.get(entrustmentPlanList.size() - 1);
        assertThat(testEntrustmentPlan.getAcademicYear()).isEqualTo(UPDATED_ACADEMIC_YEAR);
        assertThat(testEntrustmentPlan.getSemesterType()).isEqualTo(UPDATED_SEMESTER_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrustmentPlan() throws Exception {
        int databaseSizeBeforeUpdate = entrustmentPlanRepository.findAll().size();

        // Create the EntrustmentPlan
        EntrustmentPlanDTO entrustmentPlanDTO = entrustmentPlanMapper.toDto(entrustmentPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrustmentPlanMockMvc.perform(put("/api/entrustment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntrustmentPlan in the database
        List<EntrustmentPlan> entrustmentPlanList = entrustmentPlanRepository.findAll();
        assertThat(entrustmentPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntrustmentPlan() throws Exception {
        // Initialize the database
        entrustmentPlanRepository.saveAndFlush(entrustmentPlan);

        int databaseSizeBeforeDelete = entrustmentPlanRepository.findAll().size();

        // Delete the entrustmentPlan
        restEntrustmentPlanMockMvc.perform(delete("/api/entrustment-plans/{id}", entrustmentPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntrustmentPlan> entrustmentPlanList = entrustmentPlanRepository.findAll();
        assertThat(entrustmentPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
