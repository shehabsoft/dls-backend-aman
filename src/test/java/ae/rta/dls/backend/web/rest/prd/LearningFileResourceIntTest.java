package ae.rta.dls.backend.web.rest.prd;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.prd.LearningFile;
import ae.rta.dls.backend.domain.type.LearningFileProductJsonType;
import ae.rta.dls.backend.repository.prd.LearningFileRepository;
import ae.rta.dls.backend.service.prd.LearningFileService;
import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;
import ae.rta.dls.backend.service.mapper.prd.LearningFileMapper;
import ae.rta.dls.backend.web.rest.TestUtil;
import ae.rta.dls.backend.web.rest.util.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


import static ae.rta.dls.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LearningFileResource REST controller.
 *
 * @see LearningFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class LearningFileResourceIntTest {

    private static final String DEFAULT_PERSONA_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONA_VERSION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA_VERSION_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_SERVICE_REQUEST_ID = 1L;
    private static final Long UPDATED_SERVICE_REQUEST_ID = 2L;

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    private static final LearningFileProductJsonType DEFAULT_PRODUCT_DOCUMENT = new LearningFileProductJsonType();
    private static final LearningFileProductJsonType UPDATED_PRODUCT_DOCUMENT = new LearningFileProductJsonType();

    private static final String DEFAULT_TECHNICAL_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_REMARKS = "BBBBBBBBBB";

    @Autowired
    private LearningFileRepository learningFileRepository;

    @Autowired
    private LearningFileMapper learningFileMapper;

    @Autowired
    private LearningFileService learningFileService;

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

    private MockMvc restLearningFileMockMvc;

    private LearningFile learningFile;

    private static final String PRD_LEARNING_FILE_API = "/api/prd/learning-files";

    private static final String PRD_LEARNING_FILE_BY_ID_API = PRD_LEARNING_FILE_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LearningFileResource learningFileResource = new LearningFileResource(learningFileService);
        this.restLearningFileMockMvc = MockMvcBuilders.standaloneSetup(learningFileResource)
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
    public static LearningFile createEntity(EntityManager em) {
        return new LearningFile()
            .personaCategoryCode(DEFAULT_PERSONA_CATEGORY_CODE)
            .personaVersionCode(DEFAULT_PERSONA_VERSION_CODE)
            .serviceRequestId(DEFAULT_SERVICE_REQUEST_ID)
            .applicationId(DEFAULT_APPLICATION_ID)
            .productDocument(DEFAULT_PRODUCT_DOCUMENT)
            .technicalRemarks(DEFAULT_TECHNICAL_REMARKS);
    }

    @Before
    public void initTest() {
        learningFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createLearningFile() throws Exception {
        int databaseSizeBeforeCreate = learningFileRepository.findAll().size();

        // Create the LearningFile
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(learningFile);
        restLearningFileMockMvc.perform(post(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isCreated());

        // Validate the LearningFile in the database
        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeCreate + 1);
        LearningFile testLearningFile = learningFileList.get(learningFileList.size() - 1);
        assertThat(testLearningFile.getPersonaCategoryCode()).isEqualTo(DEFAULT_PERSONA_CATEGORY_CODE);
        assertThat(testLearningFile.getPersonaVersionCode()).isEqualTo(DEFAULT_PERSONA_VERSION_CODE);
        assertThat(testLearningFile.getServiceRequestId()).isEqualTo(DEFAULT_SERVICE_REQUEST_ID);
        assertThat(testLearningFile.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testLearningFile.getProductDocument()).isEqualTo(DEFAULT_PRODUCT_DOCUMENT);
        assertThat(testLearningFile.getTechnicalRemarks()).isEqualTo(DEFAULT_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void createLearningFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = learningFileRepository.findAll().size();

        // Create the LearningFile with an existing ID
        learningFile.setId(1L);
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(learningFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLearningFileMockMvc.perform(post(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LearningFile in the database
        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPersonaCategoryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningFileRepository.findAll().size();
        // set the field null
        learningFile.setPersonaCategoryCode(null);

        // Create the LearningFile, which fails.
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(learningFile);

        restLearningFileMockMvc.perform(post(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isBadRequest());

        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPersonaVersionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningFileRepository.findAll().size();
        // set the field null
        learningFile.setPersonaVersionCode(null);

        // Create the LearningFile, which fails.
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(learningFile);

        restLearningFileMockMvc.perform(post(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isBadRequest());

        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningFileRepository.findAll().size();
        // set the field null
        learningFile.setServiceRequestId(null);

        // Create the LearningFile, which fails.
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(learningFile);

        restLearningFileMockMvc.perform(post(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isBadRequest());

        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = learningFileRepository.findAll().size();
        // set the field null
        learningFile.setApplicationId(null);

        // Create the LearningFile, which fails.
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(learningFile);

        restLearningFileMockMvc.perform(post(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isBadRequest());

        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLearningFiles() throws Exception {
        // Initialize the database
        learningFileRepository.saveAndFlush(learningFile);

        // Get all the learningFileList
        restLearningFileMockMvc.perform(get("/api/prd/learning-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(learningFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].personaCategoryCode").value(hasItem(DEFAULT_PERSONA_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].personaVersionCode").value(hasItem(DEFAULT_PERSONA_VERSION_CODE)))
            .andExpect(jsonPath("$.[*].serviceRequestId").value(hasItem(DEFAULT_SERVICE_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productDocument").value(hasItem(DEFAULT_PRODUCT_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)));
    }

    @Test
    @Transactional
    public void getLearningFile() throws Exception {
        // Initialize the database
        learningFileRepository.saveAndFlush(learningFile);

        // Get the learningFile
        restLearningFileMockMvc.perform(get(PRD_LEARNING_FILE_BY_ID_API, learningFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(learningFile.getId().intValue()))
            .andExpect(jsonPath("$.personaCategoryCode").value(DEFAULT_PERSONA_CATEGORY_CODE))
            .andExpect(jsonPath("$.personaVersionCode").value(DEFAULT_PERSONA_VERSION_CODE))
            .andExpect(jsonPath("$.serviceRequestId").value(DEFAULT_SERVICE_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.productDocument").value(DEFAULT_PRODUCT_DOCUMENT.toString()))
            .andExpect(jsonPath("$.technicalRemarks").value(DEFAULT_TECHNICAL_REMARKS));
    }

    @Test
    @Transactional
    public void getNonExistingLearningFile() throws Exception {
        // Get the learningFile
        restLearningFileMockMvc.perform(get(PRD_LEARNING_FILE_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLearningFile() throws Exception {
        // Initialize the database
        learningFileRepository.saveAndFlush(learningFile);

        int databaseSizeBeforeUpdate = learningFileRepository.findAll().size();

        // Update the learningFile
        Optional<LearningFile> optionalLearningFile = learningFileRepository.findById(learningFile.getId());
        if (! optionalLearningFile.isPresent()) {
            return;
        }

        LearningFile updatedLearningFile = optionalLearningFile.get();
        // Disconnect from session so that the updates on updatedLearningFile are not directly saved in db
        em.detach(updatedLearningFile);
        updatedLearningFile
            .personaCategoryCode(UPDATED_PERSONA_CATEGORY_CODE)
            .personaVersionCode(UPDATED_PERSONA_VERSION_CODE)
            .serviceRequestId(UPDATED_SERVICE_REQUEST_ID)
            .applicationId(UPDATED_APPLICATION_ID)
            .productDocument(UPDATED_PRODUCT_DOCUMENT)
            .technicalRemarks(DEFAULT_TECHNICAL_REMARKS);
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(updatedLearningFile);

        restLearningFileMockMvc.perform(put(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isOk());

        // Validate the LearningFile in the database
        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeUpdate);
        LearningFile testLearningFile = learningFileList.get(learningFileList.size() - 1);
        assertThat(testLearningFile.getPersonaCategoryCode()).isEqualTo(UPDATED_PERSONA_CATEGORY_CODE);
        assertThat(testLearningFile.getPersonaVersionCode()).isEqualTo(UPDATED_PERSONA_VERSION_CODE);
        assertThat(testLearningFile.getServiceRequestId()).isEqualTo(UPDATED_SERVICE_REQUEST_ID);
        assertThat(testLearningFile.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testLearningFile.getProductDocument()).isEqualTo(UPDATED_PRODUCT_DOCUMENT);
        assertThat(testLearningFile.getTechnicalRemarks()).isEqualTo(UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingLearningFile() throws Exception {
        int databaseSizeBeforeUpdate = learningFileRepository.findAll().size();

        // Create the LearningFile
        LearningFileDTO learningFileDTO = learningFileMapper.toDto(learningFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLearningFileMockMvc.perform(put(PRD_LEARNING_FILE_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(learningFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LearningFile in the database
        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLearningFile() throws Exception {
        // Initialize the database
        learningFileRepository.saveAndFlush(learningFile);

        int databaseSizeBeforeDelete = learningFileRepository.findAll().size();

        // Delete the learningFile
        restLearningFileMockMvc.perform(delete(PRD_LEARNING_FILE_BY_ID_API, learningFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LearningFile> learningFileList = learningFileRepository.findAll();
        assertThat(learningFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LearningFile.class);
        LearningFile learningFile1 = new LearningFile();
        learningFile1.setId(1L);
        LearningFile learningFile2 = new LearningFile();
        learningFile2.setId(learningFile1.getId());
        assertThat(learningFile1).isEqualTo(learningFile2);
        learningFile2.setId(2L);
        assertThat(learningFile1).isNotEqualTo(learningFile2);
        learningFile1.setId(null);
        assertThat(learningFile1).isNotEqualTo(learningFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LearningFileDTO.class);
        LearningFileDTO learningFileDTO1 = new LearningFileDTO();
        learningFileDTO1.setId(1L);
        LearningFileDTO learningFileDTO2 = new LearningFileDTO();
        assertThat(learningFileDTO1).isNotEqualTo(learningFileDTO2);
        learningFileDTO2.setId(learningFileDTO1.getId());
        assertThat(learningFileDTO1).isEqualTo(learningFileDTO2);
        learningFileDTO2.setId(2L);
        assertThat(learningFileDTO1).isNotEqualTo(learningFileDTO2);
        learningFileDTO1.setId(null);
        assertThat(learningFileDTO1).isNotEqualTo(learningFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(learningFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(learningFileMapper.fromId(null)).isNull();
    }
}
