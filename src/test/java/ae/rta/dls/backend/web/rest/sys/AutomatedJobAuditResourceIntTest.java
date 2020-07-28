package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.sys.AutomatedJobAudit;
import ae.rta.dls.backend.repository.sys.AutomatedJobAuditRepository;
import ae.rta.dls.backend.service.sys.AutomatedJobAuditService;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobAuditDTO;
import ae.rta.dls.backend.service.mapper.sys.AutomatedJobAuditMapper;
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

/**
 * Test class for the AutomatedJobAuditResource REST controller.
 *
 * @see AutomatedJobAuditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class AutomatedJobAuditResourceIntTest {

    private static final String DEFAULT_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NAME = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_START_TIME = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_START_TIME = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDateTime DEFAULT_END_TIME = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_END_TIME = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_DURATION_IN_SECONDS = 1L;
    private static final Long UPDATED_DURATION_IN_SECONDS = 2L;

    private static final String DEFAULT_CRON = "AAAAAAAAAA";
    private static final String UPDATED_CRON = "BBBBBBBBBB";

    private static final Long DEFAULT_FIXED_DELAY = 1L;
    private static final Long UPDATED_FIXED_DELAY = 2L;

    private static final Long DEFAULT_INITIAL_DELAY = 1L;
    private static final Long UPDATED_INITIAL_DELAY = 2L;

    private static final Long DEFAULT_FIXED_RATE = 1L;
    private static final Long UPDATED_FIXED_RATE = 2L;

    private static final String DEFAULT_TECHNICAL_REMARKS = "AAAAAAAAAA";
    private static final String UPDATE_TECHNICAL_REMARKS = "BBBBBBBBBB";

    @Autowired
    private AutomatedJobAuditRepository automatedJobAuditRepository;

    @Autowired
    private AutomatedJobAuditMapper automatedJobAuditMapper;

    @Autowired
    private AutomatedJobAuditService automatedJobAuditService;

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

    private MockMvc restAutomatedJobAuditMockMvc;

    private AutomatedJobAudit automatedJobAudit;

    private static final String AUTOMATED_JOB_AUDITS_API = "/api/sys/automated-job-audits";

    private static final String AUTOMATED_JOB_AUDITS_BY_ID_API = AUTOMATED_JOB_AUDITS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutomatedJobAuditResource automatedJobAuditResource = new AutomatedJobAuditResource(automatedJobAuditService);
        this.restAutomatedJobAuditMockMvc = MockMvcBuilders.standaloneSetup(automatedJobAuditResource)
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
    public static AutomatedJobAudit createEntity(EntityManager em) {
        return new AutomatedJobAudit()
            .jobName(DEFAULT_JOB_NAME)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .durationInSeconds(DEFAULT_DURATION_IN_SECONDS)
            .cron(DEFAULT_CRON)
            .fixedDelay(DEFAULT_FIXED_DELAY)
            .initialDelay(DEFAULT_INITIAL_DELAY)
            .fixedRate(DEFAULT_FIXED_RATE)
            .technicalRemarks(DEFAULT_TECHNICAL_REMARKS);
    }

    @Before
    public void initTest() {
        automatedJobAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutomatedJobAudit() throws Exception {
        int databaseSizeBeforeCreate = automatedJobAuditRepository.findAll().size();

        // Create the AutomatedJobAudit
        AutomatedJobAuditDTO automatedJobAuditDTO = automatedJobAuditMapper.toDto(automatedJobAudit);
        restAutomatedJobAuditMockMvc.perform(post(AUTOMATED_JOB_AUDITS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobAuditDTO)))
            .andExpect(status().isCreated());

        // Validate the AutomatedJobAudit in the database
        List<AutomatedJobAudit> automatedJobAuditList = automatedJobAuditRepository.findAll();
        assertThat(automatedJobAuditList).hasSize(databaseSizeBeforeCreate + 1);
        AutomatedJobAudit testAutomatedJobAudit = automatedJobAuditList.get(automatedJobAuditList.size() - 1);
        assertThat(testAutomatedJobAudit.getJobName()).isEqualTo(DEFAULT_JOB_NAME);
        assertThat(testAutomatedJobAudit.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testAutomatedJobAudit.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testAutomatedJobAudit.getDurationInSeconds()).isEqualTo(DEFAULT_DURATION_IN_SECONDS);
        assertThat(testAutomatedJobAudit.getCron()).isEqualTo(DEFAULT_CRON);
        assertThat(testAutomatedJobAudit.getFixedDelay()).isEqualTo(DEFAULT_FIXED_DELAY);
        assertThat(testAutomatedJobAudit.getInitialDelay()).isEqualTo(DEFAULT_INITIAL_DELAY);
        assertThat(testAutomatedJobAudit.getFixedRate()).isEqualTo(DEFAULT_FIXED_RATE);
        assertThat(testAutomatedJobAudit.getTechnicalRemarks()).isEqualTo(DEFAULT_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void createAutomatedJobAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = automatedJobAuditRepository.findAll().size();

        // Create the AutomatedJobAudit with an existing ID
        automatedJobAudit.setId(1L);
        AutomatedJobAuditDTO automatedJobAuditDTO = automatedJobAuditMapper.toDto(automatedJobAudit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutomatedJobAuditMockMvc.perform(post(AUTOMATED_JOB_AUDITS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutomatedJobAudit in the database
        List<AutomatedJobAudit> automatedJobAuditList = automatedJobAuditRepository.findAll();
        assertThat(automatedJobAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJobNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = automatedJobAuditRepository.findAll().size();
        // set the field null
        automatedJobAudit.setJobName(null);

        // Create the AutomatedJobAudit, which fails.
        AutomatedJobAuditDTO automatedJobAuditDTO = automatedJobAuditMapper.toDto(automatedJobAudit);

        restAutomatedJobAuditMockMvc.perform(post(AUTOMATED_JOB_AUDITS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobAuditDTO)))
            .andExpect(status().isBadRequest());

        List<AutomatedJobAudit> automatedJobAuditList = automatedJobAuditRepository.findAll();
        assertThat(automatedJobAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = automatedJobAuditRepository.findAll().size();
        // set the field null
        automatedJobAudit.setStartTime(null);

        // Create the AutomatedJobAudit, which fails.
        AutomatedJobAuditDTO automatedJobAuditDTO = automatedJobAuditMapper.toDto(automatedJobAudit);

        restAutomatedJobAuditMockMvc.perform(post(AUTOMATED_JOB_AUDITS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobAuditDTO)))
            .andExpect(status().isBadRequest());

        List<AutomatedJobAudit> automatedJobAuditList = automatedJobAuditRepository.findAll();
        assertThat(automatedJobAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutomatedJobAudits() throws Exception {
        // Initialize the database
        automatedJobAuditRepository.saveAndFlush(automatedJobAudit);

        // Get all the automatedJobAuditList
        restAutomatedJobAuditMockMvc.perform(get("/api/automated-job-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(automatedJobAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobName").value(hasItem(DEFAULT_JOB_NAME)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].durationInSeconds").value(hasItem(DEFAULT_DURATION_IN_SECONDS.intValue())))
            .andExpect(jsonPath("$.[*].cron").value(hasItem(DEFAULT_CRON)))
            .andExpect(jsonPath("$.[*].fixedDelay").value(hasItem(DEFAULT_FIXED_DELAY.intValue())))
            .andExpect(jsonPath("$.[*].initialDelay").value(hasItem(DEFAULT_INITIAL_DELAY.intValue())))
            .andExpect(jsonPath("$.[*].fixedRate").value(hasItem(DEFAULT_FIXED_RATE.intValue())))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)));
    }

    @Test
    @Transactional
    public void getAutomatedJobAudit() throws Exception {
        // Initialize the database
        automatedJobAuditRepository.saveAndFlush(automatedJobAudit);

        // Get the automatedJobAudit
        restAutomatedJobAuditMockMvc.perform(get(AUTOMATED_JOB_AUDITS_BY_ID_API, automatedJobAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(automatedJobAudit.getId().intValue()))
            .andExpect(jsonPath("$.jobName").value(DEFAULT_JOB_NAME))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.durationInSeconds").value(DEFAULT_DURATION_IN_SECONDS.intValue()))
            .andExpect(jsonPath("$.cron").value(DEFAULT_CRON))
            .andExpect(jsonPath("$.fixedDelay").value(DEFAULT_FIXED_DELAY.intValue()))
            .andExpect(jsonPath("$.initialDelay").value(DEFAULT_INITIAL_DELAY.intValue()))
            .andExpect(jsonPath("$.fixedRate").value(DEFAULT_FIXED_RATE.intValue()))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)));
    }

    @Test
    @Transactional
    public void getNonExistingAutomatedJobAudit() throws Exception {
        // Get the automatedJobAudit
        restAutomatedJobAuditMockMvc.perform(get(AUTOMATED_JOB_AUDITS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutomatedJobAudit() throws Exception {
        // Initialize the database
        automatedJobAuditRepository.saveAndFlush(automatedJobAudit);

        int databaseSizeBeforeUpdate = automatedJobAuditRepository.findAll().size();

        // Update the automatedJobAudit
        Optional<AutomatedJobAudit> optionalAutomatedJobAudit = automatedJobAuditRepository.findById(automatedJobAudit.getId());

        if (! optionalAutomatedJobAudit.isPresent()) {
            return;
        }

        AutomatedJobAudit updatedAutomatedJobAudit = optionalAutomatedJobAudit.get();
        // Disconnect from session so that the updates on updatedAutomatedJobAudit are not directly saved in db
        em.detach(updatedAutomatedJobAudit);
        updatedAutomatedJobAudit
            .jobName(UPDATED_JOB_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .durationInSeconds(UPDATED_DURATION_IN_SECONDS)
            .cron(UPDATED_CRON)
            .fixedDelay(UPDATED_FIXED_DELAY)
            .initialDelay(UPDATED_INITIAL_DELAY)
            .fixedRate(UPDATED_FIXED_RATE)
            .technicalRemarks(UPDATE_TECHNICAL_REMARKS);
        AutomatedJobAuditDTO automatedJobAuditDTO = automatedJobAuditMapper.toDto(updatedAutomatedJobAudit);

        restAutomatedJobAuditMockMvc.perform(put(AUTOMATED_JOB_AUDITS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobAuditDTO)))
            .andExpect(status().isOk());

        // Validate the AutomatedJobAudit in the database
        List<AutomatedJobAudit> automatedJobAuditList = automatedJobAuditRepository.findAll();
        assertThat(automatedJobAuditList).hasSize(databaseSizeBeforeUpdate);
        AutomatedJobAudit testAutomatedJobAudit = automatedJobAuditList.get(automatedJobAuditList.size() - 1);
        assertThat(testAutomatedJobAudit.getJobName()).isEqualTo(UPDATED_JOB_NAME);
        assertThat(testAutomatedJobAudit.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAutomatedJobAudit.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testAutomatedJobAudit.getDurationInSeconds()).isEqualTo(UPDATED_DURATION_IN_SECONDS);
        assertThat(testAutomatedJobAudit.getCron()).isEqualTo(UPDATED_CRON);
        assertThat(testAutomatedJobAudit.getFixedDelay()).isEqualTo(UPDATED_FIXED_DELAY);
        assertThat(testAutomatedJobAudit.getInitialDelay()).isEqualTo(UPDATED_INITIAL_DELAY);
        assertThat(testAutomatedJobAudit.getFixedRate()).isEqualTo(UPDATED_FIXED_RATE);
        assertThat(testAutomatedJobAudit.getTechnicalRemarks()).isEqualTo(UPDATE_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingAutomatedJobAudit() throws Exception {
        int databaseSizeBeforeUpdate = automatedJobAuditRepository.findAll().size();

        // Create the AutomatedJobAudit
        AutomatedJobAuditDTO automatedJobAuditDTO = automatedJobAuditMapper.toDto(automatedJobAudit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutomatedJobAuditMockMvc.perform(put(AUTOMATED_JOB_AUDITS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutomatedJobAudit in the database
        List<AutomatedJobAudit> automatedJobAuditList = automatedJobAuditRepository.findAll();
        assertThat(automatedJobAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutomatedJobAudit() throws Exception {
        // Initialize the database
        automatedJobAuditRepository.saveAndFlush(automatedJobAudit);

        int databaseSizeBeforeDelete = automatedJobAuditRepository.findAll().size();

        // Delete the automatedJobAudit
        restAutomatedJobAuditMockMvc.perform(delete(AUTOMATED_JOB_AUDITS_BY_ID_API, automatedJobAudit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AutomatedJobAudit> automatedJobAuditList = automatedJobAuditRepository.findAll();
        assertThat(automatedJobAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutomatedJobAudit.class);
        AutomatedJobAudit automatedJobAudit1 = new AutomatedJobAudit();
        automatedJobAudit1.setId(1L);
        AutomatedJobAudit automatedJobAudit2 = new AutomatedJobAudit();
        automatedJobAudit2.setId(automatedJobAudit1.getId());
        assertThat(automatedJobAudit1).isEqualTo(automatedJobAudit2);
        automatedJobAudit2.setId(2L);
        assertThat(automatedJobAudit1).isNotEqualTo(automatedJobAudit2);
        automatedJobAudit1.setId(null);
        assertThat(automatedJobAudit1).isNotEqualTo(automatedJobAudit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutomatedJobAuditDTO.class);
        AutomatedJobAuditDTO automatedJobAuditDTO1 = new AutomatedJobAuditDTO();
        automatedJobAuditDTO1.setId(1L);
        AutomatedJobAuditDTO automatedJobAuditDTO2 = new AutomatedJobAuditDTO();
        assertThat(automatedJobAuditDTO1).isNotEqualTo(automatedJobAuditDTO2);
        automatedJobAuditDTO2.setId(automatedJobAuditDTO1.getId());
        assertThat(automatedJobAuditDTO1).isEqualTo(automatedJobAuditDTO2);
        automatedJobAuditDTO2.setId(2L);
        assertThat(automatedJobAuditDTO1).isNotEqualTo(automatedJobAuditDTO2);
        automatedJobAuditDTO1.setId(null);
        assertThat(automatedJobAuditDTO1).isNotEqualTo(automatedJobAuditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(automatedJobAuditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(automatedJobAuditMapper.fromId(null)).isNull();
    }
}
