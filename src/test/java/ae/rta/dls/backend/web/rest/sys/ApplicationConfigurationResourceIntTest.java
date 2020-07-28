package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;
import ae.rta.dls.backend.domain.sys.ApplicationConfiguration;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.repository.sys.ApplicationConfigurationRepository;
import ae.rta.dls.backend.service.mapper.sys.ApplicationConfigurationMapper;
import ae.rta.dls.backend.service.sys.ApplicationConfigurationService;
import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;
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
 * Test class for the ApplicationConfigurationResource REST controller.
 *
 * @see ae.rta.dls.backend.web.rest.sys.ApplicationConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ApplicationConfigurationResourceIntTest {

    private static final String DEFAULT_CONFIG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_VALUE = "BBBBBBBBBB";

    private static final MultilingualJsonType DEFAULT_DESCRIPTION = new MultilingualJsonType();
    private static final MultilingualJsonType UPDATED_DESCRIPTION = new MultilingualJsonType();

    private static final Boolean DEFAULT_CACHED = false;
    private static final Boolean UPDATED_CACHED = true;

    private static final Boolean DEFAULT_ENCRYPTED = false;
    private static final Boolean UPDATED_ENCRYPTED = true;

    @Autowired
    private ApplicationConfigurationRepository applicationConfigurationRepository;

    @Autowired
    private ApplicationConfigurationMapper applicationConfigurationMapper;

    @Autowired
    private ApplicationConfigurationService applicationConfigurationService;

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

    private MockMvc restApplicationConfigurationMockMvc;

    private ApplicationConfiguration applicationConfiguration;

    private static final String SYS_APPLICATION_CONFIGURATIONS_API = "/api/sys/application-configurations";

    private static final String SYS_APPLICATION_CONFIGURATIONS_BY_ID_API = SYS_APPLICATION_CONFIGURATIONS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationConfigurationResource applicationConfigurationResource = new ApplicationConfigurationResource(applicationConfigurationService);
        this.restApplicationConfigurationMockMvc = MockMvcBuilders.standaloneSetup(applicationConfigurationResource)
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
    public static ApplicationConfiguration createEntity(EntityManager em) {
        return new ApplicationConfiguration()
                .configKey(DEFAULT_CONFIG_KEY)
                .configValue(DEFAULT_CONFIG_VALUE)
                .description(new MultilingualJsonType())
                .cached(DEFAULT_CACHED)
                .encrypted(DEFAULT_ENCRYPTED);
    }

    @Before
    public void initTest() {
        applicationConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationConfiguration() throws Exception {
        int databaseSizeBeforeCreate = applicationConfigurationRepository.findAll().size();

        // Create the ApplicationConfiguration
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);
        restApplicationConfigurationMockMvc.perform(post(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isCreated());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationConfiguration testApplicationConfiguration = applicationConfigurationList.get(applicationConfigurationList.size() - 1);
        assertThat(testApplicationConfiguration.getConfigKey()).isEqualTo(DEFAULT_CONFIG_KEY);
        assertThat(testApplicationConfiguration.getConfigValue()).isEqualTo(DEFAULT_CONFIG_VALUE);
        assertThat(testApplicationConfiguration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicationConfiguration.isCached()).isEqualTo(DEFAULT_CACHED);
        assertThat(testApplicationConfiguration.isEncrypted()).isEqualTo(DEFAULT_ENCRYPTED);
    }

    @Test
    @Transactional
    public void createApplicationConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationConfigurationRepository.findAll().size();

        // Create the ApplicationConfiguration with an existing ID
        applicationConfiguration.setId(1L);
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationConfigurationMockMvc.perform(post(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isBadRequest());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkConfigKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setConfigKey(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfigValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setConfigValue(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCachedIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setCached(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEncryptedIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setEncrypted(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurations() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList
        restApplicationConfigurationMockMvc.perform(get("/api/sys/application-configurations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(applicationConfiguration.getId().intValue())))
                .andExpect(jsonPath("$.[*].configKey").value(hasItem(DEFAULT_CONFIG_KEY)))
                .andExpect(jsonPath("$.[*].configValue").value(hasItem(DEFAULT_CONFIG_VALUE)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cached").value(hasItem(DEFAULT_CACHED.booleanValue())))
                .andExpect(jsonPath("$.[*].encrypted").value(hasItem(DEFAULT_ENCRYPTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getApplicationConfiguration() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get the applicationConfiguration
        restApplicationConfigurationMockMvc.perform(get(SYS_APPLICATION_CONFIGURATIONS_BY_ID_API, applicationConfiguration.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(applicationConfiguration.getId().intValue()))
                .andExpect(jsonPath("$.configKey").value(DEFAULT_CONFIG_KEY))
                .andExpect(jsonPath("$.configValue").value(DEFAULT_CONFIG_VALUE))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.cached").value(DEFAULT_CACHED.booleanValue()))
                .andExpect(jsonPath("$.encrypted").value(DEFAULT_ENCRYPTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationConfiguration() throws Exception {
        // Get the applicationConfiguration
        restApplicationConfigurationMockMvc.perform(get(SYS_APPLICATION_CONFIGURATIONS_BY_ID_API, Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationConfiguration() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        int databaseSizeBeforeUpdate = applicationConfigurationRepository.findAll().size();

        // Update the applicationConfiguration
        Optional<ApplicationConfiguration> optionalApplicationConfiguration = applicationConfigurationRepository.findById(applicationConfiguration.getId());
        if (! optionalApplicationConfiguration.isPresent()) {
            return;
        }

        ApplicationConfiguration updatedApplicationConfiguration = optionalApplicationConfiguration.get();
        // Disconnect from session so that the updates on updatedApplicationConfiguration are not directly saved in db
        em.detach(updatedApplicationConfiguration);
        updatedApplicationConfiguration
                .configKey(UPDATED_CONFIG_KEY)
                .configValue(UPDATED_CONFIG_VALUE)
                .description(new MultilingualJsonType())
                .cached(UPDATED_CACHED)
                .encrypted(UPDATED_ENCRYPTED);
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(updatedApplicationConfiguration);

        restApplicationConfigurationMockMvc.perform(put(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isOk());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeUpdate);
        ApplicationConfiguration testApplicationConfiguration = applicationConfigurationList.get(applicationConfigurationList.size() - 1);
        assertThat(testApplicationConfiguration.getConfigKey()).isEqualTo(UPDATED_CONFIG_KEY);
        assertThat(testApplicationConfiguration.getConfigValue()).isEqualTo(UPDATED_CONFIG_VALUE);
        assertThat(testApplicationConfiguration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationConfiguration.isCached()).isEqualTo(UPDATED_CACHED);
        assertThat(testApplicationConfiguration.isEncrypted()).isEqualTo(UPDATED_ENCRYPTED);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = applicationConfigurationRepository.findAll().size();

        // Create the ApplicationConfiguration
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationConfigurationMockMvc.perform(put(SYS_APPLICATION_CONFIGURATIONS_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
                .andExpect(status().isBadRequest());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationConfiguration() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        int databaseSizeBeforeDelete = applicationConfigurationRepository.findAll().size();

        // Get the applicationConfiguration
        restApplicationConfigurationMockMvc.perform(delete(SYS_APPLICATION_CONFIGURATIONS_BY_ID_API, applicationConfiguration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationConfiguration.class);
        ApplicationConfiguration applicationConfiguration1 = new ApplicationConfiguration();
        applicationConfiguration1.setId(1L);
        ApplicationConfiguration applicationConfiguration2 = new ApplicationConfiguration();
        applicationConfiguration2.setId(applicationConfiguration1.getId());
        assertThat(applicationConfiguration1).isEqualTo(applicationConfiguration2);
        applicationConfiguration2.setId(2L);
        assertThat(applicationConfiguration1).isNotEqualTo(applicationConfiguration2);
        applicationConfiguration1.setId(null);
        assertThat(applicationConfiguration1).isNotEqualTo(applicationConfiguration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationConfigurationDTO.class);
        ApplicationConfigurationDTO applicationConfigurationDTO1 = new ApplicationConfigurationDTO();
        applicationConfigurationDTO1.setId(1L);
        ApplicationConfigurationDTO applicationConfigurationDTO2 = new ApplicationConfigurationDTO();
        assertThat(applicationConfigurationDTO1).isNotEqualTo(applicationConfigurationDTO2);
        applicationConfigurationDTO2.setId(applicationConfigurationDTO1.getId());
        assertThat(applicationConfigurationDTO1).isEqualTo(applicationConfigurationDTO2);
        applicationConfigurationDTO2.setId(2L);
        assertThat(applicationConfigurationDTO1).isNotEqualTo(applicationConfigurationDTO2);
        applicationConfigurationDTO1.setId(null);
        assertThat(applicationConfigurationDTO1).isNotEqualTo(applicationConfigurationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationConfigurationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationConfigurationMapper.fromId(null)).isNull();
    }
}
