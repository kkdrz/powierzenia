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
import pl.edu.pwr.domain.CourseClass;
import pl.edu.pwr.domain.enumeration.ClassFormType;
import pl.edu.pwr.repository.CourseClassRepository;
import pl.edu.pwr.service.CourseClassService;
import pl.edu.pwr.service.dto.CourseClassDTO;
import pl.edu.pwr.service.mapper.CourseClassMapper;
import pl.edu.pwr.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link CourseClassResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
public class CourseClassResourceIT {

    private static final Integer DEFAULT_HOURS = 1;
    private static final Integer UPDATED_HOURS = 2;

    private static final ClassFormType DEFAULT_FORM = ClassFormType.LABORATORY;
    private static final ClassFormType UPDATED_FORM = ClassFormType.LECTURE;

    @Autowired
    private CourseClassRepository courseClassRepository;

    @Autowired
    private CourseClassMapper courseClassMapper;

    @Autowired
    private CourseClassService courseClassService;

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

    private MockMvc restCourseClassMockMvc;

    private CourseClass courseClass;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseClass createEntity(EntityManager em) {
        CourseClass courseClass = new CourseClass()
            .hours(DEFAULT_HOURS)
            .form(DEFAULT_FORM);
        return courseClass;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseClass createUpdatedEntity(EntityManager em) {
        CourseClass courseClass = new CourseClass()
            .hours(UPDATED_HOURS)
            .form(UPDATED_FORM);
        return courseClass;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseClassResource courseClassResource = new CourseClassResource(courseClassService);
        this.restCourseClassMockMvc = MockMvcBuilders.standaloneSetup(courseClassResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        courseClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseClass() throws Exception {
        int databaseSizeBeforeCreate = courseClassRepository.findAll().size();

        // Create the CourseClass
        CourseClassDTO courseClassDTO = courseClassMapper.toDto(courseClass);
        restCourseClassMockMvc.perform(post("/api/course-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseClassDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseClass in the database
        List<CourseClass> courseClassList = courseClassRepository.findAll();
        assertThat(courseClassList).hasSize(databaseSizeBeforeCreate + 1);
        CourseClass testCourseClass = courseClassList.get(courseClassList.size() - 1);
        assertThat(testCourseClass.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testCourseClass.getForm()).isEqualTo(DEFAULT_FORM);
    }

    @Test
    @Transactional
    public void createCourseClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseClassRepository.findAll().size();

        // Create the CourseClass with an existing ID
        courseClass.setId(1L);
        CourseClassDTO courseClassDTO = courseClassMapper.toDto(courseClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseClassMockMvc.perform(post("/api/course-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseClass in the database
        List<CourseClass> courseClassList = courseClassRepository.findAll();
        assertThat(courseClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCourseClasses() throws Exception {
        // Initialize the database
        courseClassRepository.saveAndFlush(courseClass);

        // Get all the courseClassList
        restCourseClassMockMvc.perform(get("/api/course-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].form").value(hasItem(DEFAULT_FORM.toString())));
    }

    @Test
    @Transactional
    public void getCourseClass() throws Exception {
        // Initialize the database
        courseClassRepository.saveAndFlush(courseClass);

        // Get the courseClass
        restCourseClassMockMvc.perform(get("/api/course-classes/{id}", courseClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseClass.getId().intValue()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.form").value(DEFAULT_FORM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseClass() throws Exception {
        // Get the courseClass
        restCourseClassMockMvc.perform(get("/api/course-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseClass() throws Exception {
        // Initialize the database
        courseClassRepository.saveAndFlush(courseClass);

        int databaseSizeBeforeUpdate = courseClassRepository.findAll().size();

        // Update the courseClass
        CourseClass updatedCourseClass = courseClassRepository.findById(courseClass.getId()).get();
        // Disconnect from session so that the updates on updatedCourseClass are not directly saved in db
        em.detach(updatedCourseClass);
        updatedCourseClass
            .hours(UPDATED_HOURS)
            .form(UPDATED_FORM);
        CourseClassDTO courseClassDTO = courseClassMapper.toDto(updatedCourseClass);

        restCourseClassMockMvc.perform(put("/api/course-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseClassDTO)))
            .andExpect(status().isOk());

        // Validate the CourseClass in the database
        List<CourseClass> courseClassList = courseClassRepository.findAll();
        assertThat(courseClassList).hasSize(databaseSizeBeforeUpdate);
        CourseClass testCourseClass = courseClassList.get(courseClassList.size() - 1);
        assertThat(testCourseClass.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testCourseClass.getForm()).isEqualTo(UPDATED_FORM);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseClass() throws Exception {
        int databaseSizeBeforeUpdate = courseClassRepository.findAll().size();

        // Create the CourseClass
        CourseClassDTO courseClassDTO = courseClassMapper.toDto(courseClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseClassMockMvc.perform(put("/api/course-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseClass in the database
        List<CourseClass> courseClassList = courseClassRepository.findAll();
        assertThat(courseClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourseClass() throws Exception {
        // Initialize the database
        courseClassRepository.saveAndFlush(courseClass);

        int databaseSizeBeforeDelete = courseClassRepository.findAll().size();

        // Delete the courseClass
        restCourseClassMockMvc.perform(delete("/api/course-classes/{id}", courseClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseClass> courseClassList = courseClassRepository.findAll();
        assertThat(courseClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
