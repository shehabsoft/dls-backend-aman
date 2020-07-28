package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;
import ae.rta.dls.backend.domain.enumeration.sys.JobStatus;
import ae.rta.dls.backend.domain.sys.AutomatedJobConfig;
import ae.rta.dls.backend.repository.sys.AutomatedJobConfigRepository;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import ae.rta.dls.backend.service.mapper.sys.AutomatedJobConfigMapper;
import ae.rta.dls.backend.service.sys.AutomatedJobConfigService;
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
 * Test class for the AutomatedJobConfigResource REST controller.
 *
 * @see AutomatedJobConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class AutomatedJobConfigResourceIntTest {

    private static final String DEFAULT_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CRON = "AAAAAAAAAA";
    private static final String UPDATED_CRON = "BBBBBBBBBB";

    private static final Long DEFAULT_FIXED_DELAY = 1L;
    private static final Long UPDATED_FIXED_DELAY = 2L;

    private static final Long DEFAULT_INITIAL_DELAY = 1L;
    private static final Long UPDATED_INITIAL_DELAY = 2L;

    private static final Long DEFAULT_FIXED_RATE = 1L;
    private static final Long UPDATED_FIXED_RATE = 2L;

    private static final JobStatus DEFAULT_STATUS = JobStatus.ACTIVE;
    private static final JobStatus UPDATED_STATUS = JobStatus.INACTIVE;

    @Autowired
    private AutomatedJobConfigRepository automatedJobConfigRepository;

    @Autowired
    private AutomatedJobConfigMapper automatedJobConfigMapper;

    @Autowired
    private AutomatedJobConfigService automatedJobConfigService;

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

    private MockMvc restAutomatedJobConfigMockMvc;

    private AutomatedJobConfig automatedJobConfig;

    private static final String SYS_AUTOMATED_JOB_CONFIGS_API = "/api/sys/automated-job-configs";

    private static final String SYS_AUTOMATED_JOB_CONFIGS_BY_ID_API = SYS_AUTOMATED_JOB_CONFIGS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutomatedJobConfigResource automatedJobConfigResource = new AutomatedJobConfigResource(automatedJobConfigService);
        this.restAutomatedJobConfigMockMvc = MockMvcBuilders.standaloneSetup(automatedJobConfigResource)
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
    public static AutomatedJobConfig createEntity(EntityManager em) {
        return new AutomatedJobConfig()
            .jobName(DEFAULT_JOB_NAME)
            .cron(DEFAULT_CRON)
            .fixedDelay(DEFAULT_FIXED_DELAY)
            .initialDelay(DEFAULT_INITIAL_DELAY)
            .fixedRate(DEFAULT_FIXED_RATE)
            .status(DEFAULT_STATUS);
    }

    @Before
    public void initTest() {
        automatedJobConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutomatedJobConfig() throws Exception {
        int databaseSizeBeforeCreate = automatedJobConfigRepository.findAll().size();

        // Create the AutomatedJobConfig
        AutomatedJobConfigDTO automatedJobConfigDTO = automatedJobConfigMapper.toDto(automatedJobConfig);
        restAutomatedJobConfigMockMvc.perform(post(SYS_AUTOMATED_JOB_CONFIGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the AutomatedJobConfig in the database
        List<AutomatedJobConfig> automatedJobConfigList = automatedJobConfigRepository.findAll();
        assertThat(automatedJobConfigList).hasSize(databaseSizeBeforeCreate + 1);
        AutomatedJobConfig testAutomatedJobConfig = automatedJobConfigList.get(automatedJobConfigList.size() - 1);
        assertThat(testAutomatedJobConfig.getJobName()).isEqualTo(DEFAULT_JOB_NAME);
        assertThat(testAutomatedJobConfig.getCron()).isEqualTo(DEFAULT_CRON);
        assertThat(testAutomatedJobConfig.getFixedDelay()).isEqualTo(DEFAULT_FIXED_DELAY);
        assertThat(testAutomatedJobConfig.getInitialDelay()).isEqualTo(DEFAULT_INITIAL_DELAY);
        assertThat(testAutomatedJobConfig.getFixedRate()).isEqualTo(DEFAULT_FIXED_RATE);
        assertThat(testAutomatedJobConfig.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAutomatedJobConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = automatedJobConfigRepository.findAll().size();

        // Create the AutomatedJobConfig with an existing ID
        automatedJobConfig.setId(1L);
        AutomatedJobConfigDTO automatedJobConfigDTO = automatedJobConfigMapper.toDto(automatedJobConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutomatedJobConfigMockMvc.perform(post(SYS_AUTOMATED_JOB_CONFIGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutomatedJobConfig in the database
        List<AutomatedJobConfig> automatedJobConfigList = automatedJobConfigRepository.findAll();
        assertThat(automatedJobConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJobNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = automatedJobConfigRepository.findAll().size();
        // set the field null
        automatedJobConfig.setJobName(null);

        // Create the AutomatedJobConfig, which fails.
        AutomatedJobConfigDTO automatedJobConfigDTO = automatedJobConfigMapper.toDto(automatedJobConfig);

        restAutomatedJobConfigMockMvc.perform(post(SYS_AUTOMATED_JOB_CONFIGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobConfigDTO)))
            .andExpect(status().isBadRequest());

        List<AutomatedJobConfig> automatedJobConfigList = automatedJobConfigRepository.findAll();
        assertThat(automatedJobConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = automatedJobConfigRepository.findAll().size();
        // set the field null
        automatedJobConfig.setStatus(null);

        // Create the AutomatedJobConfig, which fails.
        AutomatedJobConfigDTO automatedJobConfigDTO = automatedJobConfigMapper.toDto(automatedJobConfig);

        restAutomatedJobConfigMockMvc.perform(post(SYS_AUTOMATED_JOB_CONFIGS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(automatedJobConfigDTO)))
                .andExpect(status().isBadRequest());

        List<AutomatedJobConfig> automatedJobConfigList = automatedJobConfigRepository.findAll();
        assertThat(automatedJobConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutomatedJobConfigs() throws Exception {
        // Initialize the database
        automatedJobConfigRepository.saveAndFlush(automatedJobConfig);

        // Get all the automatedJobConfigList
        restAutomatedJobConfigMockMvc.perform(get("/api/sys/automated-job-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(automatedJobConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobName").value(hasItem(DEFAULT_JOB_NAME)))
            .andExpect(jsonPath("$.[*].cron").value(hasItem(DEFAULT_CRON)))
            .andExpect(jsonPath("$.[*].fixedDelay").value(hasItem(DEFAULT_FIXED_DELAY.intValue())))
            .andExpect(jsonPath("$.[*].initialDelay").value(hasItem(DEFAULT_INITIAL_DELAY.intValue())))
            .andExpect(jsonPath("$.[*].fixedRate").value(hasItem(DEFAULT_FIXED_RATE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getAutomatedJobConfig() throws Exception {
        // Initialize the database
        automatedJobConfigRepository.saveAndFlush(automatedJobConfig);

        // Get the automatedJobConfig
        restAutomatedJobConfigMockMvc.perform(get(SYS_AUTOMATED_JOB_CONFIGS_BY_ID_API, automatedJobConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(automatedJobConfig.getId().intValue()))
            .andExpect(jsonPath("$.jobName").value(DEFAULT_JOB_NAME))
            .andExpect(jsonPath("$.cron").value(DEFAULT_CRON))
            .andExpect(jsonPath("$.fixedDelay").value(DEFAULT_FIXED_DELAY.intValue()))
            .andExpect(jsonPath("$.initialDelay").value(DEFAULT_INITIAL_DELAY.intValue()))
            .andExpect(jsonPath("$.fixedRate").value(DEFAULT_FIXED_RATE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingAutomatedJobConfig() throws Exception {
        // Get the automatedJobConfig
        restAutomatedJobConfigMockMvc.perform(get(SYS_AUTOMATED_JOB_CONFIGS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutomatedJobConfig() throws Exception {
        // Initialize the database
        automatedJobConfigRepository.saveAndFlush(automatedJobConfig);

        int databaseSizeBeforeUpdate = automatedJobConfigRepository.findAll().size();

        // Update the automatedJobConfig

        Optional<AutomatedJobConfig> optionalAutomatedJobConfig = automatedJobConfigRepository.findById(automatedJobConfig.getId());
        if (! optionalAutomatedJobConfig.isPresent()) {
            return;
        }

        AutomatedJobConfig updatedAutomatedJobConfig = optionalAutomatedJobConfig.get();
        // Disconnect from session so that the updates on updatedAutomatedJobConfig are not directly saved in db
        em.detach(updatedAutomatedJobConfig);
        updatedAutomatedJobConfig
            .jobName(UPDATED_JOB_NAME)
            .cron(UPDATED_CRON)
            .fixedDelay(UPDATED_FIXED_DELAY)
            .initialDelay(UPDATED_INITIAL_DELAY)
            .fixedRate(UPDATED_FIXED_RATE)
            .status(UPDATED_STATUS);
        AutomatedJobConfigDTO automatedJobConfigDTO = automatedJobConfigMapper.toDto(updatedAutomatedJobConfig);

        restAutomatedJobConfigMockMvc.perform(put(SYS_AUTOMATED_JOB_CONFIGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobConfigDTO)))
            .andExpect(status().isOk());

        // Validate the AutomatedJobConfig in the database
        List<AutomatedJobConfig> automatedJobConfigList = automatedJobConfigRepository.findAll();
        assertThat(automatedJobConfigList).hasSize(databaseSizeBeforeUpdate);
        AutomatedJobConfig testAutomatedJobConfig = automatedJobConfigList.get(automatedJobConfigList.size() - 1);
        assertThat(testAutomatedJobConfig.getJobName()).isEqualTo(UPDATED_JOB_NAME);
        assertThat(testAutomatedJobConfig.getCron()).isEqualTo(UPDATED_CRON);
        assertThat(testAutomatedJobConfig.getFixedDelay()).isEqualTo(UPDATED_FIXED_DELAY);
        assertThat(testAutomatedJobConfig.getInitialDelay()).isEqualTo(UPDATED_INITIAL_DELAY);
        assertThat(testAutomatedJobConfig.getFixedRate()).isEqualTo(UPDATED_FIXED_RATE);
        assertThat(testAutomatedJobConfig.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAutomatedJobConfig() throws Exception {
        int databaseSizeBeforeUpdate = automatedJobConfigRepository.findAll().size();

        // Create the AutomatedJobConfig
        AutomatedJobConfigDTO automatedJobConfigDTO = automatedJobConfigMapper.toDto(automatedJobConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutomatedJobConfigMockMvc.perform(put(SYS_AUTOMATED_JOB_CONFIGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(automatedJobConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutomatedJobConfig in the database
        List<AutomatedJobConfig> automatedJobConfigList = automatedJobConfigRepository.findAll();
        assertThat(automatedJobConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutomatedJobConfig() throws Exception {
        // Initialize the database
        automatedJobConfigRepository.saveAndFlush(automatedJobConfig);

        int databaseSizeBeforeDelete = automatedJobConfigRepository.findAll().size();

        // Get the automatedJobConfig
        restAutomatedJobConfigMockMvc.perform(delete(SYS_AUTOMATED_JOB_CONFIGS_BY_ID_API, automatedJobConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AutomatedJobConfig> automatedJobConfigList = automatedJobConfigRepository.findAll();
        assertThat(automatedJobConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutomatedJobConfig.class);
        AutomatedJobConfig automatedJobConfig1 = new AutomatedJobConfig();
        automatedJobConfig1.setId(1L);
        AutomatedJobConfig automatedJobConfig2 = new AutomatedJobConfig();
        automatedJobConfig2.setId(automatedJobConfig1.getId());
        assertThat(automatedJobConfig1).isEqualTo(automatedJobConfig2);
        automatedJobConfig2.setId(2L);
        assertThat(automatedJobConfig1).isNotEqualTo(automatedJobConfig2);
        automatedJobConfig1.setId(null);
        assertThat(automatedJobConfig1).isNotEqualTo(automatedJobConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutomatedJobConfigDTO.class);
        AutomatedJobConfigDTO automatedJobConfigDTO1 = new AutomatedJobConfigDTO();
        automatedJobConfigDTO1.setId(1L);
        AutomatedJobConfigDTO automatedJobConfigDTO2 = new AutomatedJobConfigDTO();
        assertThat(automatedJobConfigDTO1).isNotEqualTo(automatedJobConfigDTO2);
        automatedJobConfigDTO2.setId(automatedJobConfigDTO1.getId());
        assertThat(automatedJobConfigDTO1).isEqualTo(automatedJobConfigDTO2);
        automatedJobConfigDTO2.setId(2L);
        assertThat(automatedJobConfigDTO1).isNotEqualTo(automatedJobConfigDTO2);
        automatedJobConfigDTO1.setId(null);
        assertThat(automatedJobConfigDTO1).isNotEqualTo(automatedJobConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(automatedJobConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(automatedJobConfigMapper.fromId(null)).isNull();
    }
}
