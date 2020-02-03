package pl.edu.pwr.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import pl.edu.pwr.domain.EntrustmentPlan;
import pl.edu.pwr.domain.enumeration.SemesterType;
import pl.edu.pwr.repository.EducationPlanRepository;
import pl.edu.pwr.repository.EntrustmentPlanRepository;
import pl.edu.pwr.repository.FieldOfStudyRepository;
import pl.edu.pwr.service.EntrustmentPlanService;
import pl.edu.pwr.service.dto.EntrustmentPlanDTO;
import pl.edu.pwr.service.mapper.EntrustmentPlanMapper;
import pl.edu.pwr.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link EntrustmentPlanResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
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

    @Qualifier("mvcValidator")
    @Autowired
    private Validator validator;

    private MockMvc restEntrustmentPlanMockMvc;

    private EntrustmentPlan entrustmentPlan;
    @Autowired
    private EducationPlanRepository educationPlanRepository;
    @Autowired
    private FieldOfStudyRepository fieldOfStudyRepository;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntrustmentPlan createEntity(EntityManager em) {
        return new EntrustmentPlan()
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .semesterType(DEFAULT_SEMESTER_TYPE)
            .educationPlan(EducationPlanResourceIT.createEntity(em));
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntrustmentPlan createUpdatedEntity(EntityManager em) {
        return new EntrustmentPlan()
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .semesterType(UPDATED_SEMESTER_TYPE)
            .educationPlan(EducationPlanResourceIT.createEntity(em));
    }

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

    @BeforeEach
    public void initTest() {
        entrustmentPlan = createEntity(em);
        fieldOfStudyRepository.saveAndFlush(entrustmentPlan.getEducationPlan().getFieldOfStudy());
        educationPlanRepository.saveAndFlush(entrustmentPlan.getEducationPlan());
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
