package ae.rta.dls.backend.web.rest.trn;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.trn.ServiceRequest;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.domain.type.ServiceDocumentJsonType;
import ae.rta.dls.backend.repository.trn.ServiceRequestRepository;
import ae.rta.dls.backend.service.trn.ServiceRequestService;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import ae.rta.dls.backend.service.mapper.trn.ServiceRequestMapper;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


import static ae.rta.dls.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;
/**
 * Test class for the ServiceRequestResource REST controller.
 *
 * @see ServiceRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ServiceRequestResourceIntTest {


    private static final ServiceDocumentJsonType DEFAULT_SERVICE_DOCUMENT = new ServiceDocumentJsonType();
    private static final ServiceDocumentJsonType UPDATED_SERVICE_DOCUMENT = new ServiceDocumentJsonType();


    private static final PhaseType DEFAULT_PHASE_TYPE = PhaseType.CUSTOMER_ELIGIBILITY;
    private static final PhaseType UPDATED_PHASE_TYPE = PhaseType.DRIVING_LEARNING_FILE_PROCESSING;

    private static final ServiceRequestStatus DEFAULT_STATUS = ServiceRequestStatus.UNDER_PROCESSING;
    private static final ServiceRequestStatus UPDATED_STATUS = ServiceRequestStatus.VERIFIED_AND_LOCKED;

    private static final MultilingualJsonType DEFAULT_STATUS_DESCRIPTION = new MultilingualJsonType("جديد","New");
    private static final MultilingualJsonType UPDATED_STATUS_DESCRIPTION = new MultilingualJsonType("مكتمل","Cpmpleted");

    private static final LocalDateTime DEFAULT_STATUS_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_STATUS_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_FEE_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_FEE_AMOUNT = 2D;

    private static final String DEFAULT_PAID_BY = "AAAAAAAAAA";
    private static final String UPDATED_PAID_BY = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final Long DEFAULT_PAYMENT_REFERENCE = 1L;
    private static final Long UPDATED_PAYMENT_REFERENCE = 2L;

    private static final LocalDateTime DEFAULT_PAYMENT_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_PAYMENT_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REJECTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTION_REASON = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_REJECTION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private static final LocalDateTime UPDATED_REJECTION_DATE = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_PROCESS_INSTANCE_ID = 1L;
    private static final Long UPDATED_PROCESS_INSTANCE_ID = 2L;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceRequestMapper serviceRequestMapper;

    @Autowired
    private ServiceRequestService serviceRequestService;

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

    private MockMvc restServiceRequestMockMvc;

    private ServiceRequest serviceRequest;

    private static final String TRN_SERVICE_REQUESTS_API = "/api/trn/service-requests";

    private static final String TRN_SERVICE_REQUESTS_BY_ID_API = TRN_SERVICE_REQUESTS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceRequestResource serviceRequestResource = new ServiceRequestResource(serviceRequestService);
        this.restServiceRequestMockMvc = MockMvcBuilders.standaloneSetup(serviceRequestResource)
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
    public static ServiceRequest createEntity(EntityManager em) {
        return new ServiceRequest()
            .serviceDocument(DEFAULT_SERVICE_DOCUMENT)
            .phaseType(DEFAULT_PHASE_TYPE)
            .status(DEFAULT_STATUS)
            .statusDescription(DEFAULT_STATUS_DESCRIPTION)
            .statusDate(DEFAULT_STATUS_DATE)
            .totalFeeAmount(DEFAULT_TOTAL_FEE_AMOUNT)
            .paidBy(DEFAULT_PAID_BY)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .paymentReference(DEFAULT_PAYMENT_REFERENCE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .rejectedBy(DEFAULT_REJECTED_BY)
            .rejectionReason(DEFAULT_REJECTION_REASON)
            .rejectionDate(DEFAULT_REJECTION_DATE)
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID);
    }

    @Before
    public void initTest() {
        serviceRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceRequest() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);
        restServiceRequestMockMvc.perform(post(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getServiceDocument()).isEqualTo(DEFAULT_SERVICE_DOCUMENT);
        assertThat(testServiceRequest.getPhaseType()).isEqualTo(DEFAULT_PHASE_TYPE);
        assertThat(testServiceRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testServiceRequest.getStatusDescription()).isEqualTo(DEFAULT_STATUS_DESCRIPTION);
        assertThat(testServiceRequest.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testServiceRequest.getTotalFeeAmount()).isEqualTo(DEFAULT_TOTAL_FEE_AMOUNT);
        assertThat(testServiceRequest.getPaidBy()).isEqualTo(DEFAULT_PAID_BY);
        assertThat(testServiceRequest.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testServiceRequest.getPaymentReference()).isEqualTo(DEFAULT_PAYMENT_REFERENCE);
        assertThat(testServiceRequest.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testServiceRequest.getRejectedBy()).isEqualTo(DEFAULT_REJECTED_BY);
        assertThat(testServiceRequest.getRejectionReason()).isEqualTo(DEFAULT_REJECTION_REASON);
        assertThat(testServiceRequest.getRejectionDate()).isEqualTo(DEFAULT_REJECTION_DATE);
        assertThat(testServiceRequest.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void createServiceRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest with an existing ID
        serviceRequest.setId(1L);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRequestMockMvc.perform(post(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRequestNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhaseTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setPhaseType(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setStatus(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setStatusDate(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceRequests() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList
        restServiceRequestMockMvc.perform(get("/api/service-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceDocument").value(hasItem(DEFAULT_SERVICE_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].phaseType").value(hasItem(DEFAULT_PHASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalFeeAmount").value(hasItem(DEFAULT_TOTAL_FEE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidBy").value(hasItem(DEFAULT_PAID_BY)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].paymentReference").value(hasItem(DEFAULT_PAYMENT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].rejectedBy").value(hasItem(DEFAULT_REJECTED_BY)))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON)))
            .andExpect(jsonPath("$.[*].rejectionDate").value(hasItem(DEFAULT_REJECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get(TRN_SERVICE_REQUESTS_BY_ID_API, serviceRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRequest.getId().intValue()))
            .andExpect(jsonPath("$.serviceDocument").value(DEFAULT_SERVICE_DOCUMENT.toString()))
            .andExpect(jsonPath("$.phaseType").value(DEFAULT_PHASE_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusDescription").value(DEFAULT_STATUS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.totalFeeAmount").value(DEFAULT_TOTAL_FEE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paidBy").value(DEFAULT_PAID_BY))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.paymentReference").value(DEFAULT_PAYMENT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.rejectedBy").value(DEFAULT_REJECTED_BY))
            .andExpect(jsonPath("$.rejectionReason").value(DEFAULT_REJECTION_REASON))
            .andExpect(jsonPath("$.rejectionDate").value(DEFAULT_REJECTION_DATE.toString()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceRequest() throws Exception {
        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get(TRN_SERVICE_REQUESTS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Update the serviceRequest
        Optional<ServiceRequest> optionalServiceRequest = serviceRequestRepository.findById(serviceRequest.getId());
        if (! optionalServiceRequest.isPresent()) {
            return;
        }

        ServiceRequest updatedServiceRequest = optionalServiceRequest.get();
        // Disconnect from session so that the updates on updatedServiceRequest are not directly saved in db
        em.detach(updatedServiceRequest);
        updatedServiceRequest
            .serviceDocument(UPDATED_SERVICE_DOCUMENT)
            .phaseType(UPDATED_PHASE_TYPE)
            .status(UPDATED_STATUS)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusDate(UPDATED_STATUS_DATE)
            .totalFeeAmount(UPDATED_TOTAL_FEE_AMOUNT)
            .paidBy(UPDATED_PAID_BY)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentReference(UPDATED_PAYMENT_REFERENCE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .rejectedBy(UPDATED_REJECTED_BY)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .rejectionDate(UPDATED_REJECTION_DATE)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(updatedServiceRequest);

        restServiceRequestMockMvc.perform(put(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getServiceDocument()).isEqualTo(UPDATED_SERVICE_DOCUMENT);
        assertThat(testServiceRequest.getPhaseType()).isEqualTo(UPDATED_PHASE_TYPE);
        assertThat(testServiceRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testServiceRequest.getStatusDescription()).isEqualTo(UPDATED_STATUS_DESCRIPTION);
        assertThat(testServiceRequest.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testServiceRequest.getTotalFeeAmount()).isEqualTo(UPDATED_TOTAL_FEE_AMOUNT);
        assertThat(testServiceRequest.getPaidBy()).isEqualTo(UPDATED_PAID_BY);
        assertThat(testServiceRequest.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testServiceRequest.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
        assertThat(testServiceRequest.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testServiceRequest.getRejectedBy()).isEqualTo(UPDATED_REJECTED_BY);
        assertThat(testServiceRequest.getRejectionReason()).isEqualTo(UPDATED_REJECTION_REASON);
        assertThat(testServiceRequest.getRejectionDate()).isEqualTo(UPDATED_REJECTION_DATE);
        assertThat(testServiceRequest.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc.perform(put(TRN_SERVICE_REQUESTS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeDelete = serviceRequestRepository.findAll().size();

        // Delete the serviceRequest
        restServiceRequestMockMvc.perform(delete(TRN_SERVICE_REQUESTS_BY_ID_API, serviceRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequest.class);
        ServiceRequest serviceRequest1 = new ServiceRequest();
        serviceRequest1.setId(1L);
        ServiceRequest serviceRequest2 = new ServiceRequest();
        serviceRequest2.setId(serviceRequest1.getId());
        assertThat(serviceRequest1).isEqualTo(serviceRequest2);
        serviceRequest2.setId(2L);
        assertThat(serviceRequest1).isNotEqualTo(serviceRequest2);
        serviceRequest1.setId(null);
        assertThat(serviceRequest1).isNotEqualTo(serviceRequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceRequestDTO.class);
        ServiceRequestDTO serviceRequestDTO1 = new ServiceRequestDTO();
        serviceRequestDTO1.setId(1L);
        ServiceRequestDTO serviceRequestDTO2 = new ServiceRequestDTO();
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
        serviceRequestDTO2.setId(serviceRequestDTO1.getId());
        assertThat(serviceRequestDTO1).isEqualTo(serviceRequestDTO2);
        serviceRequestDTO2.setId(2L);
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
        serviceRequestDTO1.setId(null);
        assertThat(serviceRequestDTO1).isNotEqualTo(serviceRequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceRequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceRequestMapper.fromId(null)).isNull();
    }
}
