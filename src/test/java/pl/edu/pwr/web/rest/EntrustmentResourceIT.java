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
import pl.edu.pwr.domain.Entrustment;
import pl.edu.pwr.repository.*;
import pl.edu.pwr.service.EntrustmentService;
import pl.edu.pwr.service.TeacherService;
import pl.edu.pwr.service.dto.EntrustmentDTO;
import pl.edu.pwr.service.mapper.EntrustmentMapper;
import pl.edu.pwr.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link EntrustmentResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
public class EntrustmentResourceIT {

    private static final Integer DEFAULT_HOURS = 1;
    private static final Integer UPDATED_HOURS = 2;

    private static final Float DEFAULT_HOURS_MULTIPLIER = 1F;
    private static final Float UPDATED_HOURS_MULTIPLIER = 2F;

    @Autowired
    private EntrustmentRepository entrustmentRepository;

    @Autowired
    private EntrustmentMapper entrustmentMapper;

    @Autowired
    private EntrustmentService entrustmentService;

    @Autowired
    private TeacherService teacherService;

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

    private MockMvc restEntrustmentMockMvc;

    private Entrustment entrustment;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FieldOfStudyRepository fieldOfStudyRepository;
    @Autowired
    private EducationPlanRepository educationPlanRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private EntrustmentPlanRepository entrustmentPlanRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseClassRepository courseClassRepository;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrustment createEntity(EntityManager em) {
        Entrustment entrustment = new Entrustment()
            .hours(DEFAULT_HOURS)
            .hoursMultiplier(DEFAULT_HOURS_MULTIPLIER)
            .entrustmentPlan(EntrustmentPlanResourceIT.createEntity(em))
            .teacher(TeacherResourceIT.createEntity(em))
            .courseClass(CourseClassResourceIT.createEntity(em));
        return entrustment;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrustment createUpdatedEntity(EntityManager em) {
        Entrustment entrustment = new Entrustment()
            .hours(UPDATED_HOURS)
            .hoursMultiplier(UPDATED_HOURS_MULTIPLIER)
            .entrustmentPlan(EntrustmentPlanResourceIT.createEntity(em))
            .teacher(TeacherResourceIT.createEntity(em))
            .courseClass(CourseClassResourceIT.createEntity(em));
        return entrustment;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntrustmentResource entrustmentResource = new EntrustmentResource(entrustmentService, teacherService);
        this.restEntrustmentMockMvc = MockMvcBuilders.standaloneSetup(entrustmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        entrustment = createEntity(em);

        userRepository.saveAndFlush(entrustment.getTeacher().getUser());
        teacherRepository.saveAndFlush(entrustment.getTeacher());

        fieldOfStudyRepository.saveAndFlush(entrustment.getEntrustmentPlan().getEducationPlan().getFieldOfStudy());
        educationPlanRepository.saveAndFlush(entrustment.getEntrustmentPlan().getEducationPlan());
        entrustmentPlanRepository.saveAndFlush(entrustment.getEntrustmentPlan());

        entrustment.getCourseClass().getCourse().setEducationPlan(entrustment.getEntrustmentPlan().getEducationPlan());
        courseRepository.saveAndFlush(entrustment.getCourseClass().getCourse());
        courseClassRepository.saveAndFlush(entrustment.getCourseClass());
    }

    @Test
    @Transactional
    public void createEntrustment() throws Exception {
        int databaseSizeBeforeCreate = entrustmentRepository.findAll().size();

        // Create the Entrustment
        EntrustmentDTO entrustmentDTO = entrustmentMapper.toDto(entrustment);
        restEntrustmentMockMvc.perform(post("/api/entrustments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Entrustment in the database
        List<Entrustment> entrustmentList = entrustmentRepository.findAll();
        assertThat(entrustmentList).hasSize(databaseSizeBeforeCreate + 1);
        Entrustment testEntrustment = entrustmentList.get(entrustmentList.size() - 1);
        assertThat(testEntrustment.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testEntrustment.getHoursMultiplier()).isEqualTo(DEFAULT_HOURS_MULTIPLIER);
    }

    @Test
    @Transactional
    public void createEntrustmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entrustmentRepository.findAll().size();

        // Create the Entrustment with an existing ID
        entrustment.setId(1L);
        EntrustmentDTO entrustmentDTO = entrustmentMapper.toDto(entrustment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrustmentMockMvc.perform(post("/api/entrustments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entrustment in the database
        List<Entrustment> entrustmentList = entrustmentRepository.findAll();
        assertThat(entrustmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntrustments() throws Exception {
        // Initialize the database
        entrustmentRepository.saveAndFlush(entrustment);

        // Get all the entrustmentList
        restEntrustmentMockMvc.perform(get("/api/entrustments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrustment.getId().intValue())))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].hoursMultiplier").value(hasItem(DEFAULT_HOURS_MULTIPLIER.doubleValue())));
    }

    @Test
    @Transactional
    public void getEntrustment() throws Exception {
        // Initialize the database
        entrustmentRepository.saveAndFlush(entrustment);

        // Get the entrustment
        restEntrustmentMockMvc.perform(get("/api/entrustments/{id}", entrustment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrustment.getId().intValue()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.hoursMultiplier").value(DEFAULT_HOURS_MULTIPLIER.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntrustment() throws Exception {
        // Get the entrustment
        restEntrustmentMockMvc.perform(get("/api/entrustments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrustment() throws Exception {
        // Initialize the database
        entrustmentRepository.saveAndFlush(entrustment);

        int databaseSizeBeforeUpdate = entrustmentRepository.findAll().size();

        // Update the entrustment
        Entrustment updatedEntrustment = entrustmentRepository.findById(entrustment.getId()).get();
        // Disconnect from session so that the updates on updatedEntrustment are not directly saved in db
        em.detach(updatedEntrustment);
        updatedEntrustment
            .hours(UPDATED_HOURS)
            .hoursMultiplier(UPDATED_HOURS_MULTIPLIER);
        EntrustmentDTO entrustmentDTO = entrustmentMapper.toDto(updatedEntrustment);

        restEntrustmentMockMvc.perform(put("/api/entrustments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentDTO)))
            .andExpect(status().isOk());

        // Validate the Entrustment in the database
        List<Entrustment> entrustmentList = entrustmentRepository.findAll();
        assertThat(entrustmentList).hasSize(databaseSizeBeforeUpdate);
        Entrustment testEntrustment = entrustmentList.get(entrustmentList.size() - 1);
        assertThat(testEntrustment.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testEntrustment.getHoursMultiplier()).isEqualTo(UPDATED_HOURS_MULTIPLIER);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrustment() throws Exception {
        int databaseSizeBeforeUpdate = entrustmentRepository.findAll().size();

        // Create the Entrustment
        EntrustmentDTO entrustmentDTO = entrustmentMapper.toDto(entrustment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrustmentMockMvc.perform(put("/api/entrustments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrustmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entrustment in the database
        List<Entrustment> entrustmentList = entrustmentRepository.findAll();
        assertThat(entrustmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntrustment() throws Exception {
        // Initialize the database
        entrustmentRepository.saveAndFlush(entrustment);

        int databaseSizeBeforeDelete = entrustmentRepository.findAll().size();

        // Delete the entrustment
        restEntrustmentMockMvc.perform(delete("/api/entrustments/{id}", entrustment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entrustment> entrustmentList = entrustmentRepository.findAll();
        assertThat(entrustmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
