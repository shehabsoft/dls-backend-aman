package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.sys.Domain;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.repository.sys.DomainRepository;
import ae.rta.dls.backend.service.sys.DomainService;
import ae.rta.dls.backend.service.dto.sys.DomainDTO;
import ae.rta.dls.backend.service.mapper.sys.DomainMapper;
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
 * Test class for the DomainResource REST controller.
 *
 * @see DomainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class DomainResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_DESCRIPTION = "AAAAAAAAAA";

    private static final String DEFAULT_ARABIC_DESCRIPTION = "AAAAAAAAAA";

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private DomainMapper domainMapper;

    @Autowired
    private DomainService domainService;

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

    private MockMvc restDomainMockMvc;

    private Domain domain;

    private static final String SYS_DOMAINS_API = "/api/sys/domains";

    private static final String SYS_DOMAINS_BY_ID_API = SYS_DOMAINS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DomainResource domainResource = new DomainResource(domainService);
        this.restDomainMockMvc = MockMvcBuilders.standaloneSetup(domainResource)
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
    public static Domain createEntity(EntityManager em) {
        return new Domain()
            .code(DEFAULT_CODE)
            .description(new MultilingualJsonType());
    }

    @Before
    public void initTest() {
        domain = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomain() throws Exception {
        int databaseSizeBeforeCreate = domainRepository.findAll().size();

        // Create the Domain
        DomainDTO domainDTO = domainMapper.toDto(domain);
        restDomainMockMvc.perform(post(SYS_DOMAINS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isCreated());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeCreate + 1);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDomain.getDescription()).isEqualTo(new MultilingualJsonType());
    }

    @Test
    @Transactional
    public void createDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domainRepository.findAll().size();

        // Create the Domain with an existing ID
        domain.setId(1L);
        DomainDTO domainDTO = domainMapper.toDto(domain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomainMockMvc.perform(post(SYS_DOMAINS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = domainRepository.findAll().size();
        // set the field null
        domain.setCode(null);

        // Create the Domain, which fails.
        DomainDTO domainDTO = domainMapper.toDto(domain);

        restDomainMockMvc.perform(post(SYS_DOMAINS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isBadRequest());

        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnglishDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = domainRepository.findAll().size();
        // set the field null
        domain.setDescription(null);

        // Create the Domain, which fails.
        DomainDTO domainDTO = domainMapper.toDto(domain);

        restDomainMockMvc.perform(post(SYS_DOMAINS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isBadRequest());

        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArabicDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = domainRepository.findAll().size();
        // set the field null
        domain.setDescription(null);

        // Create the Domain, which fails.
        DomainDTO domainDTO = domainMapper.toDto(domain);

        restDomainMockMvc.perform(post(SYS_DOMAINS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isBadRequest());

        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDomains() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get all the domainList
        restDomainMockMvc.perform(get("/api/sys/domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domain.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].englishDescription").value(hasItem(DEFAULT_ENGLISH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].arabicDescription").value(hasItem(DEFAULT_ARABIC_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        // Get the domain
        restDomainMockMvc.perform(get(SYS_DOMAINS_BY_ID_API, domain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(domain.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.englishDescription").value(DEFAULT_ENGLISH_DESCRIPTION))
            .andExpect(jsonPath("$.arabicDescription").value(DEFAULT_ARABIC_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingDomain() throws Exception {
        // Get the domain
        restDomainMockMvc.perform(get(SYS_DOMAINS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Update the domain
        Optional<Domain> optionalDomain = domainRepository.findById(domain.getId());
        if (! optionalDomain.isPresent()) {
            return;
        }

        Domain updatedDomain = optionalDomain.get();
        // Disconnect from session so that the updates on updatedDomain are not directly saved in db
        em.detach(updatedDomain);
        updatedDomain
            .code(UPDATED_CODE)
            .description(new MultilingualJsonType());
        DomainDTO domainDTO = domainMapper.toDto(updatedDomain);

        restDomainMockMvc.perform(put(SYS_DOMAINS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isOk());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
        Domain testDomain = domainList.get(domainList.size() - 1);
        assertThat(testDomain.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDomain.getDescription()).isEqualTo(new MultilingualJsonType());
    }

    @Test
    @Transactional
    public void updateNonExistingDomain() throws Exception {
        int databaseSizeBeforeUpdate = domainRepository.findAll().size();

        // Create the Domain
        DomainDTO domainDTO = domainMapper.toDto(domain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomainMockMvc.perform(put(SYS_DOMAINS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Domain in the database
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomain() throws Exception {
        // Initialize the database
        domainRepository.saveAndFlush(domain);

        int databaseSizeBeforeDelete = domainRepository.findAll().size();

        // Delete the domain
        restDomainMockMvc.perform(delete(SYS_DOMAINS_BY_ID_API, domain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Domain> domainList = domainRepository.findAll();
        assertThat(domainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domain.class);
        Domain domain1 = new Domain();
        domain1.setId(1L);
        Domain domain2 = new Domain();
        domain2.setId(domain1.getId());
        assertThat(domain1).isEqualTo(domain2);
        domain2.setId(2L);
        assertThat(domain1).isNotEqualTo(domain2);
        domain1.setId(null);
        assertThat(domain1).isNotEqualTo(domain2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DomainDTO.class);
        DomainDTO domainDTO1 = new DomainDTO();
        domainDTO1.setId(1L);
        DomainDTO domainDTO2 = new DomainDTO();
        assertThat(domainDTO1).isNotEqualTo(domainDTO2);
        domainDTO2.setId(domainDTO1.getId());
        assertThat(domainDTO1).isEqualTo(domainDTO2);
        domainDTO2.setId(2L);
        assertThat(domainDTO1).isNotEqualTo(domainDTO2);
        domainDTO1.setId(null);
        assertThat(domainDTO1).isNotEqualTo(domainDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(domainMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(domainMapper.fromId(null)).isNull();
    }
}
