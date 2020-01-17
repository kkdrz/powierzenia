package edu.psi.powierzenia.web.rest;

import edu.psi.powierzenia.RedisTestContainerExtension;
import edu.psi.powierzenia.PowierzeniaApp;
import edu.psi.powierzenia.config.TestSecurityConfiguration;
import edu.psi.powierzenia.domain.ClassForm;
import edu.psi.powierzenia.repository.ClassFormRepository;
import edu.psi.powierzenia.service.ClassFormService;
import edu.psi.powierzenia.service.dto.ClassFormDTO;
import edu.psi.powierzenia.service.mapper.ClassFormMapper;
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

import edu.psi.powierzenia.domain.enumeration.ClassFormType;
/**
 * Integration tests for the {@link ClassFormResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
@ExtendWith(RedisTestContainerExtension.class)
public class ClassFormResourceIT {

    private static final ClassFormType DEFAULT_TYPE = ClassFormType.LABORATORY;
    private static final ClassFormType UPDATED_TYPE = ClassFormType.LECTURE;

    @Autowired
    private ClassFormRepository classFormRepository;

    @Autowired
    private ClassFormMapper classFormMapper;

    @Autowired
    private ClassFormService classFormService;

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

    private MockMvc restClassFormMockMvc;

    private ClassForm classForm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassFormResource classFormResource = new ClassFormResource(classFormService);
        this.restClassFormMockMvc = MockMvcBuilders.standaloneSetup(classFormResource)
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
    public static ClassForm createEntity(EntityManager em) {
        ClassForm classForm = new ClassForm()
            .type(DEFAULT_TYPE);
        return classForm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassForm createUpdatedEntity(EntityManager em) {
        ClassForm classForm = new ClassForm()
            .type(UPDATED_TYPE);
        return classForm;
    }

    @BeforeEach
    public void initTest() {
        classForm = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassForm() throws Exception {
        int databaseSizeBeforeCreate = classFormRepository.findAll().size();

        // Create the ClassForm
        ClassFormDTO classFormDTO = classFormMapper.toDto(classForm);
        restClassFormMockMvc.perform(post("/api/class-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classFormDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassForm in the database
        List<ClassForm> classFormList = classFormRepository.findAll();
        assertThat(classFormList).hasSize(databaseSizeBeforeCreate + 1);
        ClassForm testClassForm = classFormList.get(classFormList.size() - 1);
        assertThat(testClassForm.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createClassFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classFormRepository.findAll().size();

        // Create the ClassForm with an existing ID
        classForm.setId(1L);
        ClassFormDTO classFormDTO = classFormMapper.toDto(classForm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassFormMockMvc.perform(post("/api/class-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassForm in the database
        List<ClassForm> classFormList = classFormRepository.findAll();
        assertThat(classFormList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClassForms() throws Exception {
        // Initialize the database
        classFormRepository.saveAndFlush(classForm);

        // Get all the classFormList
        restClassFormMockMvc.perform(get("/api/class-forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getClassForm() throws Exception {
        // Initialize the database
        classFormRepository.saveAndFlush(classForm);

        // Get the classForm
        restClassFormMockMvc.perform(get("/api/class-forms/{id}", classForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classForm.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassForm() throws Exception {
        // Get the classForm
        restClassFormMockMvc.perform(get("/api/class-forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassForm() throws Exception {
        // Initialize the database
        classFormRepository.saveAndFlush(classForm);

        int databaseSizeBeforeUpdate = classFormRepository.findAll().size();

        // Update the classForm
        ClassForm updatedClassForm = classFormRepository.findById(classForm.getId()).get();
        // Disconnect from session so that the updates on updatedClassForm are not directly saved in db
        em.detach(updatedClassForm);
        updatedClassForm
            .type(UPDATED_TYPE);
        ClassFormDTO classFormDTO = classFormMapper.toDto(updatedClassForm);

        restClassFormMockMvc.perform(put("/api/class-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classFormDTO)))
            .andExpect(status().isOk());

        // Validate the ClassForm in the database
        List<ClassForm> classFormList = classFormRepository.findAll();
        assertThat(classFormList).hasSize(databaseSizeBeforeUpdate);
        ClassForm testClassForm = classFormList.get(classFormList.size() - 1);
        assertThat(testClassForm.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassForm() throws Exception {
        int databaseSizeBeforeUpdate = classFormRepository.findAll().size();

        // Create the ClassForm
        ClassFormDTO classFormDTO = classFormMapper.toDto(classForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassFormMockMvc.perform(put("/api/class-forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassForm in the database
        List<ClassForm> classFormList = classFormRepository.findAll();
        assertThat(classFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassForm() throws Exception {
        // Initialize the database
        classFormRepository.saveAndFlush(classForm);

        int databaseSizeBeforeDelete = classFormRepository.findAll().size();

        // Delete the classForm
        restClassFormMockMvc.perform(delete("/api/class-forms/{id}", classForm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassForm> classFormList = classFormRepository.findAll();
        assertThat(classFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
