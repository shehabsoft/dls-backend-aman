package ae.rta.dls.backend.web.rest.sdm;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.enumeration.sdm.LicenseCategoryStatus;
import ae.rta.dls.backend.domain.sdm.LicenseCategory;
import ae.rta.dls.backend.repository.sdm.LicenseCategoryRepository;
import ae.rta.dls.backend.service.sdm.LicenseCategoryService;
import ae.rta.dls.backend.service.dto.sdm.LicenseCategoryDTO;
import ae.rta.dls.backend.service.mapper.sdm.LicenseCategoryMapper;
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
 * Test class for the LicenseCategoryResource REST controller.
 *
 * @see LicenseCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class LicenseCategoryResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LicenseCategoryStatus DEFAULT_STATUS = LicenseCategoryStatus.ACTIVE;
    private static final LicenseCategoryStatus UPDATED_STATUS = LicenseCategoryStatus.INACTIVE;

    @Autowired
    private LicenseCategoryRepository licenseCategoryRepository;

    @Autowired
    private LicenseCategoryMapper licenseCategoryMapper;

    @Autowired
    private LicenseCategoryService licenseCategoryService;

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

    private MockMvc restLicenseCategoryMockMvc;

    private LicenseCategory licenseCategory;

    private static final String SDM_LICENSE_CATEGORIES_API = "/api/sdm/license-categories";

    private static final String SDM_LICENSE_CATEGORIES_BY_ID_API = SDM_LICENSE_CATEGORIES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LicenseCategoryResource licenseCategoryResource = new LicenseCategoryResource(licenseCategoryService);
        this.restLicenseCategoryMockMvc = MockMvcBuilders.standaloneSetup(licenseCategoryResource)
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
    public static LicenseCategory createEntity(EntityManager em) {
        return new LicenseCategory()
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
    }

    @Before
    public void initTest() {
        licenseCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicenseCategory() throws Exception {
        int databaseSizeBeforeCreate = licenseCategoryRepository.findAll().size();

        // Create the LicenseCategory
        LicenseCategoryDTO licenseCategoryDTO = licenseCategoryMapper.toDto(licenseCategory);
        restLicenseCategoryMockMvc.perform(post(SDM_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the LicenseCategory in the database
        List<LicenseCategory> licenseCategoryList = licenseCategoryRepository.findAll();
        assertThat(licenseCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        LicenseCategory testLicenseCategory = licenseCategoryList.get(licenseCategoryList.size() - 1);
        assertThat(testLicenseCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLicenseCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createLicenseCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenseCategoryRepository.findAll().size();

        // Create the LicenseCategory with an existing ID
        licenseCategory.setId(1L);
        LicenseCategoryDTO licenseCategoryDTO = licenseCategoryMapper.toDto(licenseCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenseCategoryMockMvc.perform(post(SDM_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LicenseCategory in the database
        List<LicenseCategory> licenseCategoryList = licenseCategoryRepository.findAll();
        assertThat(licenseCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = licenseCategoryRepository.findAll().size();
        // set the field null
        licenseCategory.setCode(null);

        // Create the LicenseCategory, which fails.
        LicenseCategoryDTO licenseCategoryDTO = licenseCategoryMapper.toDto(licenseCategory);

        restLicenseCategoryMockMvc.perform(post(SDM_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<LicenseCategory> licenseCategoryList = licenseCategoryRepository.findAll();
        assertThat(licenseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = licenseCategoryRepository.findAll().size();
        // set the field null
        licenseCategory.setStatus(null);

        // Create the LicenseCategory, which fails.
        LicenseCategoryDTO licenseCategoryDTO = licenseCategoryMapper.toDto(licenseCategory);

        restLicenseCategoryMockMvc.perform(post(SDM_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<LicenseCategory> licenseCategoryList = licenseCategoryRepository.findAll();
        assertThat(licenseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLicenseCategories() throws Exception {
        // Initialize the database
        licenseCategoryRepository.saveAndFlush(licenseCategory);

        // Get all the licenseCategoryList
        restLicenseCategoryMockMvc.perform(get("/api/sdm/license-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenseCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.value())));
    }

    @Test
    @Transactional
    public void getLicenseCategory() throws Exception {
        // Initialize the database
        licenseCategoryRepository.saveAndFlush(licenseCategory);

        // Get the licenseCategory
        restLicenseCategoryMockMvc.perform(get(SDM_LICENSE_CATEGORIES_BY_ID_API, licenseCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(licenseCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.value()));
    }

    @Test
    @Transactional
    public void getNonExistingLicenseCategory() throws Exception {
        // Get the licenseCategory
        restLicenseCategoryMockMvc.perform(get(SDM_LICENSE_CATEGORIES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicenseCategory() throws Exception {
        // Initialize the database
        licenseCategoryRepository.saveAndFlush(licenseCategory);

        int databaseSizeBeforeUpdate = licenseCategoryRepository.findAll().size();

        // Update the licenseCategory
        Optional<LicenseCategory> optionalLicenseCategory = licenseCategoryRepository.findById(licenseCategory.getId());
        if (! optionalLicenseCategory.isPresent()) {
            return;
        }

        LicenseCategory updatedLicenseCategory = optionalLicenseCategory.get();
        // Disconnect from session so that the updates on updatedLicenseCategory are not directly saved in db
        em.detach(updatedLicenseCategory);
        updatedLicenseCategory
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        LicenseCategoryDTO licenseCategoryDTO = licenseCategoryMapper.toDto(updatedLicenseCategory);

        restLicenseCategoryMockMvc.perform(put(SDM_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the LicenseCategory in the database
        List<LicenseCategory> licenseCategoryList = licenseCategoryRepository.findAll();
        assertThat(licenseCategoryList).hasSize(databaseSizeBeforeUpdate);
        LicenseCategory testLicenseCategory = licenseCategoryList.get(licenseCategoryList.size() - 1);
        assertThat(testLicenseCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLicenseCategory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingLicenseCategory() throws Exception {
        int databaseSizeBeforeUpdate = licenseCategoryRepository.findAll().size();

        // Create the LicenseCategory
        LicenseCategoryDTO licenseCategoryDTO = licenseCategoryMapper.toDto(licenseCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseCategoryMockMvc.perform(put(SDM_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LicenseCategory in the database
        List<LicenseCategory> licenseCategoryList = licenseCategoryRepository.findAll();
        assertThat(licenseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLicenseCategory() throws Exception {
        // Initialize the database
        licenseCategoryRepository.saveAndFlush(licenseCategory);

        int databaseSizeBeforeDelete = licenseCategoryRepository.findAll().size();

        // Get the licenseCategory
        restLicenseCategoryMockMvc.perform(delete(SDM_LICENSE_CATEGORIES_BY_ID_API, licenseCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LicenseCategory> licenseCategoryList = licenseCategoryRepository.findAll();
        assertThat(licenseCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseCategory.class);
        LicenseCategory licenseCategory1 = new LicenseCategory();
        licenseCategory1.setId(1L);
        LicenseCategory licenseCategory2 = new LicenseCategory();
        licenseCategory2.setId(licenseCategory1.getId());
        assertThat(licenseCategory1).isEqualTo(licenseCategory2);
        licenseCategory2.setId(2L);
        assertThat(licenseCategory1).isNotEqualTo(licenseCategory2);
        licenseCategory1.setId(null);
        assertThat(licenseCategory1).isNotEqualTo(licenseCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseCategoryDTO.class);
        LicenseCategoryDTO licenseCategoryDTO1 = new LicenseCategoryDTO();
        licenseCategoryDTO1.setId(1L);
        LicenseCategoryDTO licenseCategoryDTO2 = new LicenseCategoryDTO();
        assertThat(licenseCategoryDTO1).isNotEqualTo(licenseCategoryDTO2);
        licenseCategoryDTO2.setId(licenseCategoryDTO1.getId());
        assertThat(licenseCategoryDTO1).isEqualTo(licenseCategoryDTO2);
        licenseCategoryDTO2.setId(2L);
        assertThat(licenseCategoryDTO1).isNotEqualTo(licenseCategoryDTO2);
        licenseCategoryDTO1.setId(null);
        assertThat(licenseCategoryDTO1).isNotEqualTo(licenseCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(licenseCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(licenseCategoryMapper.fromId(null)).isNull();
    }
}
