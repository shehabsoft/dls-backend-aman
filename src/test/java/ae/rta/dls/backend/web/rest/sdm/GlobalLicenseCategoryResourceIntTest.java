package ae.rta.dls.backend.web.rest.sdm;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.sdm.GlobalLicenseCategory;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.repository.sdm.GlobalLicenseCategoryRepository;
import ae.rta.dls.backend.service.dto.sdm.GlobalLicenseCategoryDTO;
import ae.rta.dls.backend.service.sdm.GlobalLicenseCategoryService;
import ae.rta.dls.backend.service.mapper.sdm.GlobalLicenseCategoryMapper;
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
 * Test class for the GlobalLicenseCategoryResource REST controller.
 *
 * @see GlobalLicenseCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class GlobalLicenseCategoryResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final MultilingualJsonType DEFAULT_NAME = new MultilingualJsonType();
    private static final MultilingualJsonType UPDATED_NAME = new MultilingualJsonType();

    @Autowired
    private GlobalLicenseCategoryRepository globalLicenseCategoryRepository;

    @Autowired
    private GlobalLicenseCategoryMapper globalLicenseCategoryMapper;

    @Autowired
    private GlobalLicenseCategoryService globalLicenseCategoryService;

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

    private MockMvc restGlobalLicenseCategoryMockMvc;

    private GlobalLicenseCategory globalLicenseCategory;

    private static final String SDM_GLOBAL_LICENSE_CATEGORIES_API = "/api/sdm/global-license-categories";

    private static final String SDM_GLOBAL_LICENSE_CATEGORIES_BY_ID_API = SDM_GLOBAL_LICENSE_CATEGORIES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlobalLicenseCategoryResource globalLicenseCategoryResource = new GlobalLicenseCategoryResource(globalLicenseCategoryService);
        this.restGlobalLicenseCategoryMockMvc = MockMvcBuilders.standaloneSetup(globalLicenseCategoryResource)
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
    public static GlobalLicenseCategory createEntity(EntityManager em) {
        return new GlobalLicenseCategory()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
    }

    @Before
    public void initTest() {
        globalLicenseCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlobalLicenseCategory() throws Exception {
        int databaseSizeBeforeCreate = globalLicenseCategoryRepository.findAll().size();

        // Create the GlobalLicenseCategory
        GlobalLicenseCategoryDTO globalLicenseCategoryDTO = globalLicenseCategoryMapper.toDto(globalLicenseCategory);
        restGlobalLicenseCategoryMockMvc.perform(post(SDM_GLOBAL_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalLicenseCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the GlobalLicenseCategory in the database
        List<GlobalLicenseCategory> globalLicenseCategoryList = globalLicenseCategoryRepository.findAll();
        assertThat(globalLicenseCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        GlobalLicenseCategory testGlobalLicenseCategory = globalLicenseCategoryList.get(globalLicenseCategoryList.size() - 1);
        assertThat(testGlobalLicenseCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGlobalLicenseCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGlobalLicenseCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = globalLicenseCategoryRepository.findAll().size();

        // Create the GlobalLicenseCategory with an existing ID
        globalLicenseCategory.setId(1L);
        GlobalLicenseCategoryDTO globalLicenseCategoryDTO = globalLicenseCategoryMapper.toDto(globalLicenseCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlobalLicenseCategoryMockMvc.perform(post(SDM_GLOBAL_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalLicenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalLicenseCategory in the database
        List<GlobalLicenseCategory> globalLicenseCategoryList = globalLicenseCategoryRepository.findAll();
        assertThat(globalLicenseCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalLicenseCategoryRepository.findAll().size();
        // set the field null
        globalLicenseCategory.setCode(null);

        // Create the GlobalLicenseCategory, which fails.
        GlobalLicenseCategoryDTO globalLicenseCategoryDTO = globalLicenseCategoryMapper.toDto(globalLicenseCategory);

        restGlobalLicenseCategoryMockMvc.perform(post(SDM_GLOBAL_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalLicenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<GlobalLicenseCategory> globalLicenseCategoryList = globalLicenseCategoryRepository.findAll();
        assertThat(globalLicenseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlobalLicenseCategories() throws Exception {
        // Initialize the database
        globalLicenseCategoryRepository.saveAndFlush(globalLicenseCategory);

        // Get all the globalLicenseCategoryList
        restGlobalLicenseCategoryMockMvc.perform(get("/api/sdm/global-license-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalLicenseCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGlobalLicenseCategory() throws Exception {
        // Initialize the database
        globalLicenseCategoryRepository.saveAndFlush(globalLicenseCategory);

        // Get the globalLicenseCategory
        restGlobalLicenseCategoryMockMvc.perform(get(SDM_GLOBAL_LICENSE_CATEGORIES_BY_ID_API, globalLicenseCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(globalLicenseCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGlobalLicenseCategory() throws Exception {
        // Get the globalLicenseCategory
        restGlobalLicenseCategoryMockMvc.perform(get(SDM_GLOBAL_LICENSE_CATEGORIES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlobalLicenseCategory() throws Exception {
        // Initialize the database
        globalLicenseCategoryRepository.saveAndFlush(globalLicenseCategory);

        int databaseSizeBeforeUpdate = globalLicenseCategoryRepository.findAll().size();

        // Update the globalLicenseCategory
        Optional<GlobalLicenseCategory> optionalGlobalLicenseCategory = globalLicenseCategoryRepository.findById(globalLicenseCategory.getId());
        if (! optionalGlobalLicenseCategory.isPresent()) {
            return;
        }

        GlobalLicenseCategory updatedGlobalLicenseCategory = optionalGlobalLicenseCategory.get();
        // Disconnect from session so that the updates on updatedGlobalLicenseCategory are not directly saved in db
        em.detach(updatedGlobalLicenseCategory);
        updatedGlobalLicenseCategory
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        GlobalLicenseCategoryDTO globalLicenseCategoryDTO = globalLicenseCategoryMapper.toDto(updatedGlobalLicenseCategory);

        restGlobalLicenseCategoryMockMvc.perform(put(SDM_GLOBAL_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalLicenseCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the GlobalLicenseCategory in the database
        List<GlobalLicenseCategory> globalLicenseCategoryList = globalLicenseCategoryRepository.findAll();
        assertThat(globalLicenseCategoryList).hasSize(databaseSizeBeforeUpdate);
        GlobalLicenseCategory testGlobalLicenseCategory = globalLicenseCategoryList.get(globalLicenseCategoryList.size() - 1);
        assertThat(testGlobalLicenseCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGlobalLicenseCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGlobalLicenseCategory() throws Exception {
        int databaseSizeBeforeUpdate = globalLicenseCategoryRepository.findAll().size();

        // Create the GlobalLicenseCategory
        GlobalLicenseCategoryDTO globalLicenseCategoryDTO = globalLicenseCategoryMapper.toDto(globalLicenseCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlobalLicenseCategoryMockMvc.perform(put(SDM_GLOBAL_LICENSE_CATEGORIES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalLicenseCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalLicenseCategory in the database
        List<GlobalLicenseCategory> globalLicenseCategoryList = globalLicenseCategoryRepository.findAll();
        assertThat(globalLicenseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGlobalLicenseCategory() throws Exception {
        // Initialize the database
        globalLicenseCategoryRepository.saveAndFlush(globalLicenseCategory);

        int databaseSizeBeforeDelete = globalLicenseCategoryRepository.findAll().size();

        // Get the globalLicenseCategory
        restGlobalLicenseCategoryMockMvc.perform(delete(SDM_GLOBAL_LICENSE_CATEGORIES_BY_ID_API, globalLicenseCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GlobalLicenseCategory> globalLicenseCategoryList = globalLicenseCategoryRepository.findAll();
        assertThat(globalLicenseCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlobalLicenseCategory.class);
        GlobalLicenseCategory globalLicenseCategory1 = new GlobalLicenseCategory();
        globalLicenseCategory1.setId(1L);
        GlobalLicenseCategory globalLicenseCategory2 = new GlobalLicenseCategory();
        globalLicenseCategory2.setId(globalLicenseCategory1.getId());
        assertThat(globalLicenseCategory1).isEqualTo(globalLicenseCategory2);
        globalLicenseCategory2.setId(2L);
        assertThat(globalLicenseCategory1).isNotEqualTo(globalLicenseCategory2);
        globalLicenseCategory1.setId(null);
        assertThat(globalLicenseCategory1).isNotEqualTo(globalLicenseCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlobalLicenseCategoryDTO.class);
        GlobalLicenseCategoryDTO globalLicenseCategoryDTO1 = new GlobalLicenseCategoryDTO();
        globalLicenseCategoryDTO1.setId(1L);
        GlobalLicenseCategoryDTO globalLicenseCategoryDTO2 = new GlobalLicenseCategoryDTO();
        assertThat(globalLicenseCategoryDTO1).isNotEqualTo(globalLicenseCategoryDTO2);
        globalLicenseCategoryDTO2.setId(globalLicenseCategoryDTO1.getId());
        assertThat(globalLicenseCategoryDTO1).isEqualTo(globalLicenseCategoryDTO2);
        globalLicenseCategoryDTO2.setId(2L);
        assertThat(globalLicenseCategoryDTO1).isNotEqualTo(globalLicenseCategoryDTO2);
        globalLicenseCategoryDTO1.setId(null);
        assertThat(globalLicenseCategoryDTO1).isNotEqualTo(globalLicenseCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(globalLicenseCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(globalLicenseCategoryMapper.fromId(null)).isNull();
    }
}
