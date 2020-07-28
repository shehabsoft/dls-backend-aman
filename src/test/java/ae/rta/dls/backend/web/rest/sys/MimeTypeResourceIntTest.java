package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.sys.MimeType;
import ae.rta.dls.backend.repository.sys.MimeTypeRepository;
import ae.rta.dls.backend.service.sys.MimeTypeService;
import ae.rta.dls.backend.service.dto.sys.MimeTypeDTO;
import ae.rta.dls.backend.service.mapper.sys.MimeTypeMapper;
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
 * Test class for the MimeTypeResource REST controller.
 *
 * @see MimeTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class MimeTypeResourceIntTest {

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAXIMUM_SIZE = 1;
    private static final Integer UPDATED_MAXIMUM_SIZE = 2;

    @Autowired
    private MimeTypeRepository mimeTypeRepository;

    @Autowired
    private MimeTypeMapper mimeTypeMapper;

    @Autowired
    private MimeTypeService mimeTypeService;

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

    private MockMvc restMimeTypeMockMvc;

    private MimeType mimeType;

    private static final String SYS_MIME_TYPES_API = "/api/sys/mime-types";

    private static final String SYS_MIME_TYPES_BY_ID_API = SYS_MIME_TYPES_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MimeTypeResource mimeTypeResource = new MimeTypeResource(mimeTypeService);
        this.restMimeTypeMockMvc = MockMvcBuilders.standaloneSetup(mimeTypeResource)
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
    public static MimeType createEntity(EntityManager em) {
        return new MimeType()
            .extension(DEFAULT_EXTENSION)
            .contentType(DEFAULT_CONTENT_TYPE)
            .maximumSize(DEFAULT_MAXIMUM_SIZE);
    }

    @Before
    public void initTest() {
        mimeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMimeType() throws Exception {
        int databaseSizeBeforeCreate = mimeTypeRepository.findAll().size();

        // Create the MimeType
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);
        restMimeTypeMockMvc.perform(post(SYS_MIME_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MimeType testMimeType = mimeTypeList.get(mimeTypeList.size() - 1);
        assertThat(testMimeType.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testMimeType.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testMimeType.getMaximumSize()).isEqualTo(DEFAULT_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void createMimeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mimeTypeRepository.findAll().size();

        // Create the MimeType with an existing ID
        mimeType.setId(1L);
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMimeTypeMockMvc.perform(post(SYS_MIME_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkExtensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mimeTypeRepository.findAll().size();
        // set the field null
        mimeType.setExtension(null);

        // Create the MimeType, which fails.
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        restMimeTypeMockMvc.perform(post(SYS_MIME_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mimeTypeRepository.findAll().size();
        // set the field null
        mimeType.setContentType(null);

        // Create the MimeType, which fails.
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        restMimeTypeMockMvc.perform(post(SYS_MIME_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaximumSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mimeTypeRepository.findAll().size();
        // set the field null
        mimeType.setMaximumSize(null);

        // Create the MimeType, which fails.
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        restMimeTypeMockMvc.perform(post(SYS_MIME_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMimeTypes() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList
        restMimeTypeMockMvc.perform(get("/api/mime-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mimeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].maximumSize").value(hasItem(DEFAULT_MAXIMUM_SIZE)));
    }

    @Test
    @Transactional
    public void getMimeType() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get the mimeType
        restMimeTypeMockMvc.perform(get(SYS_MIME_TYPES_BY_ID_API, mimeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mimeType.getId().intValue()))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.maximumSize").value(DEFAULT_MAXIMUM_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingMimeType() throws Exception {
        // Get the mimeType
        restMimeTypeMockMvc.perform(get(SYS_MIME_TYPES_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMimeType() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        int databaseSizeBeforeUpdate = mimeTypeRepository.findAll().size();

        // Update the mimeType

        Optional<MimeType> optionalMimeType = mimeTypeRepository.findById(mimeType.getId());
        if (! optionalMimeType.isPresent()) {
            return;
        }

        MimeType updatedMimeType = optionalMimeType.get();
        // Disconnect from session so that the updates on updatedMimeType are not directly saved in db
        em.detach(updatedMimeType);
        updatedMimeType
            .extension(UPDATED_EXTENSION)
            .contentType(UPDATED_CONTENT_TYPE)
            .maximumSize(UPDATED_MAXIMUM_SIZE);
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(updatedMimeType);

        restMimeTypeMockMvc.perform(put(SYS_MIME_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isOk());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeUpdate);
        MimeType testMimeType = mimeTypeList.get(mimeTypeList.size() - 1);
        assertThat(testMimeType.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testMimeType.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testMimeType.getMaximumSize()).isEqualTo(UPDATED_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingMimeType() throws Exception {
        int databaseSizeBeforeUpdate = mimeTypeRepository.findAll().size();

        // Create the MimeType
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMimeTypeMockMvc.perform(put(SYS_MIME_TYPES_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMimeType() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        int databaseSizeBeforeDelete = mimeTypeRepository.findAll().size();

        // Delete the mimeType
        restMimeTypeMockMvc.perform(delete(SYS_MIME_TYPES_BY_ID_API, mimeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MimeType.class);
        MimeType mimeType1 = new MimeType();
        mimeType1.setId(1L);
        MimeType mimeType2 = new MimeType();
        mimeType2.setId(mimeType1.getId());
        assertThat(mimeType1).isEqualTo(mimeType2);
        mimeType2.setId(2L);
        assertThat(mimeType1).isNotEqualTo(mimeType2);
        mimeType1.setId(null);
        assertThat(mimeType1).isNotEqualTo(mimeType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MimeTypeDTO.class);
        MimeTypeDTO mimeTypeDTO1 = new MimeTypeDTO();
        mimeTypeDTO1.setId(1L);
        MimeTypeDTO mimeTypeDTO2 = new MimeTypeDTO();
        assertThat(mimeTypeDTO1).isNotEqualTo(mimeTypeDTO2);
        mimeTypeDTO2.setId(mimeTypeDTO1.getId());
        assertThat(mimeTypeDTO1).isEqualTo(mimeTypeDTO2);
        mimeTypeDTO2.setId(2L);
        assertThat(mimeTypeDTO1).isNotEqualTo(mimeTypeDTO2);
        mimeTypeDTO1.setId(null);
        assertThat(mimeTypeDTO1).isNotEqualTo(mimeTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mimeTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mimeTypeMapper.fromId(null)).isNull();
    }
}
