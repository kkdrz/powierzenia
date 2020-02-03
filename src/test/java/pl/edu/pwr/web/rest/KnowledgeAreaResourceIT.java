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
import pl.edu.pwr.domain.KnowledgeArea;
import pl.edu.pwr.repository.KnowledgeAreaRepository;
import pl.edu.pwr.service.KnowledgeAreaService;
import pl.edu.pwr.service.dto.KnowledgeAreaDTO;
import pl.edu.pwr.service.mapper.KnowledgeAreaMapper;
import pl.edu.pwr.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.edu.pwr.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link KnowledgeAreaResource} REST controller.
 */
@SpringBootTest(classes = {PowierzeniaApp.class, TestSecurityConfiguration.class})
public class KnowledgeAreaResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private KnowledgeAreaRepository knowledgeAreaRepository;

    @Autowired
    private KnowledgeAreaMapper knowledgeAreaMapper;

    @Autowired
    private KnowledgeAreaService knowledgeAreaService;

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

    private MockMvc restKnowledgeAreaMockMvc;

    private KnowledgeArea knowledgeArea;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KnowledgeArea createEntity(EntityManager em) {
        KnowledgeArea knowledgeArea = new KnowledgeArea()
            .type(DEFAULT_TYPE);
        return knowledgeArea;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KnowledgeArea createUpdatedEntity(EntityManager em) {
        KnowledgeArea knowledgeArea = new KnowledgeArea()
            .type(UPDATED_TYPE);
        return knowledgeArea;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KnowledgeAreaResource knowledgeAreaResource = new KnowledgeAreaResource(knowledgeAreaService);
        this.restKnowledgeAreaMockMvc = MockMvcBuilders.standaloneSetup(knowledgeAreaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @BeforeEach
    public void initTest() {
        knowledgeArea = createEntity(em);
    }

    @Test
    @Transactional
    public void createKnowledgeArea() throws Exception {
        int databaseSizeBeforeCreate = knowledgeAreaRepository.findAll().size();

        // Create the KnowledgeArea
        KnowledgeAreaDTO knowledgeAreaDTO = knowledgeAreaMapper.toDto(knowledgeArea);
        restKnowledgeAreaMockMvc.perform(post("/api/knowledge-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(knowledgeAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the KnowledgeArea in the database
        List<KnowledgeArea> knowledgeAreaList = knowledgeAreaRepository.findAll();
        assertThat(knowledgeAreaList).hasSize(databaseSizeBeforeCreate + 1);
        KnowledgeArea testKnowledgeArea = knowledgeAreaList.get(knowledgeAreaList.size() - 1);
        assertThat(testKnowledgeArea.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createKnowledgeAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = knowledgeAreaRepository.findAll().size();

        // Create the KnowledgeArea with an existing ID
        knowledgeArea.setId(1L);
        KnowledgeAreaDTO knowledgeAreaDTO = knowledgeAreaMapper.toDto(knowledgeArea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKnowledgeAreaMockMvc.perform(post("/api/knowledge-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(knowledgeAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KnowledgeArea in the database
        List<KnowledgeArea> knowledgeAreaList = knowledgeAreaRepository.findAll();
        assertThat(knowledgeAreaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKnowledgeAreas() throws Exception {
        // Initialize the database
        knowledgeAreaRepository.saveAndFlush(knowledgeArea);

        // Get all the knowledgeAreaList
        restKnowledgeAreaMockMvc.perform(get("/api/knowledge-areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(knowledgeArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    public void getKnowledgeArea() throws Exception {
        // Initialize the database
        knowledgeAreaRepository.saveAndFlush(knowledgeArea);

        // Get the knowledgeArea
        restKnowledgeAreaMockMvc.perform(get("/api/knowledge-areas/{id}", knowledgeArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(knowledgeArea.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingKnowledgeArea() throws Exception {
        // Get the knowledgeArea
        restKnowledgeAreaMockMvc.perform(get("/api/knowledge-areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKnowledgeArea() throws Exception {
        // Initialize the database
        knowledgeAreaRepository.saveAndFlush(knowledgeArea);

        int databaseSizeBeforeUpdate = knowledgeAreaRepository.findAll().size();

        // Update the knowledgeArea
        KnowledgeArea updatedKnowledgeArea = knowledgeAreaRepository.findById(knowledgeArea.getId()).get();
        // Disconnect from session so that the updates on updatedKnowledgeArea are not directly saved in db
        em.detach(updatedKnowledgeArea);
        updatedKnowledgeArea
            .type(UPDATED_TYPE);
        KnowledgeAreaDTO knowledgeAreaDTO = knowledgeAreaMapper.toDto(updatedKnowledgeArea);

        restKnowledgeAreaMockMvc.perform(put("/api/knowledge-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(knowledgeAreaDTO)))
            .andExpect(status().isOk());

        // Validate the KnowledgeArea in the database
        List<KnowledgeArea> knowledgeAreaList = knowledgeAreaRepository.findAll();
        assertThat(knowledgeAreaList).hasSize(databaseSizeBeforeUpdate);
        KnowledgeArea testKnowledgeArea = knowledgeAreaList.get(knowledgeAreaList.size() - 1);
        assertThat(testKnowledgeArea.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingKnowledgeArea() throws Exception {
        int databaseSizeBeforeUpdate = knowledgeAreaRepository.findAll().size();

        // Create the KnowledgeArea
        KnowledgeAreaDTO knowledgeAreaDTO = knowledgeAreaMapper.toDto(knowledgeArea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKnowledgeAreaMockMvc.perform(put("/api/knowledge-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(knowledgeAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KnowledgeArea in the database
        List<KnowledgeArea> knowledgeAreaList = knowledgeAreaRepository.findAll();
        assertThat(knowledgeAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKnowledgeArea() throws Exception {
        // Initialize the database
        knowledgeAreaRepository.saveAndFlush(knowledgeArea);

        int databaseSizeBeforeDelete = knowledgeAreaRepository.findAll().size();

        // Delete the knowledgeArea
        restKnowledgeAreaMockMvc.perform(delete("/api/knowledge-areas/{id}", knowledgeArea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KnowledgeArea> knowledgeAreaList = knowledgeAreaRepository.findAll();
        assertThat(knowledgeAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
