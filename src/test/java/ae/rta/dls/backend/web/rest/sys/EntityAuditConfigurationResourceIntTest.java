package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.sys.EntityAuditConfiguration;
import ae.rta.dls.backend.repository.sys.EntityAuditConfigurationRepository;
import ae.rta.dls.backend.service.dto.sys.EntityAuditConfigurationDTO;
import ae.rta.dls.backend.service.mapper.sys.EntityAuditConfigurationMapper;
import ae.rta.dls.backend.service.sys.EntityAuditConfigurationService;
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
 * Test class for the EntityAuditConfigurationResource REST controller.
 *
 * @see EntityAuditConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class EntityAuditConfigurationResourceIntTest {

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NEEDS_AUDIT = false;
    private static final Boolean UPDATED_NEEDS_AUDIT = true;

    @Autowired
    private EntityAuditConfigurationRepository entityAuditConfigurationRepository;

    @Autowired
    private EntityAuditConfigurationService entityAuditConfigurationService;

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

    @Autowired
    private EntityAuditConfigurationMapper entityAuditConfigurationMapper;


    private MockMvc restEntityAuditConfigurationMockMvc;

    private EntityAuditConfiguration entityAuditConfiguration;

    private static final String SYS_ENTITY_AUDIT_CONFIGURATION_API = "/api/sys/entity-audit-configurations";

    private static final String SYS_ENTITY_AUDIT_CONFIGURATION_BY_ID_API = SYS_ENTITY_AUDIT_CONFIGURATION_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntityAuditConfigurationResource entityAuditConfigurationResource = new EntityAuditConfigurationResource(entityAuditConfigurationService);
        this.restEntityAuditConfigurationMockMvc = MockMvcBuilders.standaloneSetup(entityAuditConfigurationResource)
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
    public static EntityAuditConfiguration createEntity(EntityManager em) {
        return new EntityAuditConfiguration()
            .entityName(DEFAULT_ENTITY_NAME)
            .needsAudit(DEFAULT_NEEDS_AUDIT);
    }

    @Before
    public void initTest() {
        entityAuditConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntityAuditConfiguration() throws Exception {
        int databaseSizeBeforeCreate = entityAuditConfigurationRepository.findAll().size();

        // Create the EntityAuditConfiguration
        restEntityAuditConfigurationMockMvc.perform(post(SYS_ENTITY_AUDIT_CONFIGURATION_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityAuditConfiguration)))
            .andExpect(status().isCreated());

        // Validate the EntityAuditConfiguration in the database
        List<EntityAuditConfiguration> entityAuditConfigurationList = entityAuditConfigurationRepository.findAll();
        assertThat(entityAuditConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        EntityAuditConfiguration testEntityAuditConfiguration = entityAuditConfigurationList.get(entityAuditConfigurationList.size() - 1);
        assertThat(testEntityAuditConfiguration.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testEntityAuditConfiguration.isNeedsAudit()).isEqualTo(DEFAULT_NEEDS_AUDIT);
    }

    @Test
    @Transactional
    public void createEntityAuditConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entityAuditConfigurationRepository.findAll().size();

        // Create the EntityAuditConfiguration with an existing ID
        entityAuditConfiguration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityAuditConfigurationMockMvc.perform(post(SYS_ENTITY_AUDIT_CONFIGURATION_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityAuditConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the EntityAuditConfiguration in the database
        List<EntityAuditConfiguration> entityAuditConfigurationList = entityAuditConfigurationRepository.findAll();
        assertThat(entityAuditConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEntityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityAuditConfigurationRepository.findAll().size();
        // set the field null
        entityAuditConfiguration.setEntityName(null);

        // Create the EntityAuditConfiguration, which fails.

        restEntityAuditConfigurationMockMvc.perform(post(SYS_ENTITY_AUDIT_CONFIGURATION_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityAuditConfiguration)))
            .andExpect(status().isBadRequest());

        List<EntityAuditConfiguration> entityAuditConfigurationList = entityAuditConfigurationRepository.findAll();
        assertThat(entityAuditConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNeedsAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityAuditConfigurationRepository.findAll().size();
        // set the field null
        entityAuditConfiguration.setNeedsAudit(null);

        // Create the EntityAuditConfiguration, which fails.

        restEntityAuditConfigurationMockMvc.perform(post(SYS_ENTITY_AUDIT_CONFIGURATION_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityAuditConfiguration)))
            .andExpect(status().isBadRequest());

        List<EntityAuditConfiguration> entityAuditConfigurationList = entityAuditConfigurationRepository.findAll();
        assertThat(entityAuditConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntityAuditConfigurations() throws Exception {
        // Initialize the database
        entityAuditConfigurationRepository.saveAndFlush(entityAuditConfiguration);

        // Get all the entityAuditConfigurationList
        restEntityAuditConfigurationMockMvc.perform(get("/api/entity-audit-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityAuditConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].needsAudit").value(hasItem(DEFAULT_NEEDS_AUDIT.booleanValue())));
    }

    @Test
    @Transactional
    public void getEntityAuditConfiguration() throws Exception {
        // Initialize the database
        entityAuditConfigurationRepository.saveAndFlush(entityAuditConfiguration);

        // Get the entityAuditConfiguration
        restEntityAuditConfigurationMockMvc.perform(get(SYS_ENTITY_AUDIT_CONFIGURATION_BY_ID_API, entityAuditConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entityAuditConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.needsAudit").value(DEFAULT_NEEDS_AUDIT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntityAuditConfiguration() throws Exception {
        // Get the entityAuditConfiguration
        restEntityAuditConfigurationMockMvc.perform(get(SYS_ENTITY_AUDIT_CONFIGURATION_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntityAuditConfiguration() throws Exception {
        // Initialize the database
        EntityAuditConfigurationDTO entityAuditConfigurationDTO = entityAuditConfigurationMapper.toDto(entityAuditConfiguration);
        entityAuditConfigurationService.save(entityAuditConfigurationDTO);

        int databaseSizeBeforeUpdate = entityAuditConfigurationRepository.findAll().size();

        // Update the entityAuditConfiguration
        Optional<EntityAuditConfiguration> optionalEntityAuditConfiguration = entityAuditConfigurationRepository.findById(entityAuditConfiguration.getId());
        if (! optionalEntityAuditConfiguration.isPresent()) {
            return;
        }

        EntityAuditConfiguration updatedEntityAuditConfiguration = optionalEntityAuditConfiguration.get();
        // Disconnect from session so that the updates on updatedEntityAuditConfiguration are not directly saved in db
        em.detach(updatedEntityAuditConfiguration);
        updatedEntityAuditConfiguration
            .entityName(UPDATED_ENTITY_NAME)
            .needsAudit(UPDATED_NEEDS_AUDIT);

        restEntityAuditConfigurationMockMvc.perform(put(SYS_ENTITY_AUDIT_CONFIGURATION_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntityAuditConfiguration)))
            .andExpect(status().isOk());

        // Validate the EntityAuditConfiguration in the database
        List<EntityAuditConfiguration> entityAuditConfigurationList = entityAuditConfigurationRepository.findAll();
        assertThat(entityAuditConfigurationList).hasSize(databaseSizeBeforeUpdate);
        EntityAuditConfiguration testEntityAuditConfiguration = entityAuditConfigurationList.get(entityAuditConfigurationList.size() - 1);
        assertThat(testEntityAuditConfiguration.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testEntityAuditConfiguration.isNeedsAudit()).isEqualTo(UPDATED_NEEDS_AUDIT);
    }

    @Test
    @Transactional
    public void updateNonExistingEntityAuditConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = entityAuditConfigurationRepository.findAll().size();

        // Create the EntityAuditConfiguration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityAuditConfigurationMockMvc.perform(put(SYS_ENTITY_AUDIT_CONFIGURATION_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityAuditConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the EntityAuditConfiguration in the database
        List<EntityAuditConfiguration> entityAuditConfigurationList = entityAuditConfigurationRepository.findAll();
        assertThat(entityAuditConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntityAuditConfiguration() throws Exception {
        // Initialize the database
        EntityAuditConfigurationDTO entityAuditConfigurationDTO = entityAuditConfigurationMapper.toDto(entityAuditConfiguration);
        entityAuditConfigurationService.save(entityAuditConfigurationDTO);

        int databaseSizeBeforeDelete = entityAuditConfigurationRepository.findAll().size();

        // Get the entityAuditConfiguration
        restEntityAuditConfigurationMockMvc.perform(delete(SYS_ENTITY_AUDIT_CONFIGURATION_BY_ID_API, entityAuditConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntityAuditConfiguration> entityAuditConfigurationList = entityAuditConfigurationRepository.findAll();
        assertThat(entityAuditConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityAuditConfiguration.class);
        EntityAuditConfiguration entityAuditConfiguration1 = new EntityAuditConfiguration();
        entityAuditConfiguration1.setId(1L);
        EntityAuditConfiguration entityAuditConfiguration2 = new EntityAuditConfiguration();
        entityAuditConfiguration2.setId(entityAuditConfiguration1.getId());
        assertThat(entityAuditConfiguration1).isEqualTo(entityAuditConfiguration2);
        entityAuditConfiguration2.setId(2L);
        assertThat(entityAuditConfiguration1).isNotEqualTo(entityAuditConfiguration2);
        entityAuditConfiguration1.setId(null);
        assertThat(entityAuditConfiguration1).isNotEqualTo(entityAuditConfiguration2);
    }
}
