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
import pl.edu.pwr.domain.EducationPlan;
import pl.edu.pwr.domain.enumeration.Specialization;
import pl.edu.pwr.domain.enumeration.StudiesLevel;
import pl.edu.pwr.domain.enumeration.StudiesType;
import pl.edu.pwr.repository.EducationPlanRepository;
import pl.edu.pwr.service.EducationPlanService;
import pl.edu.pwr.service.dto.EducationPlanDTO;
import pl.edu.pwr.service.mapper.EducationPlanMapper;
import pl.edu.pwr.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link EducationPlanResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
public class EducationPlanResourceIT {

    private static final Integer DEFAULT_START_ACADEMIC_YEAR = 1;
    private static final Integer UPDATED_START_ACADEMIC_YEAR = 2;

    private static final Specialization DEFAULT_SPECIALIZATION = Specialization.SOFTWARE_DEVELOPMENT;
    private static final Specialization UPDATED_SPECIALIZATION = Specialization.IT_SYSTEMS_DESIGN;

    private static final StudiesLevel DEFAULT_STUDIES_LEVEL = StudiesLevel.I;
    private static final StudiesLevel UPDATED_STUDIES_LEVEL = StudiesLevel.II;

    private static final StudiesType DEFAULT_STUDIES_TYPE = StudiesType.STATIONARY;
    private static final StudiesType UPDATED_STUDIES_TYPE = StudiesType.NONSTATIONARY;

    @Autowired
    private EducationPlanRepository educationPlanRepository;

    @Autowired
    private EducationPlanMapper educationPlanMapper;

    @Autowired
    private EducationPlanService educationPlanService;

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

    private MockMvc restEducationPlanMockMvc;

    private EducationPlan educationPlan;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationPlan createEntity(EntityManager em) {
        EducationPlan educationPlan = new EducationPlan()
            .startAcademicYear(DEFAULT_START_ACADEMIC_YEAR)
            .specialization(DEFAULT_SPECIALIZATION)
            .studiesLevel(DEFAULT_STUDIES_LEVEL)
            .studiesType(DEFAULT_STUDIES_TYPE);
        return educationPlan;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationPlan createUpdatedEntity(EntityManager em) {
        EducationPlan educationPlan = new EducationPlan()
            .startAcademicYear(UPDATED_START_ACADEMIC_YEAR)
            .specialization(UPDATED_SPECIALIZATION)
            .studiesLevel(UPDATED_STUDIES_LEVEL)
            .studiesType(UPDATED_STUDIES_TYPE);
        return educationPlan;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EducationPlanResource educationPlanResource = new EducationPlanResource(educationPlanService);
        this.restEducationPlanMockMvc = MockMvcBuilders.standaloneSetup(educationPlanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        educationPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationPlan() throws Exception {
        int databaseSizeBeforeCreate = educationPlanRepository.findAll().size();

        // Create the EducationPlan
        EducationPlanDTO educationPlanDTO = educationPlanMapper.toDto(educationPlan);
        restEducationPlanMockMvc.perform(post("/api/education-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the EducationPlan in the database
        List<EducationPlan> educationPlanList = educationPlanRepository.findAll();
        assertThat(educationPlanList).hasSize(databaseSizeBeforeCreate + 1);
        EducationPlan testEducationPlan = educationPlanList.get(educationPlanList.size() - 1);
        assertThat(testEducationPlan.getStartAcademicYear()).isEqualTo(DEFAULT_START_ACADEMIC_YEAR);
        assertThat(testEducationPlan.getSpecialization()).isEqualTo(DEFAULT_SPECIALIZATION);
        assertThat(testEducationPlan.getStudiesLevel()).isEqualTo(DEFAULT_STUDIES_LEVEL);
        assertThat(testEducationPlan.getStudiesType()).isEqualTo(DEFAULT_STUDIES_TYPE);
    }

    @Test
    @Transactional
    public void createEducationPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationPlanRepository.findAll().size();

        // Create the EducationPlan with an existing ID
        educationPlan.setId(1L);
        EducationPlanDTO educationPlanDTO = educationPlanMapper.toDto(educationPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationPlanMockMvc.perform(post("/api/education-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EducationPlan in the database
        List<EducationPlan> educationPlanList = educationPlanRepository.findAll();
        assertThat(educationPlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEducationPlans() throws Exception {
        // Initialize the database
        educationPlanRepository.saveAndFlush(educationPlan);

        // Get all the educationPlanList
        restEducationPlanMockMvc.perform(get("/api/education-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].startAcademicYear").value(hasItem(DEFAULT_START_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].specialization").value(hasItem(DEFAULT_SPECIALIZATION.toString())))
            .andExpect(jsonPath("$.[*].studiesLevel").value(hasItem(DEFAULT_STUDIES_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].studiesType").value(hasItem(DEFAULT_STUDIES_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEducationPlan() throws Exception {
        // Initialize the database
        educationPlanRepository.saveAndFlush(educationPlan);

        // Get the educationPlan
        restEducationPlanMockMvc.perform(get("/api/education-plans/{id}", educationPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(educationPlan.getId().intValue()))
            .andExpect(jsonPath("$.startAcademicYear").value(DEFAULT_START_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.specialization").value(DEFAULT_SPECIALIZATION.toString()))
            .andExpect(jsonPath("$.studiesLevel").value(DEFAULT_STUDIES_LEVEL.toString()))
            .andExpect(jsonPath("$.studiesType").value(DEFAULT_STUDIES_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEducationPlan() throws Exception {
        // Get the educationPlan
        restEducationPlanMockMvc.perform(get("/api/education-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationPlan() throws Exception {
        // Initialize the database
        educationPlanRepository.saveAndFlush(educationPlan);

        int databaseSizeBeforeUpdate = educationPlanRepository.findAll().size();

        // Update the educationPlan
        EducationPlan updatedEducationPlan = educationPlanRepository.findById(educationPlan.getId()).get();
        // Disconnect from session so that the updates on updatedEducationPlan are not directly saved in db
        em.detach(updatedEducationPlan);
        updatedEducationPlan
            .startAcademicYear(UPDATED_START_ACADEMIC_YEAR)
            .specialization(UPDATED_SPECIALIZATION)
            .studiesLevel(UPDATED_STUDIES_LEVEL)
            .studiesType(UPDATED_STUDIES_TYPE);
        EducationPlanDTO educationPlanDTO = educationPlanMapper.toDto(updatedEducationPlan);

        restEducationPlanMockMvc.perform(put("/api/education-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPlanDTO)))
            .andExpect(status().isOk());

        // Validate the EducationPlan in the database
        List<EducationPlan> educationPlanList = educationPlanRepository.findAll();
        assertThat(educationPlanList).hasSize(databaseSizeBeforeUpdate);
        EducationPlan testEducationPlan = educationPlanList.get(educationPlanList.size() - 1);
        assertThat(testEducationPlan.getStartAcademicYear()).isEqualTo(UPDATED_START_ACADEMIC_YEAR);
        assertThat(testEducationPlan.getSpecialization()).isEqualTo(UPDATED_SPECIALIZATION);
        assertThat(testEducationPlan.getStudiesLevel()).isEqualTo(UPDATED_STUDIES_LEVEL);
        assertThat(testEducationPlan.getStudiesType()).isEqualTo(UPDATED_STUDIES_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationPlan() throws Exception {
        int databaseSizeBeforeUpdate = educationPlanRepository.findAll().size();

        // Create the EducationPlan
        EducationPlanDTO educationPlanDTO = educationPlanMapper.toDto(educationPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationPlanMockMvc.perform(put("/api/education-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EducationPlan in the database
        List<EducationPlan> educationPlanList = educationPlanRepository.findAll();
        assertThat(educationPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEducationPlan() throws Exception {
        // Initialize the database
        educationPlanRepository.saveAndFlush(educationPlan);

        int databaseSizeBeforeDelete = educationPlanRepository.findAll().size();

        // Delete the educationPlan
        restEducationPlanMockMvc.perform(delete("/api/education-plans/{id}", educationPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EducationPlan> educationPlanList = educationPlanRepository.findAll();
        assertThat(educationPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
