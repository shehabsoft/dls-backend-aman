package ae.rta.dls.backend.web.rest;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.prd.BusinessProfile;
import ae.rta.dls.backend.domain.util.type.BusinessProfileProductJsonType;
import ae.rta.dls.backend.repository.prd.BusinessProfileRepository;
import ae.rta.dls.backend.service.prd.BusinessProfileService;
import ae.rta.dls.backend.service.dto.prd.BusinessProfileDTO;
import ae.rta.dls.backend.service.mapper.prd.BusinessProfileMapper;
import ae.rta.dls.backend.web.rest.prd.BusinessProfileResource;
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


import static ae.rta.dls.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessProfileResource REST controller.
 *
 * @see BusinessProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class BusinessProfileResourceIntTest {

    private static final BusinessProfileProductJsonType DEFAULT_PRODUCT_DOCUMENT = new BusinessProfileProductJsonType();
    private static final BusinessProfileProductJsonType UPDATED_PRODUCT_DOCUMENT = new BusinessProfileProductJsonType();

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private BusinessProfileMapper businessProfileMapper;

    @Autowired
    private BusinessProfileService businessProfileService;

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

    private MockMvc restBusinessProfileMockMvc;

    private BusinessProfile businessProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessProfileResource businessProfileResource = new BusinessProfileResource(businessProfileService);
        this.restBusinessProfileMockMvc = MockMvcBuilders.standaloneSetup(businessProfileResource)
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
    public static BusinessProfile createEntity(EntityManager em) {
        BusinessProfile businessProfile = new BusinessProfile()
            .productDocument(DEFAULT_PRODUCT_DOCUMENT)
            .remarks(DEFAULT_REMARKS);
        return businessProfile;
    }

    @Before
    public void initTest() {
        businessProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessProfile() throws Exception {
        int databaseSizeBeforeCreate = businessProfileRepository.findAll().size();

        // Create the BusinessProfile
        BusinessProfileDTO businessProfileDTO = businessProfileMapper.toDto(businessProfile);
        restBusinessProfileMockMvc.perform(post("/api/business-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessProfile in the database
        List<BusinessProfile> businessProfileList = businessProfileRepository.findAll();
        assertThat(businessProfileList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessProfile testBusinessProfile = businessProfileList.get(businessProfileList.size() - 1);
        assertThat(testBusinessProfile.getProductDocument()).isEqualTo(DEFAULT_PRODUCT_DOCUMENT);
        assertThat(testBusinessProfile.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createBusinessProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessProfileRepository.findAll().size();

        // Create the BusinessProfile with an existing ID
        businessProfile.setId(1L);
        BusinessProfileDTO businessProfileDTO = businessProfileMapper.toDto(businessProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessProfileMockMvc.perform(post("/api/business-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessProfile in the database
        List<BusinessProfile> businessProfileList = businessProfileRepository.findAll();
        assertThat(businessProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessProfiles() throws Exception {
        // Initialize the database
        businessProfileRepository.saveAndFlush(businessProfile);

        // Get all the businessProfileList
        restBusinessProfileMockMvc.perform(get("/api/business-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].productDocument").value(hasItem(DEFAULT_PRODUCT_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getBusinessProfile() throws Exception {
        // Initialize the database
        businessProfileRepository.saveAndFlush(businessProfile);

        // Get the businessProfile
        restBusinessProfileMockMvc.perform(get("/api/prd/business-profiles/{id}", businessProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessProfile.getId().intValue()))
            .andExpect(jsonPath("$.productDocument").value(DEFAULT_PRODUCT_DOCUMENT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessProfile() throws Exception {
        // Get the businessProfile
        restBusinessProfileMockMvc.perform(get("/api/prd/business-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessProfile() throws Exception {
        // Initialize the database
        businessProfileRepository.saveAndFlush(businessProfile);

        int databaseSizeBeforeUpdate = businessProfileRepository.findAll().size();

        // Update the businessProfile
        BusinessProfile updatedBusinessProfile = businessProfileRepository.findById(businessProfile.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessProfile are not directly saved in db
        em.detach(updatedBusinessProfile);
        updatedBusinessProfile
            .productDocument(UPDATED_PRODUCT_DOCUMENT)
            .remarks(UPDATED_REMARKS);
        BusinessProfileDTO businessProfileDTO = businessProfileMapper.toDto(updatedBusinessProfile);

        restBusinessProfileMockMvc.perform(put("/api/business-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessProfileDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessProfile in the database
        List<BusinessProfile> businessProfileList = businessProfileRepository.findAll();
        assertThat(businessProfileList).hasSize(databaseSizeBeforeUpdate);
        BusinessProfile testBusinessProfile = businessProfileList.get(businessProfileList.size() - 1);
        assertThat(testBusinessProfile.getProductDocument()).isEqualTo(UPDATED_PRODUCT_DOCUMENT);
        assertThat(testBusinessProfile.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessProfile() throws Exception {
        int databaseSizeBeforeUpdate = businessProfileRepository.findAll().size();

        // Create the BusinessProfile
        BusinessProfileDTO businessProfileDTO = businessProfileMapper.toDto(businessProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessProfileMockMvc.perform(put("/api/business-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessProfile in the database
        List<BusinessProfile> businessProfileList = businessProfileRepository.findAll();
        assertThat(businessProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessProfile() throws Exception {
        // Initialize the database
        businessProfileRepository.saveAndFlush(businessProfile);

        int databaseSizeBeforeDelete = businessProfileRepository.findAll().size();

        // Delete the businessProfile
        restBusinessProfileMockMvc.perform(delete("/api/prd/business-profiles/{id}", businessProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessProfile> businessProfileList = businessProfileRepository.findAll();
        assertThat(businessProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessProfile.class);
        BusinessProfile businessProfile1 = new BusinessProfile();
        businessProfile1.setId(1L);
        BusinessProfile businessProfile2 = new BusinessProfile();
        businessProfile2.setId(businessProfile1.getId());
        assertThat(businessProfile1).isEqualTo(businessProfile2);
        businessProfile2.setId(2L);
        assertThat(businessProfile1).isNotEqualTo(businessProfile2);
        businessProfile1.setId(null);
        assertThat(businessProfile1).isNotEqualTo(businessProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessProfileDTO.class);
        BusinessProfileDTO businessProfileDTO1 = new BusinessProfileDTO();
        businessProfileDTO1.setId(1L);
        BusinessProfileDTO businessProfileDTO2 = new BusinessProfileDTO();
        assertThat(businessProfileDTO1).isNotEqualTo(businessProfileDTO2);
        businessProfileDTO2.setId(businessProfileDTO1.getId());
        assertThat(businessProfileDTO1).isEqualTo(businessProfileDTO2);
        businessProfileDTO2.setId(2L);
        assertThat(businessProfileDTO1).isNotEqualTo(businessProfileDTO2);
        businessProfileDTO1.setId(null);
        assertThat(businessProfileDTO1).isNotEqualTo(businessProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessProfileMapper.fromId(null)).isNull();
    }
}
