package ae.rta.dls.backend.web.rest.sct;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationTypeStatus;
import ae.rta.dls.backend.domain.sct.ApplicationType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.repository.sct.ApplicationTypeRepository;
import ae.rta.dls.backend.service.sct.ApplicationTypeService;
import ae.rta.dls.backend.service.dto.sct.ApplicationTypeDTO;
import ae.rta.dls.backend.service.mapper.sct.ApplicationTypeMapper;
import ae.rta.dls.backend.web.rest.TestUtil;
import ae.rta.dls.backend.web.rest.util.ExceptionTranslator;

import org.hamcrest.Matchers;
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


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApplicationTypeResource REST controller.
 *
 * @see ApplicationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ApplicationTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final MultilingualJsonType DEFAULT_DESCRIPTION = new MultilingualJsonType("descriptionAr","descriptionEn");
    private static final MultilingualJsonType UPDATED_DESCRIPTION = new MultilingualJsonType("descriptionArabic","descriptionEnglish");

    private static final MultilingualJsonType DEFAULT_SUMMARY = new MultilingualJsonType("summaryAr","summaryEn");
    private static final MultilingualJsonType UPDATED_SUMMARY = new MultilingualJsonType("summaryArabic","summaryEnglish");

    private static final ApplicationTypeStatus DEFAULT_STATUS = ApplicationTypeStatus.ACTIVE;
    private static final ApplicationTypeStatus UPDATED_STATUS = ApplicationTypeStatus.INACTIVE;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    @Autowired
    private ApplicationTypeRepository applicationTypeRepository;

    @Autowired
    private ApplicationTypeMapper applicationTypeMapper;

    @Autowired
    private ApplicationTypeService applicationTypeService;

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

    private MockMvc restApplicationTypeMockMvc;

    private ApplicationType applicationType;

    private static final String SCT_APPLICATION_TYPES_API = "/api/sct/application-types";

    private static final String SCT_APPLICATION_TYPES_BY_ID_API = SCT_APPLICATION_TYPES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationTypeResource applicationTypeResource = new ApplicationTypeResource(applicationTypeService);
        this.restApplicationTypeMockMvc = MockMvcBuilders.standaloneSetup(applicationTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(TestUtil.createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationType createEntity(EntityManager em) {
        return new ApplicationType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .summary(DEFAULT_SUMMARY)
            .status(DEFAULT_STATUS)
            .sortOrder(DEFAULT_SORT_ORDER);
    }

    @Before
    public void initTest() {
        applicationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationType() throws Exception {
        int databaseSizeBeforeCreate = applicationTypeRepository.findAll().size();

        // Create the ApplicationType
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);
        restApplicationTypeMockMvc.perform(post(SCT_APPLICATION_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationType testApplicationType = applicationTypeList.get(applicationTypeList.size() - 1);
        assertThat(testApplicationType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApplicationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicationType.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testApplicationType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplicationType.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
    }

    @Test
    @Transactional
    public void createApplicationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationTypeRepository.findAll().size();

        // Create the ApplicationType with an existing ID
        applicationType.setId(1L);
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationTypeMockMvc.perform(post(SCT_APPLICATION_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationTypeRepository.findAll().size();
        // set the field null
        applicationType.setCode(null);

        // Create the ApplicationType, which fails.
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        restApplicationTypeMockMvc.perform(post(SCT_APPLICATION_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationTypeRepository.findAll().size();
        // set the field null
        applicationType.setStatus(null);

        // Create the ApplicationType, which fails.
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        restApplicationTypeMockMvc.perform(post(SCT_APPLICATION_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationTypes() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList
        restApplicationTypeMockMvc.perform(get("/api/sct/application-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(Matchers.hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].summary").value(Matchers.hasItem(DEFAULT_SUMMARY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void getApplicationType() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get the applicationType
        restApplicationTypeMockMvc.perform(get(SCT_APPLICATION_TYPES_BY_ID_API, applicationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationType() throws Exception {
        // Get the applicationType
        restApplicationTypeMockMvc.perform(get(SCT_APPLICATION_TYPES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationType() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        int databaseSizeBeforeUpdate = applicationTypeRepository.findAll().size();

        // Update the applicationType
        Optional<ApplicationType> optionalApplicationType =  applicationTypeRepository.findById(applicationType.getId());
        if (! optionalApplicationType.isPresent()) {
            return;
        }

        ApplicationType updatedApplicationType = optionalApplicationType.get();
        // Disconnect from session so that the updates on updatedApplicationType are not directly saved in db
        em.detach(updatedApplicationType);
        updatedApplicationType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .summary(UPDATED_SUMMARY)
            .status(UPDATED_STATUS)
            .sortOrder(UPDATED_SORT_ORDER);
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(updatedApplicationType);

        restApplicationTypeMockMvc.perform(put(SCT_APPLICATION_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationType testApplicationType = applicationTypeList.get(applicationTypeList.size() - 1);
        assertThat(testApplicationType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApplicationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationType.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testApplicationType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplicationType.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationType() throws Exception {
        int databaseSizeBeforeUpdate = applicationTypeRepository.findAll().size();

        // Create the ApplicationType
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationTypeMockMvc.perform(put(SCT_APPLICATION_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationType() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        int databaseSizeBeforeDelete = applicationTypeRepository.findAll().size();

        // Get the applicationType
        restApplicationTypeMockMvc.perform(delete(SCT_APPLICATION_TYPES_BY_ID_API, applicationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationType.class);
        ApplicationType applicationType1 = new ApplicationType();
        applicationType1.setId(1L);
        ApplicationType applicationType2 = new ApplicationType();
        applicationType2.setId(applicationType1.getId());
        assertThat(applicationType1).isEqualTo(applicationType2);
        applicationType2.setId(2L);
        assertThat(applicationType1).isNotEqualTo(applicationType2);
        applicationType1.setId(null);
        assertThat(applicationType1).isNotEqualTo(applicationType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationTypeDTO.class);
        ApplicationTypeDTO applicationTypeDTO1 = new ApplicationTypeDTO();
        applicationTypeDTO1.setId(1L);
        ApplicationTypeDTO applicationTypeDTO2 = new ApplicationTypeDTO();
        assertThat(applicationTypeDTO1).isNotEqualTo(applicationTypeDTO2);
        applicationTypeDTO2.setId(applicationTypeDTO1.getId());
        assertThat(applicationTypeDTO1).isEqualTo(applicationTypeDTO2);
        applicationTypeDTO2.setId(2L);
        assertThat(applicationTypeDTO1).isNotEqualTo(applicationTypeDTO2);
        applicationTypeDTO1.setId(null);
        assertThat(applicationTypeDTO1).isNotEqualTo(applicationTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationTypeMapper.fromId(null)).isNull();
    }
}
