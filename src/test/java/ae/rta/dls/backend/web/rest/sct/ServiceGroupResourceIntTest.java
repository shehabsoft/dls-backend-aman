package ae.rta.dls.backend.web.rest.sct;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.enumeration.trn.ServiceGroupStatus;
import ae.rta.dls.backend.domain.sct.ServiceGroup;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.repository.sct.ServiceGroupRepository;
import ae.rta.dls.backend.service.sct.ServiceGroupService;
import ae.rta.dls.backend.service.dto.sct.ServiceGroupDTO;
import ae.rta.dls.backend.service.mapper.sct.ServiceGroupMapper;
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
 * Test class for the ServiceGroupResource REST controller.
 *
 * @see ServiceGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ServiceGroupResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final MultilingualJsonType DEFAULT_NAME = new MultilingualJsonType();
    private static final MultilingualJsonType UPDATED_NAME = new MultilingualJsonType();

    private static final ServiceGroupStatus DEFAULT_STATUS = ServiceGroupStatus.ACTIVE;
    private static final ServiceGroupStatus UPDATED_STATUS = ServiceGroupStatus.INACTIVE;

    @Autowired
    private ServiceGroupRepository serviceGroupRepository;

    @Autowired
    private ServiceGroupMapper serviceGroupMapper;

    @Autowired
    private ServiceGroupService serviceGroupService;

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

    private MockMvc restServiceGroupMockMvc;

    private ServiceGroup serviceGroup;

    private static final String SCT_SERVICE_GROUPS_API = "/api/sct/service-groups";

    private static final String SCT_SERVICE_GROUPS_BY_ID_API = SCT_SERVICE_GROUPS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceGroupResource serviceGroupResource = new ServiceGroupResource(serviceGroupService);
        this.restServiceGroupMockMvc = MockMvcBuilders.standaloneSetup(serviceGroupResource)
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
    public static ServiceGroup createEntity(EntityManager em) {
        return new ServiceGroup()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
    }

    @Before
    public void initTest() {
        serviceGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceGroup() throws Exception {
        int databaseSizeBeforeCreate = serviceGroupRepository.findAll().size();

        // Create the ServiceGroup
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);
        restServiceGroupMockMvc.perform(post(SCT_SERVICE_GROUPS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceGroup testServiceGroup = serviceGroupList.get(serviceGroupList.size() - 1);
        assertThat(testServiceGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testServiceGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceGroup.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createServiceGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceGroupRepository.findAll().size();

        // Create the ServiceGroup with an existing ID
        serviceGroup.setId(1L);
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceGroupMockMvc.perform(post(SCT_SERVICE_GROUPS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceGroupRepository.findAll().size();
        // set the field null
        serviceGroup.setCode(null);

        // Create the ServiceGroup, which fails.
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);

        restServiceGroupMockMvc.perform(post(SCT_SERVICE_GROUPS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceGroupRepository.findAll().size();
        // set the field null
        serviceGroup.setStatus(null);

        // Create the ServiceGroup, which fails.
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);

        restServiceGroupMockMvc.perform(post(SCT_SERVICE_GROUPS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceGroups() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get all the serviceGroupList
        restServiceGroupMockMvc.perform(get("/api/sct/service-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(Matchers.hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getServiceGroup() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        // Get the serviceGroup
        restServiceGroupMockMvc.perform(get(SCT_SERVICE_GROUPS_BY_ID_API, serviceGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingServiceGroup() throws Exception {
        // Get the serviceGroup
        restServiceGroupMockMvc.perform(get(SCT_SERVICE_GROUPS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceGroup() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        int databaseSizeBeforeUpdate = serviceGroupRepository.findAll().size();

        // Update the serviceGroup
        Optional<ServiceGroup> optionalServiceGroup = serviceGroupRepository.findById(serviceGroup.getId());
        if (! optionalServiceGroup.isPresent()) {
            return;
        }

        ServiceGroup updatedServiceGroup = optionalServiceGroup.get();
        // Disconnect from session so that the updates on updatedServiceGroup are not directly saved in db
        em.detach(updatedServiceGroup);
        updatedServiceGroup
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(updatedServiceGroup);

        restServiceGroupMockMvc.perform(put(SCT_SERVICE_GROUPS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeUpdate);
        ServiceGroup testServiceGroup = serviceGroupList.get(serviceGroupList.size() - 1);
        assertThat(testServiceGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testServiceGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceGroup.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceGroup() throws Exception {
        int databaseSizeBeforeUpdate = serviceGroupRepository.findAll().size();

        // Create the ServiceGroup
        ServiceGroupDTO serviceGroupDTO = serviceGroupMapper.toDto(serviceGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceGroupMockMvc.perform(put(SCT_SERVICE_GROUPS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceGroup in the database
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceGroup() throws Exception {
        // Initialize the database
        serviceGroupRepository.saveAndFlush(serviceGroup);

        int databaseSizeBeforeDelete = serviceGroupRepository.findAll().size();

        // Get the serviceGroup
        restServiceGroupMockMvc.perform(delete(SCT_SERVICE_GROUPS_BY_ID_API, serviceGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceGroup> serviceGroupList = serviceGroupRepository.findAll();
        assertThat(serviceGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceGroup.class);
        ServiceGroup serviceGroup1 = new ServiceGroup();
        serviceGroup1.setId(1L);
        ServiceGroup serviceGroup2 = new ServiceGroup();
        serviceGroup2.setId(serviceGroup1.getId());
        assertThat(serviceGroup1).isEqualTo(serviceGroup2);
        serviceGroup2.setId(2L);
        assertThat(serviceGroup1).isNotEqualTo(serviceGroup2);
        serviceGroup1.setId(null);
        assertThat(serviceGroup1).isNotEqualTo(serviceGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceGroupDTO.class);
        ServiceGroupDTO serviceGroupDTO1 = new ServiceGroupDTO();
        serviceGroupDTO1.setId(1L);
        ServiceGroupDTO serviceGroupDTO2 = new ServiceGroupDTO();
        assertThat(serviceGroupDTO1).isNotEqualTo(serviceGroupDTO2);
        serviceGroupDTO2.setId(serviceGroupDTO1.getId());
        assertThat(serviceGroupDTO1).isEqualTo(serviceGroupDTO2);
        serviceGroupDTO2.setId(2L);
        assertThat(serviceGroupDTO1).isNotEqualTo(serviceGroupDTO2);
        serviceGroupDTO1.setId(null);
        assertThat(serviceGroupDTO1).isNotEqualTo(serviceGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceGroupMapper.fromId(null)).isNull();
    }
}
