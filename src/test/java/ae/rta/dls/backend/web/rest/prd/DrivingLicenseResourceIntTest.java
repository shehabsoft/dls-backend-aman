package ae.rta.dls.backend.web.rest.prd;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.prd.DrivingLicense;
import ae.rta.dls.backend.domain.type.DrivingLicenseProductJsonType;
import ae.rta.dls.backend.repository.prd.DrivingLicenseRepository;
import ae.rta.dls.backend.service.prd.DrivingLicenseService;
import ae.rta.dls.backend.service.dto.prd.DrivingLicenseDTO;
import ae.rta.dls.backend.service.mapper.prd.DrivingLicenseMapper;
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
 * Test class for the DrivingLicenseResource REST controller.
 *
 * @see DrivingLicenseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class DrivingLicenseResourceIntTest {

    private static final Long DEFAULT_SERVICE_REQUEST_ID = 1L;
    private static final Long UPDATED_SERVICE_REQUEST_ID = 2L;

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    private static final String DEFAULT_PRODUCT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_REMARKS = "BBBBBBBBBB";

    @Autowired
    private DrivingLicenseRepository drivingLicenseRepository;

    @Autowired
    private DrivingLicenseMapper drivingLicenseMapper;

    @Autowired
    private DrivingLicenseService drivingLicenseService;

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

    private MockMvc restDrivingLicenseMockMvc;

    private DrivingLicense drivingLicense;

    private static final String PRD_DRIVING_LICENSES_API = "/api/prd/driving-licenses";

    private static final String PRD_DRIVING_LICENSES_BY_ID_API = PRD_DRIVING_LICENSES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrivingLicenseResource drivingLicenseResource = new DrivingLicenseResource(drivingLicenseService);
        this.restDrivingLicenseMockMvc = MockMvcBuilders.standaloneSetup(drivingLicenseResource)
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
    public static DrivingLicense createEntity(EntityManager em) {
        return new DrivingLicense()
            .serviceRequestId(DEFAULT_SERVICE_REQUEST_ID)
            .applicationId(DEFAULT_APPLICATION_ID)
            .productDocument(new DrivingLicenseProductJsonType())
            .technicalRemarks(DEFAULT_TECHNICAL_REMARKS);
    }

    @Before
    public void initTest() {
        drivingLicense = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrivingLicense() throws Exception {
        int databaseSizeBeforeCreate = drivingLicenseRepository.findAll().size();

        // Create the DrivingLicense
        DrivingLicenseDTO drivingLicenseDTO = drivingLicenseMapper.toDto(drivingLicense);
        restDrivingLicenseMockMvc.perform(post(PRD_DRIVING_LICENSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseDTO)))
            .andExpect(status().isCreated());

        // Validate the DrivingLicense in the database
        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();
        assertThat(drivingLicenseList).hasSize(databaseSizeBeforeCreate + 1);
        DrivingLicense testDrivingLicense = drivingLicenseList.get(drivingLicenseList.size() - 1);
        assertThat(testDrivingLicense.getServiceRequestId()).isEqualTo(DEFAULT_SERVICE_REQUEST_ID);
        assertThat(testDrivingLicense.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testDrivingLicense.getProductDocument()).isEqualTo(DEFAULT_PRODUCT_DOCUMENT);
        assertThat(testDrivingLicense.getTechnicalRemarks()).isEqualTo(DEFAULT_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void createDrivingLicenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drivingLicenseRepository.findAll().size();

        // Create the DrivingLicense with an existing ID
        drivingLicense.setId(1L);
        DrivingLicenseDTO drivingLicenseDTO = drivingLicenseMapper.toDto(drivingLicense);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrivingLicenseMockMvc.perform(post(PRD_DRIVING_LICENSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DrivingLicense in the database
        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();
        assertThat(drivingLicenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkServiceRequestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = drivingLicenseRepository.findAll().size();
        // set the field null
        drivingLicense.setServiceRequestId(null);

        // Create the DrivingLicense, which fails.
        DrivingLicenseDTO drivingLicenseDTO = drivingLicenseMapper.toDto(drivingLicense);

        restDrivingLicenseMockMvc.perform(post(PRD_DRIVING_LICENSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();
        assertThat(drivingLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = drivingLicenseRepository.findAll().size();
        // set the field null
        drivingLicense.setApplicationId(null);

        // Create the DrivingLicense, which fails.
        DrivingLicenseDTO drivingLicenseDTO = drivingLicenseMapper.toDto(drivingLicense);

        restDrivingLicenseMockMvc.perform(post(PRD_DRIVING_LICENSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseDTO)))
            .andExpect(status().isBadRequest());

        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();
        assertThat(drivingLicenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrivingLicenses() throws Exception {
        // Initialize the database
        drivingLicenseRepository.saveAndFlush(drivingLicense);

        // Get all the drivingLicenseList
        restDrivingLicenseMockMvc.perform(get("/api/prd/driving-licenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drivingLicense.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceRequestId").value(hasItem(DEFAULT_SERVICE_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productDocument").value(hasItem(DEFAULT_PRODUCT_DOCUMENT)))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)));
    }

    @Test
    @Transactional
    public void getDrivingLicense() throws Exception {
        // Initialize the database
        drivingLicenseRepository.saveAndFlush(drivingLicense);

        // Get the drivingLicense
        restDrivingLicenseMockMvc.perform(get(PRD_DRIVING_LICENSES_BY_ID_API, drivingLicense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drivingLicense.getId().intValue()))
            .andExpect(jsonPath("$.serviceRequestId").value(DEFAULT_SERVICE_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.productDocument").value(DEFAULT_PRODUCT_DOCUMENT))
            .andExpect(jsonPath("$.technicalRemarks").value(DEFAULT_TECHNICAL_REMARKS));
    }

    @Test
    @Transactional
    public void getNonExistingDrivingLicense() throws Exception {
        // Get the drivingLicense
        restDrivingLicenseMockMvc.perform(get(PRD_DRIVING_LICENSES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrivingLicense() throws Exception {
        // Initialize the database
        drivingLicenseRepository.saveAndFlush(drivingLicense);

        int databaseSizeBeforeUpdate = drivingLicenseRepository.findAll().size();

        // Update the drivingLicense
        Optional<DrivingLicense> optionalDrivingLicense = drivingLicenseRepository.findById(drivingLicense.getId());
        if (! optionalDrivingLicense.isPresent()) {
            return;
        }

        DrivingLicense updatedDrivingLicense = optionalDrivingLicense.get();
        // Disconnect from session so that the updates on updatedDrivingLicense are not directly saved in db
        em.detach(updatedDrivingLicense);
        updatedDrivingLicense
            .serviceRequestId(UPDATED_SERVICE_REQUEST_ID)
            .applicationId(UPDATED_APPLICATION_ID)
            .productDocument(new DrivingLicenseProductJsonType())
            .technicalRemarks(UPDATED_TECHNICAL_REMARKS);
        DrivingLicenseDTO drivingLicenseDTO = drivingLicenseMapper.toDto(updatedDrivingLicense);

        restDrivingLicenseMockMvc.perform(put(PRD_DRIVING_LICENSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseDTO)))
            .andExpect(status().isOk());

        // Validate the DrivingLicense in the database
        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();
        assertThat(drivingLicenseList).hasSize(databaseSizeBeforeUpdate);
        DrivingLicense testDrivingLicense = drivingLicenseList.get(drivingLicenseList.size() - 1);
        assertThat(testDrivingLicense.getServiceRequestId()).isEqualTo(UPDATED_SERVICE_REQUEST_ID);
        assertThat(testDrivingLicense.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testDrivingLicense.getProductDocument()).isEqualTo(UPDATED_PRODUCT_DOCUMENT);
        assertThat(testDrivingLicense.getTechnicalRemarks()).isEqualTo(UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingDrivingLicense() throws Exception {
        int databaseSizeBeforeUpdate = drivingLicenseRepository.findAll().size();

        // Create the DrivingLicense
        DrivingLicenseDTO drivingLicenseDTO = drivingLicenseMapper.toDto(drivingLicense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrivingLicenseMockMvc.perform(put(PRD_DRIVING_LICENSES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DrivingLicense in the database
        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();
        assertThat(drivingLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrivingLicense() throws Exception {
        // Initialize the database
        drivingLicenseRepository.saveAndFlush(drivingLicense);

        int databaseSizeBeforeDelete = drivingLicenseRepository.findAll().size();

        // Delete the drivingLicense
        restDrivingLicenseMockMvc.perform(delete(PRD_DRIVING_LICENSES_BY_ID_API, drivingLicense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();
        assertThat(drivingLicenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrivingLicense.class);
        DrivingLicense drivingLicense1 = new DrivingLicense();
        drivingLicense1.setId(1L);
        DrivingLicense drivingLicense2 = new DrivingLicense();
        drivingLicense2.setId(drivingLicense1.getId());
        assertThat(drivingLicense1).isEqualTo(drivingLicense2);
        drivingLicense2.setId(2L);
        assertThat(drivingLicense1).isNotEqualTo(drivingLicense2);
        drivingLicense1.setId(null);
        assertThat(drivingLicense1).isNotEqualTo(drivingLicense2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrivingLicenseDTO.class);
        DrivingLicenseDTO drivingLicenseDTO1 = new DrivingLicenseDTO();
        drivingLicenseDTO1.setId(1L);
        DrivingLicenseDTO drivingLicenseDTO2 = new DrivingLicenseDTO();
        assertThat(drivingLicenseDTO1).isNotEqualTo(drivingLicenseDTO2);
        drivingLicenseDTO2.setId(drivingLicenseDTO1.getId());
        assertThat(drivingLicenseDTO1).isEqualTo(drivingLicenseDTO2);
        drivingLicenseDTO2.setId(2L);
        assertThat(drivingLicenseDTO1).isNotEqualTo(drivingLicenseDTO2);
        drivingLicenseDTO1.setId(null);
        assertThat(drivingLicenseDTO1).isNotEqualTo(drivingLicenseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(drivingLicenseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(drivingLicenseMapper.fromId(null)).isNull();
    }
}
