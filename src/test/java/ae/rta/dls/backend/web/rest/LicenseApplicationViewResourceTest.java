package ae.rta.dls.backend.web.rest;

import ae.rta.dls.backend.DlsBackendApp;
import ae.rta.dls.backend.domain.trf.LicenseApplicationView;
import ae.rta.dls.backend.repository.trf.LicenseApplicationViewRepository;
import ae.rta.dls.backend.service.trf.LicenseApplicationViewService;
import ae.rta.dls.backend.service.dto.trf.LicenseApplicationViewDTO;
import ae.rta.dls.backend.service.mapper.trf.LicenseApplicationViewMapper;
import ae.rta.dls.backend.web.rest.util.ExceptionTranslator;

import ae.rta.dls.backend.web.rest.trf.LicenseApplicationViewResource;
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

import static ae.rta.dls.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * test for the {@link LicenseApplicationViewResource} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class LicenseApplicationViewResourceTest {

    private static final String DEFAULT_EMIRATES_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMIRATES_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_VCL_ID = 1;
    private static final Integer UPDATED_VCL_ID = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private LicenseApplicationViewRepository licenseApplicationViewRepository;

    @Autowired
    private LicenseApplicationViewMapper licenseApplicationViewMapper;

    @Autowired
    private LicenseApplicationViewService licenseApplicationViewService;

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

    private MockMvc restLicenseApplicationViewMockMvc;

    private LicenseApplicationView licenseApplicationView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LicenseApplicationViewResource licenseApplicationViewResource = new LicenseApplicationViewResource(licenseApplicationViewService);
        this.restLicenseApplicationViewMockMvc = MockMvcBuilders.standaloneSetup(licenseApplicationViewResource)
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
    public static LicenseApplicationView createEntity(EntityManager em) {
        LicenseApplicationView licenseApplicationView = new LicenseApplicationView()
            .emiratesId(DEFAULT_EMIRATES_ID)
            .vclId(DEFAULT_VCL_ID)
            .status(DEFAULT_STATUS);
        return licenseApplicationView;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenseApplicationView createUpdatedEntity(EntityManager em) {
        LicenseApplicationView licenseApplicationView = new LicenseApplicationView()
            .emiratesId(UPDATED_EMIRATES_ID)
            .vclId(UPDATED_VCL_ID)
            .status(UPDATED_STATUS);
        return licenseApplicationView;
    }

    @Before
    public void initTest() {
        licenseApplicationView = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicenseApplicationView() throws Exception {
        int databaseSizeBeforeCreate = licenseApplicationViewRepository.findAll().size();

        // Create the LicenseApplicationView
        LicenseApplicationViewDTO licenseApplicationViewDTO = licenseApplicationViewMapper.toDto(licenseApplicationView);
        restLicenseApplicationViewMockMvc.perform(post("/api/license-application-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseApplicationViewDTO)))
            .andExpect(status().isCreated());

        // Validate the LicenseApplicationView in the database
        List<LicenseApplicationView> licenseApplicationViewList = licenseApplicationViewRepository.findAll();
        assertThat(licenseApplicationViewList).hasSize(databaseSizeBeforeCreate + 1);
        LicenseApplicationView testLicenseApplicationView = licenseApplicationViewList.get(licenseApplicationViewList.size() - 1);
        assertThat(testLicenseApplicationView.getEmiratesId()).isEqualTo(DEFAULT_EMIRATES_ID);
        assertThat(testLicenseApplicationView.getVclId()).isEqualTo(DEFAULT_VCL_ID);
        assertThat(testLicenseApplicationView.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createLicenseApplicationViewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenseApplicationViewRepository.findAll().size();

        // Create the LicenseApplicationView with an existing ID
        licenseApplicationView.setId(1L);
        LicenseApplicationViewDTO licenseApplicationViewDTO = licenseApplicationViewMapper.toDto(licenseApplicationView);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenseApplicationViewMockMvc.perform(post("/api/license-application-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseApplicationViewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LicenseApplicationView in the database
        List<LicenseApplicationView> licenseApplicationViewList = licenseApplicationViewRepository.findAll();
        assertThat(licenseApplicationViewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLicenseApplicationViews() throws Exception {
        // Initialize the database
        licenseApplicationViewRepository.saveAndFlush(licenseApplicationView);

        // Get all the licenseApplicationViewList
        restLicenseApplicationViewMockMvc.perform(get("/api/license-application-views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenseApplicationView.getId().intValue())))
            .andExpect(jsonPath("$.[*].emiratesId").value(hasItem(DEFAULT_EMIRATES_ID)))
            .andExpect(jsonPath("$.[*].vclId").value(hasItem(DEFAULT_VCL_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getLicenseApplicationView() throws Exception {
        // Initialize the database
        licenseApplicationViewRepository.saveAndFlush(licenseApplicationView);

        // Get the licenseApplicationView
        restLicenseApplicationViewMockMvc.perform(get("/api/license-application-views/{id}", licenseApplicationView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(licenseApplicationView.getId().intValue()))
            .andExpect(jsonPath("$.emiratesId").value(DEFAULT_EMIRATES_ID))
            .andExpect(jsonPath("$.vclId").value(DEFAULT_VCL_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingLicenseApplicationView() throws Exception {
        // Get the licenseApplicationView
        restLicenseApplicationViewMockMvc.perform(get("/api/license-application-views/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicenseApplicationView() throws Exception {
        // Initialize the database
        licenseApplicationViewRepository.saveAndFlush(licenseApplicationView);

        int databaseSizeBeforeUpdate = licenseApplicationViewRepository.findAll().size();

        // Update the licenseApplicationView
        LicenseApplicationView updatedLicenseApplicationView = licenseApplicationViewRepository.findById(licenseApplicationView.getId()).get();
        // Disconnect from session so that the updates on updatedLicenseApplicationView are not directly saved in db
        em.detach(updatedLicenseApplicationView);
        updatedLicenseApplicationView
            .emiratesId(UPDATED_EMIRATES_ID)
            .vclId(UPDATED_VCL_ID)
            .status(UPDATED_STATUS);
        LicenseApplicationViewDTO licenseApplicationViewDTO = licenseApplicationViewMapper.toDto(updatedLicenseApplicationView);

        restLicenseApplicationViewMockMvc.perform(put("/api/license-application-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseApplicationViewDTO)))
            .andExpect(status().isOk());

        // Validate the LicenseApplicationView in the database
        List<LicenseApplicationView> licenseApplicationViewList = licenseApplicationViewRepository.findAll();
        assertThat(licenseApplicationViewList).hasSize(databaseSizeBeforeUpdate);
        LicenseApplicationView testLicenseApplicationView = licenseApplicationViewList.get(licenseApplicationViewList.size() - 1);
        assertThat(testLicenseApplicationView.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testLicenseApplicationView.getVclId()).isEqualTo(UPDATED_VCL_ID);
        assertThat(testLicenseApplicationView.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingLicenseApplicationView() throws Exception {
        int databaseSizeBeforeUpdate = licenseApplicationViewRepository.findAll().size();

        // Create the LicenseApplicationView
        LicenseApplicationViewDTO licenseApplicationViewDTO = licenseApplicationViewMapper.toDto(licenseApplicationView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseApplicationViewMockMvc.perform(put("/api/license-application-views")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licenseApplicationViewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LicenseApplicationView in the database
        List<LicenseApplicationView> licenseApplicationViewList = licenseApplicationViewRepository.findAll();
        assertThat(licenseApplicationViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLicenseApplicationView() throws Exception {
        // Initialize the database
        licenseApplicationViewRepository.saveAndFlush(licenseApplicationView);

        int databaseSizeBeforeDelete = licenseApplicationViewRepository.findAll().size();

        // Delete the licenseApplicationView
        restLicenseApplicationViewMockMvc.perform(delete("/api/license-application-views/{id}", licenseApplicationView.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LicenseApplicationView> licenseApplicationViewList = licenseApplicationViewRepository.findAll();
        assertThat(licenseApplicationViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
