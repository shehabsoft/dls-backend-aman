package ae.rta.dls.backend.web.rest.trn;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.trn.ApplicationPhase;
import ae.rta.dls.backend.repository.trn.ApplicationPhaseRepository;
import ae.rta.dls.backend.service.trn.ApplicationPhaseService;
import ae.rta.dls.backend.service.dto.trn.ApplicationPhaseDTO;
import ae.rta.dls.backend.service.mapper.trn.ApplicationPhaseMapper;
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

import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
/**
 * Test class for the ApplicationPhaseResource REST controller.
 *
 * @see ApplicationPhaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ApplicationPhaseResourceIntTest {

    private static final PhaseType DEFAULT_TYPE = PhaseType.CUSTOMER_ELIGIBILITY;
    private static final PhaseType UPDATED_TYPE = PhaseType.DRIVING_LEARNING_FILE_PROCESSING;

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_START_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDateTime DEFAULT_END_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_END_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA = "BBBBBBBBBB";

    @Autowired
    private ApplicationPhaseRepository applicationPhaseRepository;

    @Autowired
    private ApplicationPhaseMapper applicationPhaseMapper;

    @Autowired
    private ApplicationPhaseService applicationPhaseService;

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

    private MockMvc restApplicationPhaseMockMvc;

    private ApplicationPhase applicationPhase;

    private static final String TRN_APPLICATION_PHASES_API = "/api/trn/application-phases";

    private static final String TRN_APPLICATION_PHASES_BY_ID_API = TRN_APPLICATION_PHASES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationPhaseResource applicationPhaseResource = new ApplicationPhaseResource(applicationPhaseService);
        this.restApplicationPhaseMockMvc = MockMvcBuilders.standaloneSetup(applicationPhaseResource)
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
    public static ApplicationPhase createEntity(EntityManager em) {
        return new ApplicationPhase()
            .phaseType(DEFAULT_TYPE)
            .sequence(DEFAULT_SEQUENCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .persona(DEFAULT_PERSONA);
    }

    @Before
    public void initTest() {
        applicationPhase = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationPhase() throws Exception {
        int databaseSizeBeforeCreate = applicationPhaseRepository.findAll().size();

        // Create the ApplicationPhase
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);
        restApplicationPhaseMockMvc.perform(post(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationPhase testApplicationPhase = applicationPhaseList.get(applicationPhaseList.size() - 1);
        assertThat(testApplicationPhase.getPhaseType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testApplicationPhase.getPhaseSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testApplicationPhase.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testApplicationPhase.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testApplicationPhase.getPersona()).isEqualTo(DEFAULT_PERSONA);
    }

    @Test
    @Transactional
    public void createApplicationPhaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationPhaseRepository.findAll().size();

        // Create the ApplicationPhase with an existing ID
        applicationPhase.setId(1L);
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationPhaseMockMvc.perform(post(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setPhaseType(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setPhaseSequence(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setStartDate(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPersonaIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setPersona(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationPhases() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList
        restApplicationPhaseMockMvc.perform(get("/api/application-phases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationPhase.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)));
    }

    @Test
    @Transactional
    public void getApplicationPhase() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get the applicationPhase
        restApplicationPhaseMockMvc.perform(get(TRN_APPLICATION_PHASES_BY_ID_API, applicationPhase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationPhase.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.persona").value(DEFAULT_PERSONA));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationPhase() throws Exception {
        // Get the applicationPhase
        restApplicationPhaseMockMvc.perform(get(TRN_APPLICATION_PHASES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationPhase() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        int databaseSizeBeforeUpdate = applicationPhaseRepository.findAll().size();

        // Update the applicationPhase
        Optional<ApplicationPhase> optionalApplicationPhase = applicationPhaseRepository.findById(applicationPhase.getId());
        if (! optionalApplicationPhase.isPresent()) {
            return;
        }

        ApplicationPhase updatedApplicationPhase = optionalApplicationPhase.get();
        // Disconnect from session so that the updates on updatedApplicationPhase are not directly saved in db
        em.detach(updatedApplicationPhase);
        updatedApplicationPhase
            .phaseType(UPDATED_TYPE)
            .sequence(UPDATED_SEQUENCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .persona(UPDATED_PERSONA);
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(updatedApplicationPhase);

        restApplicationPhaseMockMvc.perform(put(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeUpdate);
        ApplicationPhase testApplicationPhase = applicationPhaseList.get(applicationPhaseList.size() - 1);
        assertThat(testApplicationPhase.getPhaseType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApplicationPhase.getPhaseSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testApplicationPhase.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testApplicationPhase.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testApplicationPhase.getPersona()).isEqualTo(UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationPhase() throws Exception {
        int databaseSizeBeforeUpdate = applicationPhaseRepository.findAll().size();

        // Create the ApplicationPhase
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationPhaseMockMvc.perform(put(TRN_APPLICATION_PHASES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationPhase() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        int databaseSizeBeforeDelete = applicationPhaseRepository.findAll().size();

        // Delete the applicationPhase
        restApplicationPhaseMockMvc.perform(delete(TRN_APPLICATION_PHASES_BY_ID_API, applicationPhase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationPhase.class);
        ApplicationPhase applicationPhase1 = new ApplicationPhase();
        applicationPhase1.setId(1L);
        ApplicationPhase applicationPhase2 = new ApplicationPhase();
        applicationPhase2.setId(applicationPhase1.getId());
        assertThat(applicationPhase1).isEqualTo(applicationPhase2);
        applicationPhase2.setId(2L);
        assertThat(applicationPhase1).isNotEqualTo(applicationPhase2);
        applicationPhase1.setId(null);
        assertThat(applicationPhase1).isNotEqualTo(applicationPhase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationPhaseDTO.class);
        ApplicationPhaseDTO applicationPhaseDTO1 = new ApplicationPhaseDTO();
        applicationPhaseDTO1.setId(1L);
        ApplicationPhaseDTO applicationPhaseDTO2 = new ApplicationPhaseDTO();
        assertThat(applicationPhaseDTO1).isNotEqualTo(applicationPhaseDTO2);
        applicationPhaseDTO2.setId(applicationPhaseDTO1.getId());
        assertThat(applicationPhaseDTO1).isEqualTo(applicationPhaseDTO2);
        applicationPhaseDTO2.setId(2L);
        assertThat(applicationPhaseDTO1).isNotEqualTo(applicationPhaseDTO2);
        applicationPhaseDTO1.setId(null);
        assertThat(applicationPhaseDTO1).isNotEqualTo(applicationPhaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationPhaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationPhaseMapper.fromId(null)).isNull();
    }
}
