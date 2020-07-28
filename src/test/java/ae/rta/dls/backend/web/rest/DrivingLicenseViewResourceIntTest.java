package ae.rta.dls.backend.web.rest;

import ae.rta.dls.backend.DlsBackendApp;
import ae.rta.dls.backend.domain.trf.DrivingLicenseView;
import ae.rta.dls.backend.repository.trf.DrivingLicenseViewRepository;
import ae.rta.dls.backend.service.trf.DrivingLicenseViewService;
import ae.rta.dls.backend.service.dto.trf.DrivingLicenseViewDTO;
import ae.rta.dls.backend.service.mapper.trf.DrivingLicenseViewMapper;
import ae.rta.dls.backend.web.rest.trf.DrivingLicenseViewResource;
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
 * Test class for the DrivingLicenseViewResource REST controller.
 *
 * @see DrivingLicenseViewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class DrivingLicenseViewResourceIntTest {

    private static final String DEFAULT_EMIRATES_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMIRATES_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private DrivingLicenseViewRepository drivingLicenseViewRepository;

    @Autowired
    private DrivingLicenseViewMapper drivingLicenseViewMapper;

    @Autowired
    private DrivingLicenseViewService drivingLicenseViewService;

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

    private MockMvc restDrivingLicenseViewMockMvc;

    private DrivingLicenseView drivingLicenseView;

    private static final String DRIVING_LICENSE_VIEWS_URL_TEMPLATE = "/api/trf/driving-license-views";

    private static final String DRIVING_LICENSE_VIEWS_WITH_ID_URL_TEMPLATE = DRIVING_LICENSE_VIEWS_URL_TEMPLATE + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrivingLicenseViewResource drivingLicenseViewResource = new DrivingLicenseViewResource(drivingLicenseViewService);
        this.restDrivingLicenseViewMockMvc = MockMvcBuilders.standaloneSetup(drivingLicenseViewResource)
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
    public static DrivingLicenseView createEntity(EntityManager em) {
        return new DrivingLicenseView()
            .emiratesId(DEFAULT_EMIRATES_ID)
            .status(DEFAULT_STATUS);
    }

    @Before
    public void initTest() {
        drivingLicenseView = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrivingLicenseView() throws Exception {
        int databaseSizeBeforeCreate = drivingLicenseViewRepository.findAll().size();

        // Create the DrivingLicenseView
        DrivingLicenseViewDTO drivingLicenseViewDTO = drivingLicenseViewMapper.toDto(drivingLicenseView);
        restDrivingLicenseViewMockMvc.perform(post(DRIVING_LICENSE_VIEWS_URL_TEMPLATE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseViewDTO)))
            .andExpect(status().isCreated());

        // Validate the DrivingLicenseView in the database
        List<DrivingLicenseView> drivingLicenseViewList = drivingLicenseViewRepository.findAll();
        assertThat(drivingLicenseViewList).hasSize(databaseSizeBeforeCreate + 1);
        DrivingLicenseView testDrivingLicenseView = drivingLicenseViewList.get(drivingLicenseViewList.size() - 1);
        assertThat(testDrivingLicenseView.getEmiratesId()).isEqualTo(DEFAULT_EMIRATES_ID);
        assertThat(testDrivingLicenseView.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDrivingLicenseViewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drivingLicenseViewRepository.findAll().size();

        // Create the DrivingLicenseView with an existing ID
        drivingLicenseView.setId(1L);
        DrivingLicenseViewDTO drivingLicenseViewDTO = drivingLicenseViewMapper.toDto(drivingLicenseView);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrivingLicenseViewMockMvc.perform(post(DRIVING_LICENSE_VIEWS_URL_TEMPLATE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseViewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DrivingLicenseView in the database
        List<DrivingLicenseView> drivingLicenseViewList = drivingLicenseViewRepository.findAll();
        assertThat(drivingLicenseViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDrivingLicenseViews() throws Exception {
        // Initialize the database
        drivingLicenseViewRepository.saveAndFlush(drivingLicenseView);

        // Get all the drivingLicenseViewList
        restDrivingLicenseViewMockMvc.perform(get("/api/driving-license-views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drivingLicenseView.getId().intValue())))
            .andExpect(jsonPath("$.[*].emiratesId").value(hasItem(DEFAULT_EMIRATES_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getDrivingLicenseView() throws Exception {
        // Initialize the database
        drivingLicenseViewRepository.saveAndFlush(drivingLicenseView);

        // Get the drivingLicenseView
        restDrivingLicenseViewMockMvc.perform(get(DRIVING_LICENSE_VIEWS_WITH_ID_URL_TEMPLATE, drivingLicenseView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drivingLicenseView.getId().intValue()))
            .andExpect(jsonPath("$.emiratesId").value(DEFAULT_EMIRATES_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingDrivingLicenseView() throws Exception {
        // Get the drivingLicenseView
        restDrivingLicenseViewMockMvc.perform(get(DRIVING_LICENSE_VIEWS_WITH_ID_URL_TEMPLATE, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrivingLicenseView() throws Exception {
        // Initialize the database
        drivingLicenseViewRepository.saveAndFlush(drivingLicenseView);

        int databaseSizeBeforeUpdate = drivingLicenseViewRepository.findAll().size();

        // Update the drivingLicenseView
        Optional<DrivingLicenseView> optionalDrivingLicenseView = drivingLicenseViewRepository.findById(drivingLicenseView.getId());
        if (! optionalDrivingLicenseView.isPresent()) {
            return;
        }

        DrivingLicenseView updatedDrivingLicenseView = optionalDrivingLicenseView.get();
        // Disconnect from session so that the updates on updatedDrivingLicenseView are not directly saved in db
        em.detach(updatedDrivingLicenseView);
        updatedDrivingLicenseView
            .emiratesId(UPDATED_EMIRATES_ID)
            .status(UPDATED_STATUS);
        DrivingLicenseViewDTO drivingLicenseViewDTO = drivingLicenseViewMapper.toDto(updatedDrivingLicenseView);

        restDrivingLicenseViewMockMvc.perform(put(DRIVING_LICENSE_VIEWS_URL_TEMPLATE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseViewDTO)))
            .andExpect(status().isOk());

        // Validate the DrivingLicenseView in the database
        List<DrivingLicenseView> drivingLicenseViewList = drivingLicenseViewRepository.findAll();
        assertThat(drivingLicenseViewList).hasSize(databaseSizeBeforeUpdate);
        DrivingLicenseView testDrivingLicenseView = drivingLicenseViewList.get(drivingLicenseViewList.size() - 1);
        assertThat(testDrivingLicenseView.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testDrivingLicenseView.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDrivingLicenseView() throws Exception {
        int databaseSizeBeforeUpdate = drivingLicenseViewRepository.findAll().size();

        // Create the DrivingLicenseView
        DrivingLicenseViewDTO drivingLicenseViewDTO = drivingLicenseViewMapper.toDto(drivingLicenseView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrivingLicenseViewMockMvc.perform(put(DRIVING_LICENSE_VIEWS_URL_TEMPLATE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drivingLicenseViewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DrivingLicenseView in the database
        List<DrivingLicenseView> drivingLicenseViewList = drivingLicenseViewRepository.findAll();
        assertThat(drivingLicenseViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrivingLicenseView() throws Exception {
        // Initialize the database
        drivingLicenseViewRepository.saveAndFlush(drivingLicenseView);

        int databaseSizeBeforeDelete = drivingLicenseViewRepository.findAll().size();

        // Delete the drivingLicenseView
        restDrivingLicenseViewMockMvc.perform(delete(DRIVING_LICENSE_VIEWS_WITH_ID_URL_TEMPLATE, drivingLicenseView.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DrivingLicenseView> drivingLicenseViewList = drivingLicenseViewRepository.findAll();
        assertThat(drivingLicenseViewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrivingLicenseView.class);
        DrivingLicenseView drivingLicenseView1 = new DrivingLicenseView();
        drivingLicenseView1.setId(1L);
        DrivingLicenseView drivingLicenseView2 = new DrivingLicenseView();
        drivingLicenseView2.setId(drivingLicenseView1.getId());
        assertThat(drivingLicenseView1).isEqualTo(drivingLicenseView2);
        drivingLicenseView2.setId(2L);
        assertThat(drivingLicenseView1).isNotEqualTo(drivingLicenseView2);
        drivingLicenseView1.setId(null);
        assertThat(drivingLicenseView1).isNotEqualTo(drivingLicenseView2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrivingLicenseViewDTO.class);
        DrivingLicenseViewDTO drivingLicenseViewDTO1 = new DrivingLicenseViewDTO();
        drivingLicenseViewDTO1.setId(1L);
        DrivingLicenseViewDTO drivingLicenseViewDTO2 = new DrivingLicenseViewDTO();
        assertThat(drivingLicenseViewDTO1).isNotEqualTo(drivingLicenseViewDTO2);
        drivingLicenseViewDTO2.setId(drivingLicenseViewDTO1.getId());
        assertThat(drivingLicenseViewDTO1).isEqualTo(drivingLicenseViewDTO2);
        drivingLicenseViewDTO2.setId(2L);
        assertThat(drivingLicenseViewDTO1).isNotEqualTo(drivingLicenseViewDTO2);
        drivingLicenseViewDTO1.setId(null);
        assertThat(drivingLicenseViewDTO1).isNotEqualTo(drivingLicenseViewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(drivingLicenseViewMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(drivingLicenseViewMapper.fromId(null)).isNull();
    }
}
