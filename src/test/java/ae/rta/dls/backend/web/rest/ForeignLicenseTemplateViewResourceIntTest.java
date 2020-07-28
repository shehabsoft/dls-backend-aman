package ae.rta.dls.backend.web.rest;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.trf.ForeignLicenseTemplateView;
import ae.rta.dls.backend.repository.trf.ForeignLicenseTemplateViewRepository;
import ae.rta.dls.backend.service.trf.ForeignLicenseTemplateViewService;
import ae.rta.dls.backend.service.dto.trf.ForeignLicenseTemplateViewDTO;
import ae.rta.dls.backend.service.mapper.trf.ForeignLicenseTemplateViewMapper;
import ae.rta.dls.backend.web.rest.trf.ForeignLicenseTemplateViewResource;
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
 * Test class for the HeldLicenseTemplateViewResource REST controller.
 *
 * @see ForeignLicenseTemplateViewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ForeignLicenseTemplateViewResourceIntTest {

    private static final String DEFAULT_ARABIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ARABIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BACK_SIDE_TEMPLATE = "";
    private static final String UPDATED_BACK_SIDE_TEMPLATE = "";
    private static final String DEFAULT_BACK_SIDE_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BACK_SIDE_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FRONT_SIDE_TEMPLATE = "";
    private static final String UPDATED_FRONT_SIDE_TEMPLATE = "";
    private static final String DEFAULT_FRONT_SIDE_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FRONT_SIDE_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_COUNTRY_ID = 1L;
    private static final Long UPDATED_COUNTRY_ID = 2L;

    private static final Long DEFAULT_STATE_ID = 1L;
    private static final Long UPDATED_STATE_ID = 2L;

    @Autowired
    private ForeignLicenseTemplateViewRepository foreignLicenseTemplateViewRepository;

    @Autowired
    private ForeignLicenseTemplateViewMapper foreignLicenseTemplateViewMapper;

    @Autowired
    private ForeignLicenseTemplateViewService foreignLicenseTemplateViewService;

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

    private MockMvc restHeldLicenseTemplateViewMockMvc;

    private ForeignLicenseTemplateView foreignLicenseTemplateView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ForeignLicenseTemplateViewResource foreignLicenseTemplateViewResource = new ForeignLicenseTemplateViewResource(foreignLicenseTemplateViewService);
        this.restHeldLicenseTemplateViewMockMvc = MockMvcBuilders.standaloneSetup(foreignLicenseTemplateViewResource)
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
    public static ForeignLicenseTemplateView createEntity(EntityManager em) {
        ForeignLicenseTemplateView foreignLicenseTemplateView = new ForeignLicenseTemplateView()
            .arabicName(DEFAULT_ARABIC_NAME)
            .englishName(DEFAULT_ENGLISH_NAME)
            .backSideTemplate(DEFAULT_BACK_SIDE_TEMPLATE)
            .frontSideTemplate(DEFAULT_FRONT_SIDE_TEMPLATE)
            .countryId(DEFAULT_COUNTRY_ID)
            .stateId(DEFAULT_STATE_ID);
        return foreignLicenseTemplateView;
    }

    @Before
    public void initTest() {
        foreignLicenseTemplateView = createEntity(em);
    }

    @Test
    @Transactional
    public void createHeldLicenseTemplateView() throws Exception {
        int databaseSizeBeforeCreate = foreignLicenseTemplateViewRepository.findAll().size();

        // Create the HeldLicenseTemplateView
        ForeignLicenseTemplateViewDTO foreignLicenseTemplateViewDTO = foreignLicenseTemplateViewMapper.toDto(foreignLicenseTemplateView);
        restHeldLicenseTemplateViewMockMvc.perform(post("/api/held-license-template-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foreignLicenseTemplateViewDTO)))
            .andExpect(status().isCreated());

        // Validate the HeldLicenseTemplateView in the database
        List<ForeignLicenseTemplateView> foreignLicenseTemplateViewList = foreignLicenseTemplateViewRepository.findAll();
        assertThat(foreignLicenseTemplateViewList).hasSize(databaseSizeBeforeCreate + 1);
        ForeignLicenseTemplateView testForeignLicenseTemplateView = foreignLicenseTemplateViewList.get(foreignLicenseTemplateViewList.size() - 1);
        assertThat(testForeignLicenseTemplateView.getArabicName()).isEqualTo(DEFAULT_ARABIC_NAME);
        assertThat(testForeignLicenseTemplateView.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
        assertThat(testForeignLicenseTemplateView.getBackSideTemplate()).isEqualTo(DEFAULT_BACK_SIDE_TEMPLATE);
        assertThat(testForeignLicenseTemplateView.getFrontSideTemplate()).isEqualTo(DEFAULT_FRONT_SIDE_TEMPLATE);
        assertThat(testForeignLicenseTemplateView.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testForeignLicenseTemplateView.getStateId()).isEqualTo(DEFAULT_STATE_ID);
    }

    @Test
    @Transactional
    public void createHeldLicenseTemplateViewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foreignLicenseTemplateViewRepository.findAll().size();

        // Create the HeldLicenseTemplateView with an existing ID
        foreignLicenseTemplateView.setId(1L);
        ForeignLicenseTemplateViewDTO foreignLicenseTemplateViewDTO = foreignLicenseTemplateViewMapper.toDto(foreignLicenseTemplateView);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeldLicenseTemplateViewMockMvc.perform(post("/api/held-license-template-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foreignLicenseTemplateViewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HeldLicenseTemplateView in the database
        List<ForeignLicenseTemplateView> foreignLicenseTemplateViewList = foreignLicenseTemplateViewRepository.findAll();
        assertThat(foreignLicenseTemplateViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHeldLicenseTemplateViews() throws Exception {
        // Initialize the database
        foreignLicenseTemplateViewRepository.saveAndFlush(foreignLicenseTemplateView);

        // Get all the heldLicenseTemplateViewList
        restHeldLicenseTemplateViewMockMvc.perform(get("/api/held-license-template-views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foreignLicenseTemplateView.getId().intValue())))
            .andExpect(jsonPath("$.[*].arabicName").value(hasItem(DEFAULT_ARABIC_NAME.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())))
            .andExpect(jsonPath("$.[*].backSideTemplateContentType").value(hasItem(DEFAULT_BACK_SIDE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].backSideTemplate").value(hasItem(DEFAULT_BACK_SIDE_TEMPLATE)))
            .andExpect(jsonPath("$.[*].frontSideTemplateContentType").value(hasItem(DEFAULT_FRONT_SIDE_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].frontSideTemplate").value(hasItem(DEFAULT_FRONT_SIDE_TEMPLATE)))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].stateId").value(hasItem(DEFAULT_STATE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getHeldLicenseTemplateView() throws Exception {
        // Initialize the database
        foreignLicenseTemplateViewRepository.saveAndFlush(foreignLicenseTemplateView);

        // Get the heldLicenseTemplateView
        restHeldLicenseTemplateViewMockMvc.perform(get("/api/held-license-template-views/{id}", foreignLicenseTemplateView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foreignLicenseTemplateView.getId().intValue()))
            .andExpect(jsonPath("$.arabicName").value(DEFAULT_ARABIC_NAME.toString()))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()))
            .andExpect(jsonPath("$.backSideTemplateContentType").value(DEFAULT_BACK_SIDE_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.backSideTemplate").value(DEFAULT_BACK_SIDE_TEMPLATE))
            .andExpect(jsonPath("$.frontSideTemplateContentType").value(DEFAULT_FRONT_SIDE_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.frontSideTemplate").value(DEFAULT_FRONT_SIDE_TEMPLATE))
            .andExpect(jsonPath("$.countryId").value(DEFAULT_COUNTRY_ID.intValue()))
            .andExpect(jsonPath("$.stateId").value(DEFAULT_STATE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHeldLicenseTemplateView() throws Exception {
        // Get the heldLicenseTemplateView
        restHeldLicenseTemplateViewMockMvc.perform(get("/api/held-license-template-views/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeldLicenseTemplateView() throws Exception {
        // Initialize the database
        foreignLicenseTemplateViewRepository.saveAndFlush(foreignLicenseTemplateView);

        int databaseSizeBeforeUpdate = foreignLicenseTemplateViewRepository.findAll().size();

        // Update the heldLicenseTemplateView
        Optional<ForeignLicenseTemplateView> optionalHeldLicenseTemplateView = foreignLicenseTemplateViewRepository.findById(foreignLicenseTemplateView.getId());
        if (! optionalHeldLicenseTemplateView.isPresent()) {
            return;
        }

        ForeignLicenseTemplateView updatedForeignLicenseTemplateView = optionalHeldLicenseTemplateView.get();
        // Disconnect from session so that the updates on updatedHeldLicenseTemplateView are not directly saved in db
        em.detach(updatedForeignLicenseTemplateView);
        updatedForeignLicenseTemplateView
            .arabicName(UPDATED_ARABIC_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .backSideTemplate(UPDATED_BACK_SIDE_TEMPLATE)
            .frontSideTemplate(UPDATED_FRONT_SIDE_TEMPLATE)
            .countryId(UPDATED_COUNTRY_ID)
            .stateId(UPDATED_STATE_ID);
        ForeignLicenseTemplateViewDTO foreignLicenseTemplateViewDTO = foreignLicenseTemplateViewMapper.toDto(updatedForeignLicenseTemplateView);

        restHeldLicenseTemplateViewMockMvc.perform(put("/api/held-license-template-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foreignLicenseTemplateViewDTO)))
            .andExpect(status().isOk());

        // Validate the HeldLicenseTemplateView in the database
        List<ForeignLicenseTemplateView> foreignLicenseTemplateViewList = foreignLicenseTemplateViewRepository.findAll();
        assertThat(foreignLicenseTemplateViewList).hasSize(databaseSizeBeforeUpdate);
        ForeignLicenseTemplateView testForeignLicenseTemplateView = foreignLicenseTemplateViewList.get(foreignLicenseTemplateViewList.size() - 1);
        assertThat(testForeignLicenseTemplateView.getArabicName()).isEqualTo(UPDATED_ARABIC_NAME);
        assertThat(testForeignLicenseTemplateView.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
        assertThat(testForeignLicenseTemplateView.getBackSideTemplate()).isEqualTo(UPDATED_BACK_SIDE_TEMPLATE);
        assertThat(testForeignLicenseTemplateView.getFrontSideTemplate()).isEqualTo(UPDATED_FRONT_SIDE_TEMPLATE);
        assertThat(testForeignLicenseTemplateView.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testForeignLicenseTemplateView.getStateId()).isEqualTo(UPDATED_STATE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingHeldLicenseTemplateView() throws Exception {
        int databaseSizeBeforeUpdate = foreignLicenseTemplateViewRepository.findAll().size();

        // Create the HeldLicenseTemplateView
        ForeignLicenseTemplateViewDTO foreignLicenseTemplateViewDTO = foreignLicenseTemplateViewMapper.toDto(foreignLicenseTemplateView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeldLicenseTemplateViewMockMvc.perform(put("/api/held-license-template-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foreignLicenseTemplateViewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HeldLicenseTemplateView in the database
        List<ForeignLicenseTemplateView> foreignLicenseTemplateViewList = foreignLicenseTemplateViewRepository.findAll();
        assertThat(foreignLicenseTemplateViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHeldLicenseTemplateView() throws Exception {
        // Initialize the database
        foreignLicenseTemplateViewRepository.saveAndFlush(foreignLicenseTemplateView);

        int databaseSizeBeforeDelete = foreignLicenseTemplateViewRepository.findAll().size();

        // Delete the heldLicenseTemplateView
        restHeldLicenseTemplateViewMockMvc.perform(delete("/api/held-license-template-views/{id}", foreignLicenseTemplateView.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ForeignLicenseTemplateView> foreignLicenseTemplateViewList = foreignLicenseTemplateViewRepository.findAll();
        assertThat(foreignLicenseTemplateViewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForeignLicenseTemplateView.class);
        ForeignLicenseTemplateView foreignLicenseTemplateView1 = new ForeignLicenseTemplateView();
        foreignLicenseTemplateView1.setId(1L);
        ForeignLicenseTemplateView foreignLicenseTemplateView2 = new ForeignLicenseTemplateView();
        foreignLicenseTemplateView2.setId(foreignLicenseTemplateView1.getId());
        assertThat(foreignLicenseTemplateView1).isEqualTo(foreignLicenseTemplateView2);
        foreignLicenseTemplateView2.setId(2L);
        assertThat(foreignLicenseTemplateView1).isNotEqualTo(foreignLicenseTemplateView2);
        foreignLicenseTemplateView1.setId(null);
        assertThat(foreignLicenseTemplateView1).isNotEqualTo(foreignLicenseTemplateView2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForeignLicenseTemplateViewDTO.class);
        ForeignLicenseTemplateViewDTO foreignLicenseTemplateViewDTO1 = new ForeignLicenseTemplateViewDTO();
        foreignLicenseTemplateViewDTO1.setId(1L);
        ForeignLicenseTemplateViewDTO foreignLicenseTemplateViewDTO2 = new ForeignLicenseTemplateViewDTO();
        assertThat(foreignLicenseTemplateViewDTO1).isNotEqualTo(foreignLicenseTemplateViewDTO2);
        foreignLicenseTemplateViewDTO2.setId(foreignLicenseTemplateViewDTO1.getId());
        assertThat(foreignLicenseTemplateViewDTO1).isEqualTo(foreignLicenseTemplateViewDTO2);
        foreignLicenseTemplateViewDTO2.setId(2L);
        assertThat(foreignLicenseTemplateViewDTO1).isNotEqualTo(foreignLicenseTemplateViewDTO2);
        foreignLicenseTemplateViewDTO1.setId(null);
        assertThat(foreignLicenseTemplateViewDTO1).isNotEqualTo(foreignLicenseTemplateViewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(foreignLicenseTemplateViewMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(foreignLicenseTemplateViewMapper.fromId(null)).isNull();
    }
}
