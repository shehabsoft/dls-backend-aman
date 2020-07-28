package ae.rta.dls.backend.web.rest.prd;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.prd.MedicalFitness;
import ae.rta.dls.backend.repository.prd.MedicalFitnessRepository;
import ae.rta.dls.backend.service.prd.MedicalFitnessService;
import ae.rta.dls.backend.service.dto.prd.MedicalFitnessDTO;
import ae.rta.dls.backend.service.mapper.prd.MedicalFitnessMapper;
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
 * Test class for the MedicalFitnessResource REST controller.
 *
 * @see MedicalFitnessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class MedicalFitnessResourceIntTest {

    private static final Long DEFAULT_SERVICE_REQUEST_ID = 1L;
    private static final Long UPDATED_SERVICE_REQUEST_ID = 2L;

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    private static final String DEFAULT_PRODUCT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_REMARKS = "BBBBBBBBBB";

    @Autowired
    private MedicalFitnessRepository medicalFitnessRepository;

    @Autowired
    private MedicalFitnessMapper medicalFitnessMapper;

    @Autowired
    private MedicalFitnessService medicalFitnessService;

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

    private MockMvc restMedicalFitnessMockMvc;

    private MedicalFitness medicalFitness;

    private static final String PRD_MEDICAL_FITNESSES_API = "/api/prd/medical-fitnesses";

    private static final String PRD_MEDICAL_FITNESSES_BY_ID_API = PRD_MEDICAL_FITNESSES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalFitnessResource medicalFitnessResource = new MedicalFitnessResource(medicalFitnessService);
        this.restMedicalFitnessMockMvc = MockMvcBuilders.standaloneSetup(medicalFitnessResource)
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
    public static MedicalFitness createEntity(EntityManager em) {
        return new MedicalFitness()
            .serviceRequestId(DEFAULT_SERVICE_REQUEST_ID)
            .applicationId(DEFAULT_APPLICATION_ID)
            .productDocument(DEFAULT_PRODUCT_DOCUMENT)
            .technicalRemarks(DEFAULT_TECHNICAL_REMARKS);
    }

    @Before
    public void initTest() {
        medicalFitness = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalFitness() throws Exception {
        int databaseSizeBeforeCreate = medicalFitnessRepository.findAll().size();

        // Create the MedicalFitness
        MedicalFitnessDTO medicalFitnessDTO = medicalFitnessMapper.toDto(medicalFitness);
        restMedicalFitnessMockMvc.perform(post(PRD_MEDICAL_FITNESSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalFitnessDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalFitness in the database
        List<MedicalFitness> medicalFitnessList = medicalFitnessRepository.findAll();
        assertThat(medicalFitnessList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalFitness testMedicalFitness = medicalFitnessList.get(medicalFitnessList.size() - 1);
        assertThat(testMedicalFitness.getServiceRequestId()).isEqualTo(DEFAULT_SERVICE_REQUEST_ID);
        assertThat(testMedicalFitness.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testMedicalFitness.getProductDocument()).isEqualTo(DEFAULT_PRODUCT_DOCUMENT);
        assertThat(testMedicalFitness.getTechnicalRemarks()).isEqualTo(DEFAULT_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void createMedicalFitnessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalFitnessRepository.findAll().size();

        // Create the MedicalFitness with an existing ID
        medicalFitness.setId(1L);
        MedicalFitnessDTO medicalFitnessDTO = medicalFitnessMapper.toDto(medicalFitness);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalFitnessMockMvc.perform(post(PRD_MEDICAL_FITNESSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalFitnessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalFitness in the database
        List<MedicalFitness> medicalFitnessList = medicalFitnessRepository.findAll();
        assertThat(medicalFitnessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkServiceRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalFitnessRepository.findAll().size();
        // set the field null
        medicalFitness.setServiceRequestId(null);

        // Create the MedicalFitness, which fails.
        MedicalFitnessDTO medicalFitnessDTO = medicalFitnessMapper.toDto(medicalFitness);

        restMedicalFitnessMockMvc.perform(post(PRD_MEDICAL_FITNESSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalFitnessDTO)))
            .andExpect(status().isBadRequest());

        List<MedicalFitness> medicalFitnessList = medicalFitnessRepository.findAll();
        assertThat(medicalFitnessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicalFitnessRepository.findAll().size();
        // set the field null
        medicalFitness.setApplicationId(null);

        // Create the MedicalFitness, which fails.
        MedicalFitnessDTO medicalFitnessDTO = medicalFitnessMapper.toDto(medicalFitness);

        restMedicalFitnessMockMvc.perform(post(PRD_MEDICAL_FITNESSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalFitnessDTO)))
            .andExpect(status().isBadRequest());

        List<MedicalFitness> medicalFitnessList = medicalFitnessRepository.findAll();
        assertThat(medicalFitnessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicalFitnesses() throws Exception {
        // Initialize the database
        medicalFitnessRepository.saveAndFlush(medicalFitness);

        // Get all the medicalFitnessList
        restMedicalFitnessMockMvc.perform(get("/api/prd/medical-fitnesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalFitness.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceRequestId").value(hasItem(DEFAULT_SERVICE_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productDocument").value(hasItem(DEFAULT_PRODUCT_DOCUMENT)))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)));
    }

    @Test
    @Transactional
    public void getMedicalFitness() throws Exception {
        // Initialize the database
        medicalFitnessRepository.saveAndFlush(medicalFitness);

        // Get the medicalFitness
        restMedicalFitnessMockMvc.perform(get(PRD_MEDICAL_FITNESSES_BY_ID_API, medicalFitness.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalFitness.getId().intValue()))
            .andExpect(jsonPath("$.serviceRequestId").value(DEFAULT_SERVICE_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.productDocument").value(DEFAULT_PRODUCT_DOCUMENT))
            .andExpect(jsonPath("$.technicalRemarks").value(DEFAULT_TECHNICAL_REMARKS));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalFitness() throws Exception {
        // Get the medicalFitness
        restMedicalFitnessMockMvc.perform(get(PRD_MEDICAL_FITNESSES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalFitness() throws Exception {
        // Initialize the database
        medicalFitnessRepository.saveAndFlush(medicalFitness);

        int databaseSizeBeforeUpdate = medicalFitnessRepository.findAll().size();

        // Update the medicalFitness
        Optional<MedicalFitness> optionalMedicalFitness = medicalFitnessRepository.findById(medicalFitness.getId());
        if (! optionalMedicalFitness.isPresent()) {
            return;
        }

        MedicalFitness updatedMedicalFitness = optionalMedicalFitness.get();
        // Disconnect from session so that the updates on updatedMedicalFitness are not directly saved in db
        em.detach(updatedMedicalFitness);
        updatedMedicalFitness
            .serviceRequestId(UPDATED_SERVICE_REQUEST_ID)
            .applicationId(UPDATED_APPLICATION_ID)
            .productDocument(UPDATED_PRODUCT_DOCUMENT)
            .technicalRemarks(UPDATED_TECHNICAL_REMARKS);
        MedicalFitnessDTO medicalFitnessDTO = medicalFitnessMapper.toDto(updatedMedicalFitness);

        restMedicalFitnessMockMvc.perform(put(PRD_MEDICAL_FITNESSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalFitnessDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalFitness in the database
        List<MedicalFitness> medicalFitnessList = medicalFitnessRepository.findAll();
        assertThat(medicalFitnessList).hasSize(databaseSizeBeforeUpdate);
        MedicalFitness testMedicalFitness = medicalFitnessList.get(medicalFitnessList.size() - 1);
        assertThat(testMedicalFitness.getServiceRequestId()).isEqualTo(UPDATED_SERVICE_REQUEST_ID);
        assertThat(testMedicalFitness.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testMedicalFitness.getProductDocument()).isEqualTo(UPDATED_PRODUCT_DOCUMENT);
        assertThat(testMedicalFitness.getTechnicalRemarks()).isEqualTo(UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalFitness() throws Exception {
        int databaseSizeBeforeUpdate = medicalFitnessRepository.findAll().size();

        // Create the MedicalFitness
        MedicalFitnessDTO medicalFitnessDTO = medicalFitnessMapper.toDto(medicalFitness);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalFitnessMockMvc.perform(put(PRD_MEDICAL_FITNESSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalFitnessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalFitness in the database
        List<MedicalFitness> medicalFitnessList = medicalFitnessRepository.findAll();
        assertThat(medicalFitnessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalFitness() throws Exception {
        // Initialize the database
        medicalFitnessRepository.saveAndFlush(medicalFitness);

        int databaseSizeBeforeDelete = medicalFitnessRepository.findAll().size();

        // Delete the medicalFitness
        restMedicalFitnessMockMvc.perform(delete(PRD_MEDICAL_FITNESSES_BY_ID_API, medicalFitness.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalFitness> medicalFitnessList = medicalFitnessRepository.findAll();
        assertThat(medicalFitnessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalFitness.class);
        MedicalFitness medicalFitness1 = new MedicalFitness();
        medicalFitness1.setId(1L);
        MedicalFitness medicalFitness2 = new MedicalFitness();
        medicalFitness2.setId(medicalFitness1.getId());
        assertThat(medicalFitness1).isEqualTo(medicalFitness2);
        medicalFitness2.setId(2L);
        assertThat(medicalFitness1).isNotEqualTo(medicalFitness2);
        medicalFitness1.setId(null);
        assertThat(medicalFitness1).isNotEqualTo(medicalFitness2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalFitnessDTO.class);
        MedicalFitnessDTO medicalFitnessDTO1 = new MedicalFitnessDTO();
        medicalFitnessDTO1.setId(1L);
        MedicalFitnessDTO medicalFitnessDTO2 = new MedicalFitnessDTO();
        assertThat(medicalFitnessDTO1).isNotEqualTo(medicalFitnessDTO2);
        medicalFitnessDTO2.setId(medicalFitnessDTO1.getId());
        assertThat(medicalFitnessDTO1).isEqualTo(medicalFitnessDTO2);
        medicalFitnessDTO2.setId(2L);
        assertThat(medicalFitnessDTO1).isNotEqualTo(medicalFitnessDTO2);
        medicalFitnessDTO1.setId(null);
        assertThat(medicalFitnessDTO1).isNotEqualTo(medicalFitnessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicalFitnessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicalFitnessMapper.fromId(null)).isNull();
    }
}
