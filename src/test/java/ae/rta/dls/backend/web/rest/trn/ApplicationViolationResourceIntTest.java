package ae.rta.dls.backend.web.rest.trn;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.trn.ApplicationViolation;
import ae.rta.dls.backend.repository.trn.ApplicationViolationRepository;
import ae.rta.dls.backend.service.trn.ApplicationViolationService;
import ae.rta.dls.backend.service.dto.trn.ApplicationViolationDTO;
import ae.rta.dls.backend.service.mapper.trn.ApplicationViolationMapper;
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

import ae.rta.dls.backend.domain.enumeration.trn.ViolationLevel;
/**
 * Test class for the ApplicationViolationResource REST controller.
 *
 * @see ApplicationViolationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ApplicationViolationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION = false;
    private static final Boolean UPDATED_IS_ELIGIBLE_FOR_EXEMPTION = true;

    private static final Boolean DEFAULT_IS_EXEMPTED = false;
    private static final Boolean UPDATED_IS_EXEMPTED = true;

    private static final ViolationLevel DEFAULT_LEVEL = ViolationLevel.BLOCKER;
    private static final ViolationLevel UPDATED_LEVEL = ViolationLevel.WARNING;

    private static final Long DEFAULT_EXEMPTION_PROCESS_ID = 1L;
    private static final Long UPDATED_EXEMPTION_PROCESS_ID = 2L;

    private static final String DEFAULT_EXEMPTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXEMPTED_BY = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_EXEMPTION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_EXEMPTION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplicationViolationRepository applicationViolationRepository;

    @Autowired
    private ApplicationViolationMapper applicationViolationMapper;

    @Autowired
    private ApplicationViolationService applicationViolationService;

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

    private MockMvc restApplicationViolationMockMvc;

    private ApplicationViolation applicationViolation;

    private static final String TRN_APPLICATION_VIOLATIONS_API = "/api/trn/application-violations";

    private static final String TRN_APPLICATION_VIOLATIONS_BY_ID_API = TRN_APPLICATION_VIOLATIONS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationViolationResource applicationViolationResource = new ApplicationViolationResource(applicationViolationService);
        this.restApplicationViolationMockMvc = MockMvcBuilders.standaloneSetup(applicationViolationResource)
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
    public static ApplicationViolation createEntity(EntityManager em) {
        return new ApplicationViolation()
            .code(DEFAULT_CODE)
            .isEligibleForExemption(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION)
            .isExempted(DEFAULT_IS_EXEMPTED)
            .violationlevel(DEFAULT_LEVEL)
            .exemptionProcessId(DEFAULT_EXEMPTION_PROCESS_ID)
            .exemptedBy(DEFAULT_EXEMPTED_BY)
            .exemptionDate(DEFAULT_EXEMPTION_DATE);
    }

    @Before
    public void initTest() {
        applicationViolation = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationViolation() throws Exception {
        int databaseSizeBeforeCreate = applicationViolationRepository.findAll().size();

        // Create the ApplicationViolation
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);
        restApplicationViolationMockMvc.perform(post(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationViolation testApplicationViolation = applicationViolationList.get(applicationViolationList.size() - 1);
        assertThat(testApplicationViolation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApplicationViolation.isIsEligibleForExemption()).isEqualTo(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);
        assertThat(testApplicationViolation.isIsExempted()).isEqualTo(DEFAULT_IS_EXEMPTED);
        assertThat(testApplicationViolation.getViolationLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testApplicationViolation.getExemptionProcessId()).isEqualTo(DEFAULT_EXEMPTION_PROCESS_ID);
        assertThat(testApplicationViolation.getExemptedBy()).isEqualTo(DEFAULT_EXEMPTED_BY);
        assertThat(testApplicationViolation.getExemptionDate()).isEqualTo(DEFAULT_EXEMPTION_DATE);
    }

    @Test
    @Transactional
    public void createApplicationViolationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationViolationRepository.findAll().size();

        // Create the ApplicationViolation with an existing ID
        applicationViolation.setId(1L);
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationViolationMockMvc.perform(post(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setCode(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsEligibleForExemptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setIsEligibleForExemption(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsExemptedIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setIsExempted(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setViolationLevel(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationViolations() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList
        restApplicationViolationMockMvc.perform(get("/api/application-violations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationViolation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].isEligibleForExemption").value(hasItem(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].isExempted").value(hasItem(DEFAULT_IS_EXEMPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].exemptionProcessId").value(hasItem(DEFAULT_EXEMPTION_PROCESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].exemptedBy").value(hasItem(DEFAULT_EXEMPTED_BY)))
            .andExpect(jsonPath("$.[*].exemptionDate").value(hasItem(DEFAULT_EXEMPTION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getApplicationViolation() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get the applicationViolation
        restApplicationViolationMockMvc.perform(get(TRN_APPLICATION_VIOLATIONS_BY_ID_API, applicationViolation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationViolation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.isEligibleForExemption").value(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION.booleanValue()))
            .andExpect(jsonPath("$.isExempted").value(DEFAULT_IS_EXEMPTED.booleanValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.exemptionProcessId").value(DEFAULT_EXEMPTION_PROCESS_ID.intValue()))
            .andExpect(jsonPath("$.exemptedBy").value(DEFAULT_EXEMPTED_BY))
            .andExpect(jsonPath("$.exemptionDate").value(DEFAULT_EXEMPTION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationViolation() throws Exception {
        // Get the applicationViolation
        restApplicationViolationMockMvc.perform(get(TRN_APPLICATION_VIOLATIONS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationViolation() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        int databaseSizeBeforeUpdate = applicationViolationRepository.findAll().size();

        // Update the applicationViolation
        Optional<ApplicationViolation> optionalApplicationViolation = applicationViolationRepository.findById(applicationViolation.getId());
        if (! optionalApplicationViolation.isPresent()) {
            return;
        }

        ApplicationViolation updatedApplicationViolation = optionalApplicationViolation.get();
        // Disconnect from session so that the updates on updatedApplicationViolation are not directly saved in db
        em.detach(updatedApplicationViolation);
        updatedApplicationViolation
            .code(UPDATED_CODE)
            .isEligibleForExemption(UPDATED_IS_ELIGIBLE_FOR_EXEMPTION)
            .isExempted(UPDATED_IS_EXEMPTED)
            .violationlevel(UPDATED_LEVEL)
            .exemptionProcessId(UPDATED_EXEMPTION_PROCESS_ID)
            .exemptedBy(UPDATED_EXEMPTED_BY)
            .exemptionDate(UPDATED_EXEMPTION_DATE);
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(updatedApplicationViolation);

        restApplicationViolationMockMvc.perform(put(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeUpdate);
        ApplicationViolation testApplicationViolation = applicationViolationList.get(applicationViolationList.size() - 1);
        assertThat(testApplicationViolation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApplicationViolation.isIsEligibleForExemption()).isEqualTo(UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);
        assertThat(testApplicationViolation.isIsExempted()).isEqualTo(UPDATED_IS_EXEMPTED);
        assertThat(testApplicationViolation.getViolationLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testApplicationViolation.getExemptionProcessId()).isEqualTo(UPDATED_EXEMPTION_PROCESS_ID);
        assertThat(testApplicationViolation.getExemptedBy()).isEqualTo(UPDATED_EXEMPTED_BY);
        assertThat(testApplicationViolation.getExemptionDate()).isEqualTo(UPDATED_EXEMPTION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationViolation() throws Exception {
        int databaseSizeBeforeUpdate = applicationViolationRepository.findAll().size();

        // Create the ApplicationViolation
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationViolationMockMvc.perform(put(TRN_APPLICATION_VIOLATIONS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationViolation() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        int databaseSizeBeforeDelete = applicationViolationRepository.findAll().size();

        // Delete the applicationViolation
        restApplicationViolationMockMvc.perform(delete(TRN_APPLICATION_VIOLATIONS_BY_ID_API, applicationViolation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationViolation.class);
        ApplicationViolation applicationViolation1 = new ApplicationViolation();
        applicationViolation1.setId(1L);
        ApplicationViolation applicationViolation2 = new ApplicationViolation();
        applicationViolation2.setId(applicationViolation1.getId());
        assertThat(applicationViolation1).isEqualTo(applicationViolation2);
        applicationViolation2.setId(2L);
        assertThat(applicationViolation1).isNotEqualTo(applicationViolation2);
        applicationViolation1.setId(null);
        assertThat(applicationViolation1).isNotEqualTo(applicationViolation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationViolationDTO.class);
        ApplicationViolationDTO applicationViolationDTO1 = new ApplicationViolationDTO();
        applicationViolationDTO1.setId(1L);
        ApplicationViolationDTO applicationViolationDTO2 = new ApplicationViolationDTO();
        assertThat(applicationViolationDTO1).isNotEqualTo(applicationViolationDTO2);
        applicationViolationDTO2.setId(applicationViolationDTO1.getId());
        assertThat(applicationViolationDTO1).isEqualTo(applicationViolationDTO2);
        applicationViolationDTO2.setId(2L);
        assertThat(applicationViolationDTO1).isNotEqualTo(applicationViolationDTO2);
        applicationViolationDTO1.setId(null);
        assertThat(applicationViolationDTO1).isNotEqualTo(applicationViolationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationViolationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationViolationMapper.fromId(null)).isNull();
    }
}
