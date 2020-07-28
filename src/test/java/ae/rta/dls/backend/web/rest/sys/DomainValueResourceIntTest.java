package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.sys.DomainValue;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.repository.sys.DomainValueRepository;
import ae.rta.dls.backend.service.sys.DomainValueService;
import ae.rta.dls.backend.service.dto.sys.DomainValueDTO;
import ae.rta.dls.backend.service.mapper.sys.DomainValueMapper;
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
 * Test class for the DomainValueResource REST controller.
 *
 * @see DomainValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class DomainValueResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final MultilingualJsonType DEFAULT_DESCRIPTION = new MultilingualJsonType("الوصف","description");
    private static final MultilingualJsonType UPDATED_DESCRIPTION = new MultilingualJsonType("تعديل الوصف","updated description");

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    @Autowired
    private DomainValueRepository domainValueRepository;

    @Autowired
    private DomainValueMapper domainValueMapper;

    @Autowired
    private DomainValueService domainValueService;

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

    private MockMvc restDomainValueMockMvc;

    private DomainValue domainValue;

    private static final String SYS_DOMAIN_VALUES_API = "/api/sys/domain-values";

    private static final String SYS_DOMAIN_VALUES_BY_ID_API = SYS_DOMAIN_VALUES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DomainValueResource domainValueResource = new DomainValueResource(domainValueService);
        this.restDomainValueMockMvc = MockMvcBuilders.standaloneSetup(domainValueResource)
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
    public static DomainValue createEntity(EntityManager em) {
        return new DomainValue()
                .value(DEFAULT_VALUE)
                .description(DEFAULT_DESCRIPTION)
                .sortOrder(DEFAULT_SORT_ORDER);
    }

    @Before
    public void initTest() {
        domainValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomainValue() throws Exception {
        int databaseSizeBeforeCreate = domainValueRepository.findAll().size();

        // Create the DomainValue
        DomainValueDTO domainValueDTO = domainValueMapper.toDto(domainValue);
        restDomainValueMockMvc.perform(post(SYS_DOMAIN_VALUES_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domainValueDTO)))
                .andExpect(status().isCreated());

        // Validate the DomainValue in the database
        List<DomainValue> domainValueList = domainValueRepository.findAll();
        assertThat(domainValueList).hasSize(databaseSizeBeforeCreate + 1);
        DomainValue testDomainValue = domainValueList.get(domainValueList.size() - 1);
        assertThat(testDomainValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDomainValue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDomainValue.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
    }

    @Test
    @Transactional
    public void createDomainValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domainValueRepository.findAll().size();

        // Create the DomainValue with an existing ID
        domainValue.setId(1L);
        DomainValueDTO domainValueDTO = domainValueMapper.toDto(domainValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomainValueMockMvc.perform(post(SYS_DOMAIN_VALUES_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domainValueDTO)))
                .andExpect(status().isBadRequest());

        // Validate the DomainValue in the database
        List<DomainValue> domainValueList = domainValueRepository.findAll();
        assertThat(domainValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = domainValueRepository.findAll().size();
        // set the field null
        domainValue.setDescription(null);

        // Create the DomainValue, which fails.
        DomainValueDTO domainValueDTO = domainValueMapper.toDto(domainValue);

        restDomainValueMockMvc.perform(post(SYS_DOMAIN_VALUES_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domainValueDTO)))
                .andExpect(status().isBadRequest());

        List<DomainValue> domainValueList = domainValueRepository.findAll();
        assertThat(domainValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDomainValues() throws Exception {
        // Initialize the database
        domainValueRepository.saveAndFlush(domainValue);

        // Get all the domainValueList
        restDomainValueMockMvc.perform(get("/api/sys/domain-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domainValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void getDomainValue() throws Exception {
        // Initialize the database
        domainValueRepository.saveAndFlush(domainValue);

        // Get the domainValue
        restDomainValueMockMvc.perform(get(SYS_DOMAIN_VALUES_BY_ID_API, domainValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(domainValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingDomainValue() throws Exception {
        // Get the domainValue
        restDomainValueMockMvc.perform(get(SYS_DOMAIN_VALUES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomainValue() throws Exception {
        // Initialize the database
        domainValueRepository.saveAndFlush(domainValue);

        int databaseSizeBeforeUpdate = domainValueRepository.findAll().size();

        // Update the domainValue
        Optional<DomainValue> optionalDomainValue = domainValueRepository.findById(domainValue.getId());
        if (! optionalDomainValue.isPresent()) {
            return;
        }

        DomainValue updatedDomainValue = optionalDomainValue.get();
        // Disconnect from session so that the updates on updatedDomainValue are not directly saved in db
        em.detach(updatedDomainValue);
        updatedDomainValue
                .value(UPDATED_VALUE)
                .description(UPDATED_DESCRIPTION)
                .sortOrder(UPDATED_SORT_ORDER);
        DomainValueDTO domainValueDTO = domainValueMapper.toDto(updatedDomainValue);

        restDomainValueMockMvc.perform(put(SYS_DOMAIN_VALUES_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domainValueDTO)))
                .andExpect(status().isOk());

        // Validate the DomainValue in the database
        List<DomainValue> domainValueList = domainValueRepository.findAll();
        assertThat(domainValueList).hasSize(databaseSizeBeforeUpdate);
        DomainValue testDomainValue = domainValueList.get(domainValueList.size() - 1);
        assertThat(testDomainValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDomainValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDomainValue.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingDomainValue() throws Exception {
        int databaseSizeBeforeUpdate = domainValueRepository.findAll().size();

        // Create the DomainValue
        DomainValueDTO domainValueDTO = domainValueMapper.toDto(domainValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomainValueMockMvc.perform(put(SYS_DOMAIN_VALUES_API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domainValueDTO)))
                .andExpect(status().isBadRequest());

        // Validate the DomainValue in the database
        List<DomainValue> domainValueList = domainValueRepository.findAll();
        assertThat(domainValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomainValue() throws Exception {
        // Initialize the database
        domainValueRepository.saveAndFlush(domainValue);

        int databaseSizeBeforeDelete = domainValueRepository.findAll().size();

        // Delete the domainValue
        restDomainValueMockMvc.perform(delete(SYS_DOMAIN_VALUES_BY_ID_API, domainValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DomainValue> domainValueList = domainValueRepository.findAll();
        assertThat(domainValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DomainValue.class);
        DomainValue domainValue1 = new DomainValue();
        domainValue1.setId(1L);
        DomainValue domainValue2 = new DomainValue();
        domainValue2.setId(domainValue1.getId());
        assertThat(domainValue1).isEqualTo(domainValue2);
        domainValue2.setId(2L);
        assertThat(domainValue1).isNotEqualTo(domainValue2);
        domainValue1.setId(null);
        assertThat(domainValue1).isNotEqualTo(domainValue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DomainValueDTO.class);
        DomainValueDTO domainValueDTO1 = new DomainValueDTO();
        domainValueDTO1.setId(1L);
        DomainValueDTO domainValueDTO2 = new DomainValueDTO();
        assertThat(domainValueDTO1).isNotEqualTo(domainValueDTO2);
        domainValueDTO2.setId(domainValueDTO1.getId());
        assertThat(domainValueDTO1).isEqualTo(domainValueDTO2);
        domainValueDTO2.setId(2L);
        assertThat(domainValueDTO1).isNotEqualTo(domainValueDTO2);
        domainValueDTO1.setId(null);
        assertThat(domainValueDTO1).isNotEqualTo(domainValueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(domainValueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(domainValueMapper.fromId(null)).isNull();
    }
}
