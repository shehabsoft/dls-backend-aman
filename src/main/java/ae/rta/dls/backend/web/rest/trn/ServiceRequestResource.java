package ae.rta.dls.backend.web.rest.trn;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;
import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.security.util.AuthoritiesConstants;
import ae.rta.dls.backend.service.trn.ServiceRequestService;
import ae.rta.dls.backend.service.util.ServiceRequestFactory;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.util.HeaderUtil;
import ae.rta.dls.backend.web.rest.util.PaginationUtil;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ServiceRequest.
 */
@RestController
@RequestMapping("/api/trn")
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class ServiceRequestResource {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestResource.class);

    private static final String ENTITY_NAME = "dlsBackendServiceRequest";

    private static final String INVALID_ID_ERROR_MSG = "Invalid id";

    @Autowired
    private ServiceRequestFactory serviceRequestFactory;

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestResource(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    /**
     * POST  /service-requests : Create a new serviceRequest.
     *
     * @param serviceRequestDTO the serviceRequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceRequestDTO, or with status 400 (Bad Request) if the serviceRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-requests")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "," + AuthoritiesConstants.VERIFIED_ROLE+ "\") ")
    public ResponseEntity<ServiceRequestDTO> createServiceRequest(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceRequest : {}", serviceRequestDTO);
        if (serviceRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceRequestDTO result = serviceRequestFactory.getServiceRequest(serviceRequestDTO.getServiceCode())
                .create(serviceRequestDTO);
        return ResponseEntity.created(new URI("/api/trn/service-requests/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /service-requests : Updates an existing serviceRequest.
     *
     * @param serviceRequestDTO the serviceRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestDTO,
     * or with status 400 (Bad Request) if the serviceRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-requests")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "," + AuthoritiesConstants.BPM_ROLE + "," + AuthoritiesConstants.VERIFIED_ROLE+ "," + AuthoritiesConstants.ANONYMOUS_ROLE+"\") ")
    public ResponseEntity<ServiceRequestDTO> updateServiceRequest(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceRequest : {}", serviceRequestDTO);
        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid service request id", ENTITY_NAME, "idnull");
        }
        ServiceRequestDTO result = serviceRequestFactory.getServiceRequest(serviceRequestDTO.getServiceCode())
                                    .update(serviceRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-requests/verify : Verify an existing serviceRequest.
     *
     * @param serviceRequestDTO the serviceRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestDTO,
     * or with status 400 (Bad Request) if the serviceRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect//@PathVariable String serviceCode, @PathVariable Long id
     */
    @PutMapping("/service-requests/verify")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.BPM_ROLE + "\") ")
    public ResponseEntity<ServiceRequestDTO> verifyServiceRequest(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO) throws URISyntaxException {

        log.debug("REST request to verify ServiceRequest with code: {} and id", serviceRequestDTO.getId());

        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException(INVALID_ID_ERROR_MSG, ENTITY_NAME, "id null");
        }

        if (StringUtil.isBlank(serviceRequestDTO.getServiceCode())) {
            throw new BadRequestAlertException("Invalid serviceCode", ENTITY_NAME, "serviceCode null");
        }

        ServiceRequestDTO result = serviceRequestFactory.getServiceRequest(serviceRequestDTO.getServiceCode()).verify(serviceRequestDTO.getId());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-requests/verify/application-service-requests : Verify all application non verified service request.
     *
     * @param applicationDTO Application service request verification VM
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestDTO,
     * or with status 400 (Bad Request) if the serviceRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect//@PathVariable String serviceCode, @PathVariable Long id
     */
    @PutMapping("/service-requests/verify-all")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.BPM_ROLE + "\") ")
    public ResponseEntity<ApplicationDTO> verifyAllServiceRequest(@Valid @RequestBody ApplicationDTO applicationDTO) {
        log.debug("REST request to verify ServiceRequest with applicationDTO: {} ", applicationDTO);

        ApplicationDTO application = serviceRequestService.applicationServiceRequestsVerification(applicationDTO);

        return ResponseEntity.ok().body(application);
    }

    /**
     * GET  /service-requests/applicationReferenceNo/{applicationReferenceNo}/status/{status}/serviceCode/{serviceCode}
     *
     * @param applicationReferenceNo
     * @param status
     * @param serviceCode
     * @return the ResponseEntity with status 200 (OK) and with body the service request DTO,
     * or with status 400 (Bad Request) if the service request DTO is not valid,
     * or with status 500 (Internal Server Error) if the service request DTO
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/service-requests/applicationReferenceNo/{applicationReferenceNo}/status/{status}/serviceCode/{serviceCode}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<ServiceRequestDTO> findByApplicationIdAndStatusAndServiceCode(@PathVariable Long applicationReferenceNo,
                                                                                        @PathVariable ServiceRequestStatus status,
                                                                                        @PathVariable String serviceCode) {
        log.debug("REST request to get by application id: {}, stauts: {}, serviceCode:{} ", applicationReferenceNo, status, serviceCode);

        Optional<ServiceRequestDTO> serviceRequestDTO = serviceRequestService.
            findByApplication_IdAndStatusAndServiceCode(applicationReferenceNo, status, serviceCode);

        return ResponseUtil.wrapOrNotFound(serviceRequestDTO);
    }

    /**
     * PUT  /service-requests/verified-and-locked/{applicationReferenceNo} : find verified and locked service request.
     *
     * @param applicationReferenceNo
     * @return the ResponseEntity with status 200 (OK) and with body of service requests
     * or with status 400 (Bad Request) if the applicationReferenceNo doesn't have any service request
     * @throws URISyntaxException if the Location URI syntax is incorrect//@PathVariable Long applicationReferenceNo
     */
    @GetMapping("/service-requests/verified-and-locked/{applicationReferenceNo}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<ApplicationDTO> findVerifiedAndLockedServiceRequest(@PathVariable Long applicationReferenceNo) {
        log.debug("REST request to find verified and locked service request application id: {} ", applicationReferenceNo);

        ApplicationDTO applicationDTO = serviceRequestService.findVerifiedAndLockedServiceRequest(applicationReferenceNo);

        return ResponseEntity.ok().body(applicationDTO);
    }

    /**
     * GET  /service-requests : get all the serviceRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceRequests in body
     */
    @GetMapping("/service-requests")
    public ResponseEntity<List<ServiceRequestDTO>> getAllServiceRequests(Pageable pageable) {
        log.debug("REST request to get a page of ServiceRequests");
        Page<ServiceRequestDTO> page = serviceRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trn/service-requests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /service-requests/:id : get the "id" serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceRequestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-requests/{id}")
    public ResponseEntity<ServiceRequestDTO> getServiceRequest(@PathVariable Long id) {
        log.debug("REST request to get ServiceRequest : {}", id);
        Optional<ServiceRequestDTO> serviceRequestDTO = serviceRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRequestDTO);
    }

    /**
     * GET  /service-requests/verify : get Service Request By Service Code And Application Id.
     *
     * @param serviceCode service request code
     * @param applicationReferenceNo Application Reference No.
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestDTO,
     * or with status 400 (Bad Request) if the serviceRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/service-requests/serviceCode/{serviceCode}/applicationReferenceNo/{applicationReferenceNo}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\") OR hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\") OR hasRole(\"" + AuthoritiesConstants.ANONYMOUS_ROLE + "\") OR hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<ServiceRequestDTO> getServiceRequestByServiceCodeAndApplicationId(@PathVariable String serviceCode,
                                                                                            @NotNull @PathVariable Long applicationReferenceNo) {
        log.debug("REST request to get ServiceRequest by applicationReferenceNo and service code : {} , {}", applicationReferenceNo , serviceCode);
        Optional<ServiceRequestDTO> serviceRequestResponseDTO = serviceRequestService.findOne(serviceCode, applicationReferenceNo);
        return ResponseUtil.wrapOrNotFound(serviceRequestResponseDTO);
    }

    /**
     * GET  /service-requests/applicationReferenceNo/{applicationReferenceNo} : get Service Request By Application Id.
     *
     * @param applicationReferenceNo Application Reference No.
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestDTO,
     * or with status 400 (Bad Request) if the serviceRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/service-requests/applicationReferenceNo/{applicationReferenceNo}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE+ "\")")
    public List<ServiceRequestDTO> getServiceRequestByApplicationId(@PathVariable Long applicationReferenceNo) {
        log.debug("REST request to get ServiceRequest by applicationReferenceNo : {}", applicationReferenceNo);

        return serviceRequestService.findByApplicationId(applicationReferenceNo);
    }

    /**
     * DELETE  /service-requests/:id : delete the "id" serviceRequest.
     *
     * @param id the id of the serviceRequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-requests/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable Long id) {
        log.debug("REST request to delete ServiceRequest : {}", id);
        serviceRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * PUT  /service-requests/verify : Verify an existing serviceRequest.
     *
     * @param customerInfoDTO customer Info DTO.
     * @param serviceRequestId Service Request Id.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestDTO,
     * or with status 400 (Bad Request) if the serviceRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect//@PathVariable String serviceCode, @PathVariable Long id
     */
    @PutMapping("/service-requests/confirm/serviceRequestReferenceNo/{serviceRequestId}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.BPM_ROLE + "\") ")
    public ResponseEntity<ServiceRequestDTO> confirmServiceRequest(@Valid @RequestBody CustomerInfoDTO customerInfoDTO,
                                                                   @NotNull @PathVariable Long serviceRequestId) {

        Optional<ServiceRequestDTO> serviceRequest = serviceRequestService.findOne(serviceRequestId);

        if (!serviceRequest.isPresent()) {
            throw new BadRequestAlertException("not service request found for provided details");
        }

        if (StringUtil.isBlank(serviceRequest.get().getServiceCode())) {
            throw new BadRequestAlertException("missing serviceCode value");
        }

        String paymentMethod = customerInfoDTO.getPaymentMethod();
        Long paymentReference = customerInfoDTO.getPaymentReference();
        LocalDateTime paymentDate = customerInfoDTO.getPaymentDate();

        // Nullify payment details before reflect on product tables
        customerInfoDTO.setPaymentReference(null);
        customerInfoDTO.setPaymentMethod(null);
        customerInfoDTO.setPaymentDate(null);

        ServiceRequestDTO result = serviceRequestFactory.
            getServiceRequest(serviceRequest.get().getServiceCode()).confirm(serviceRequest.get(), customerInfoDTO,
                                                                                paymentMethod, paymentReference, paymentDate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceRequest.get().getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-requests/confirm-all : confirm all application non confirmed service request.
     *
     * @param customerInfoDTO customer info object.
     * @param applicationReferenceNo application reference no.
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestDTO,
     * or with status 400 (Bad Request) if the serviceRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect//@PathVariable String serviceCode, @PathVariable Long id
     */
    @PostMapping("/service-requests/confirm-all/applicationReferenceNo/{applicationReferenceNo}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.BPM_ROLE + "\") ")
    public ResponseEntity<Void> confirmAllServiceRequest(@Valid @RequestBody CustomerInfoDTO customerInfoDTO, @NotNull @PathVariable Long applicationReferenceNo) {

        serviceRequestService.confirmAllApplicationServiceRequests(customerInfoDTO, applicationReferenceNo);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationReferenceNo.toString())).build();
    }
}
