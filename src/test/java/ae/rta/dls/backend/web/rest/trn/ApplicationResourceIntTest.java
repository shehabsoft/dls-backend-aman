package ae.rta.dls.backend.web.rest.trn;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.trn.Application;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.repository.trn.ApplicationRepository;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.mapper.trn.ApplicationMapper;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


import static ae.rta.dls.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
/**
 * Test class for the ApplicationResource REST controller.
 *
 * @see ApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ApplicationResourceIntTest {

    private static final ApplicationStatus DEFAULT_STATUS = ApplicationStatus.DRAFT;
    private static final ApplicationStatus UPDATED_STATUS = ApplicationStatus.UNDER_PROCESSING;

    private static final MultilingualJsonType DEFAULT_STATUS_DESCRIPTION = new MultilingualJsonType("جديد","New");
    private static final MultilingualJsonType UPDATED_STATUS_DESCRIPTION = new MultilingualJsonType("مكتمل","Completed");

    private static final LocalDateTime DEFAULT_STATUS_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_STATUS_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PhaseType DEFAULT_ACTIVE_PHASE = PhaseType.CUSTOMER_ELIGIBILITY;
    private static final PhaseType UPDATED_ACTIVE_PHASE = PhaseType.DRIVING_LEARNING_FILE_PROCESSING;

    private static final String DEFAULT_CONFIRMED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMED_BY = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_CONFIRMATION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_CONFIRMATION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REJECTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTION_REASON = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_REJECTION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_REJECTION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_PROCESS_INSTANCE_ID = 1L;
    private static final Long UPDATED_PROCESS_INSTANCE_ID = 2L;

    private static final String DEFAULT_CHANNEL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL_CODE = "BBBBBBBBBB";

    private static final String PORTAL_ARABIC_CHANNEL_NAME = "بورتال";
    private static final String PORTAL_ENGLISH_CHANNEL_NAME = "Portal";

    private static final MultilingualJsonType DEFAULT_CHANNEL_DESCRIPTION = new MultilingualJsonType(PORTAL_ARABIC_CHANNEL_NAME,PORTAL_ENGLISH_CHANNEL_NAME);

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_TRADE_LICENSE_NO = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_LICENSE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE = "BBBBBBBBBB";

    private static final MultilingualJsonType DEFAULT_USER_TYPE_DESCRIPTION = new MultilingualJsonType(PORTAL_ARABIC_CHANNEL_NAME,PORTAL_ENGLISH_CHANNEL_NAME);
    private static final MultilingualJsonType UPDATED_USER_TYPE_DESCRIPTION = new MultilingualJsonType(PORTAL_ARABIC_CHANNEL_NAME,PORTAL_ENGLISH_CHANNEL_NAME);

    private static final String DEFAULT_USER_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_USER_ROLE = "BBBBBBBBBB";

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationService applicationService;

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

    private MockMvc restApplicationMockMvc;

    private Application application;

    private static final String TRN_APPLICATIONS_API = "/api/trn/applications";

    private static final String TRN_APPLICATIONS_BY_ID_API = TRN_APPLICATIONS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationResource applicationResource = new ApplicationResource(applicationService);
        this.restApplicationMockMvc = MockMvcBuilders.standaloneSetup(applicationResource)
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
    public static Application createEntity(EntityManager em) {
        return new Application()
            .status(DEFAULT_STATUS)
            .statusDescription(DEFAULT_STATUS_DESCRIPTION)
            .statusDate(DEFAULT_STATUS_DATE)
            .activePhase(DEFAULT_ACTIVE_PHASE)
            .confirmedBy(DEFAULT_CONFIRMED_BY)
            .confirmationDate(DEFAULT_CONFIRMATION_DATE)
            .rejectedBy(DEFAULT_REJECTED_BY)
            .rejectionReason(DEFAULT_REJECTION_REASON)
            .rejectionDate(DEFAULT_REJECTION_DATE)
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID)
            .channelCode(DEFAULT_CHANNEL_CODE)
            .userId(DEFAULT_USER_ID)
            .tradeLicenseNo(DEFAULT_TRADE_LICENSE_NO)
            .userType(DEFAULT_USER_TYPE)
            .userTypeDescription(DEFAULT_USER_TYPE_DESCRIPTION)
            .userRole(DEFAULT_USER_ROLE);
    }

    @Before
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);
        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplication.getStatusDescription()).isEqualTo(DEFAULT_STATUS_DESCRIPTION);
        assertThat(testApplication.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testApplication.getActivePhase()).isEqualTo(DEFAULT_ACTIVE_PHASE);
        assertThat(testApplication.getConfirmedBy()).isEqualTo(DEFAULT_CONFIRMED_BY);
        assertThat(testApplication.getConfirmationDate()).isEqualTo(DEFAULT_CONFIRMATION_DATE);
        assertThat(testApplication.getRejectedBy()).isEqualTo(DEFAULT_REJECTED_BY);
        assertThat(testApplication.getRejectionReason()).isEqualTo(DEFAULT_REJECTION_REASON);
        assertThat(testApplication.getRejectionDate()).isEqualTo(DEFAULT_REJECTION_DATE);
        assertThat(testApplication.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
        assertThat(testApplication.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testApplication.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testApplication.getTradeLicenseNo()).isEqualTo(DEFAULT_TRADE_LICENSE_NO);
        assertThat(testApplication.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testApplication.getUserTypeDescription()).isEqualTo(DEFAULT_USER_TYPE_DESCRIPTION);
        assertThat(testApplication.getUserRole()).isEqualTo(DEFAULT_USER_ROLE);
    }

    @Test
    @Transactional
    public void createApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application with an existing ID
        application.setId(1L);
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkApplicationNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setStatus(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setStatusDate(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivePhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setActivePhase(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChannelCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setChannelCode(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setUserType(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setUserRole(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc.perform(get("/api/trn/applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].activePhase").value(hasItem(DEFAULT_ACTIVE_PHASE.toString())))
            .andExpect(jsonPath("$.[*].confirmedBy").value(hasItem(DEFAULT_CONFIRMED_BY)))
            .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].rejectedBy").value(hasItem(DEFAULT_REJECTED_BY)))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON)))
            .andExpect(jsonPath("$.[*].rejectionDate").value(hasItem(DEFAULT_REJECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE)))
            .andExpect(jsonPath("$.[*].channelDescription").value(hasItem(DEFAULT_CHANNEL_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].tradeLicenseNo").value(hasItem(DEFAULT_TRADE_LICENSE_NO)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].userTypeDescription").value(hasItem(DEFAULT_USER_TYPE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE)));
    }

    @Test
    @Transactional
    public void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc.perform(get(TRN_APPLICATIONS_BY_ID_API, application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusDescription").value(DEFAULT_STATUS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.activePhase").value(DEFAULT_ACTIVE_PHASE.toString()))
            .andExpect(jsonPath("$.confirmedBy").value(DEFAULT_CONFIRMED_BY))
            .andExpect(jsonPath("$.confirmationDate").value(DEFAULT_CONFIRMATION_DATE.toString()))
            .andExpect(jsonPath("$.rejectedBy").value(DEFAULT_REJECTED_BY))
            .andExpect(jsonPath("$.rejectionReason").value(DEFAULT_REJECTION_REASON))
            .andExpect(jsonPath("$.rejectionDate").value(DEFAULT_REJECTION_DATE.toString()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID.intValue()))
            .andExpect(jsonPath("$.channelCode").value(DEFAULT_CHANNEL_CODE))
            .andExpect(jsonPath("$.channelDescription").value(DEFAULT_CHANNEL_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.tradeLicenseNo").value(DEFAULT_TRADE_LICENSE_NO))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE))
            .andExpect(jsonPath("$.userTypeDescription").value(DEFAULT_USER_TYPE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.userRole").value(DEFAULT_USER_ROLE));
    }

    @Test
    @Transactional
    public void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get(TRN_APPLICATIONS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Optional<Application> optionalApplication = applicationRepository.findById(application.getId());
        if (! optionalApplication.isPresent()) {
            return;
        }

        Application updatedApplication = optionalApplication.get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .status(UPDATED_STATUS)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusDate(UPDATED_STATUS_DATE)
            .activePhase(UPDATED_ACTIVE_PHASE)
            .confirmedBy(UPDATED_CONFIRMED_BY)
            .confirmationDate(UPDATED_CONFIRMATION_DATE)
            .rejectedBy(UPDATED_REJECTED_BY)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .rejectionDate(UPDATED_REJECTION_DATE)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .userId(UPDATED_USER_ID)
            .tradeLicenseNo(UPDATED_TRADE_LICENSE_NO)
            .userType(UPDATED_USER_TYPE)
            .userTypeDescription(UPDATED_USER_TYPE_DESCRIPTION)
            .userRole(UPDATED_USER_ROLE);
        ApplicationDTO applicationDTO = applicationMapper.toDto(updatedApplication);

        restApplicationMockMvc.perform(put(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplication.getStatusDescription()).isEqualTo(UPDATED_STATUS_DESCRIPTION);
        assertThat(testApplication.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testApplication.getActivePhase()).isEqualTo(UPDATED_ACTIVE_PHASE);
        assertThat(testApplication.getConfirmedBy()).isEqualTo(UPDATED_CONFIRMED_BY);
        assertThat(testApplication.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
        assertThat(testApplication.getRejectedBy()).isEqualTo(UPDATED_REJECTED_BY);
        assertThat(testApplication.getRejectionReason()).isEqualTo(UPDATED_REJECTION_REASON);
        assertThat(testApplication.getRejectionDate()).isEqualTo(UPDATED_REJECTION_DATE);
        assertThat(testApplication.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testApplication.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testApplication.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testApplication.getTradeLicenseNo()).isEqualTo(UPDATED_TRADE_LICENSE_NO);
        assertThat(testApplication.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testApplication.getUserTypeDescription()).isEqualTo(UPDATED_USER_TYPE_DESCRIPTION);
        assertThat(testApplication.getUserRole()).isEqualTo(UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc.perform(put(TRN_APPLICATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Application.class);
        Application application1 = new Application();
        application1.setId(1L);
        Application application2 = new Application();
        application2.setId(application1.getId());
        assertThat(application1).isEqualTo(application2);
        application2.setId(2L);
        assertThat(application1).isNotEqualTo(application2);
        application1.setId(null);
        assertThat(application1).isNotEqualTo(application2);
    }

    @Test
    @Transactional
    public void deleteApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc.perform(delete(TRN_APPLICATIONS_BY_ID_API, application.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationDTO.class);
        ApplicationDTO applicationDTO1 = new ApplicationDTO();
        applicationDTO1.setId(1L);
        ApplicationDTO applicationDTO2 = new ApplicationDTO();
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
        applicationDTO2.setId(applicationDTO1.getId());
        assertThat(applicationDTO1).isEqualTo(applicationDTO2);
        applicationDTO2.setId(2L);
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
        applicationDTO1.setId(null);
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationMapper.fromId(null)).isNull();
    }
}
