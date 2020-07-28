package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;
import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.sct.ServiceDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.sct.ServiceService;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.service.trn.ServiceRequestService;
import ae.rta.dls.backend.domain.trn.ServiceRequest;
import ae.rta.dls.backend.repository.trn.ServiceRequestRepository;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import ae.rta.dls.backend.service.mapper.trn.ServiceRequestMapper;
import ae.rta.dls.backend.service.util.ServiceRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ServiceRequest.
 */
@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);

    private final ServiceRequestFactory serviceRequestFactory;

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    private final ApplicationService applicationService;

    private final ServiceService serviceService;

    private final CommonUtil commonUtil;

    public ServiceRequestServiceImpl(ServiceRequestFactory serviceRequestFactory,
                                     ServiceRequestRepository serviceRequestRepository,
                                     ServiceRequestMapper serviceRequestMapper,
                                     ApplicationService applicationService,
                                     ServiceService serviceService,
                                     CommonUtil commonUtil) {
        this.serviceRequestFactory = serviceRequestFactory;
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestMapper = serviceRequestMapper;
        this.applicationService = applicationService;
        this.serviceService = serviceService;
        this.commonUtil = commonUtil;
    }

    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to save ServiceRequest : {}", serviceRequestDTO);
        ServiceRequest serviceRequest = serviceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);
        return serviceRequestMapper.toDto(serviceRequest);
    }

    /**
     * Get all the serviceRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRequests");
        return serviceRequestRepository.findAll(pageable)
                .map(serviceRequestMapper::toDto);
    }


    /**
     * Get one serviceRequest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceRequestDTO> findOne(Long id) {
        log.debug("Request to get ServiceRequest : {}", id);
        return serviceRequestRepository.findById(id)
                .map(serviceRequestMapper::toDto);
    }

    /**
     * Get the "id" serviceRequest.
     *
     * @param applicationId the id of the application
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> findByApplicationId(Long applicationId) {
        log.debug("Request to get ServiceRequest by application : {}", applicationId);
        return serviceRequestMapper.toDto(serviceRequestRepository.findByApplication_Id(applicationId));
    }

    /**
     * Delete the serviceRequest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceRequest : {}", id);
        serviceRequestRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isServiceRequestExit(String serviceCode, Long applicationId) {
        log.debug("Request to check if the service request exists by serviceCode {} and applicationId {}"
                , serviceCode, applicationId);
        return serviceRequestRepository.existsByServiceCodeAndApplication_Id(serviceCode, applicationId);
    }

    /**
     * Find Service Request.
     *
     * @param serviceCode   Service Code.
     * @param applicationId Application Id.
     * @return Service Request DTO.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceRequestDTO> findOne(String serviceCode, Long applicationId) {

        Optional<ServiceRequest> request = serviceRequestRepository.findByServiceCodeAndApplication_Id(serviceCode, applicationId);

        Optional<ServiceRequestDTO> requestDTO = request.map(serviceRequestMapper::toDto);
        if (requestDTO.isPresent()) {
            Optional<ApplicationDTO> application = applicationService.findOne(applicationId);
            if(application.isPresent()) {
                requestDTO.get().setApplicationDetails(application.get());
            }

        }

        return requestDTO;
    }

    /**
     * Application Service Requests Verification.
     *
     * @param applicationDTO Application DTO.
     * @return Service Request DTO.
     */
    @Override
    @Transactional
    public ApplicationDTO applicationServiceRequestsVerification(ApplicationDTO applicationDTO) {

        // Update application (details and phase) and return the new updated application..
        applicationService.update(applicationDTO);

        Optional<List<ServiceRequest>> request = serviceRequestRepository.findByApplication_IdAndStatusIs(applicationDTO.getId(), ServiceRequestStatus.UNDER_PROCESSING);
        Optional<List<ServiceRequestDTO>> serviceRequests = request.map(serviceRequestMapper::toDto);

        if (serviceRequests.isPresent()) {
            for (ServiceRequestDTO serviceRequest : serviceRequests.get()) {
                serviceRequestFactory.getServiceRequest(serviceRequest.getServiceCode()).verify(serviceRequest.getId());
            }
        }

        return applicationDTO;
    }


    /**
     * Find Verified and Locked Service Request.
     *
     * @param applicationId Application Id.
     */
    @Override
    @Transactional(readOnly = true)
    public ApplicationDTO findVerifiedAndLockedServiceRequest(Long applicationId) {

        Optional<ApplicationDTO>  applicationDTO = applicationService.findOne(applicationId);

        if(!applicationDTO.isPresent()) {
            return null;
        }

        Optional<List<ServiceRequest>> serviceRequests = serviceRequestRepository.findByApplication_IdAndStatus(applicationId, ServiceRequestStatus.VERIFIED_AND_LOCKED);
        Optional<List<ServiceRequestDTO>> serviceRequestsDTO = serviceRequests.map(serviceRequestMapper::toDto);

        if(!serviceRequestsDTO.isPresent()) {
            return null;
        }

        for (ServiceRequestDTO serviceRequest : serviceRequestsDTO.get()) {

            Optional<ServiceDTO> serviceDTO = serviceService.findByCode(serviceRequest.getServiceCode());
            if(serviceDTO.isPresent()) {
                serviceRequest.setServiceDetails(serviceDTO.get());
            }
        }

        applicationDTO.get().setServiceRequests(null);
        applicationDTO.get().setServiceRequests(serviceRequestsDTO.get().stream().collect(Collectors.toSet()));


        return applicationDTO.get();
    }

    /**
     * Find By Application_Id And Status and service code
     *
     * @param applicationId Application id
     * @param status Service request status
     * @param serviceCode Service code
     * @return Service request list
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceRequestDTO> findByApplication_IdAndStatusAndServiceCode(Long applicationId, ServiceRequestStatus status, String serviceCode) {
         Optional<ServiceRequest>  serviceRequest = serviceRequestRepository.findByApplication_IdAndStatusAndServiceCode(
            applicationId, status, serviceCode);

        Optional<ServiceRequestDTO> requestDTO = serviceRequest.map(serviceRequestMapper::toDto);

        if (requestDTO.isPresent()) {
            Optional<ApplicationDTO> application = applicationService.findOne(applicationId);
            if(application.isPresent()) {
                requestDTO.get().setApplicationDetails(application.get());
            }
        }
        return requestDTO;
    }

    /**
     * Find By Application Id
     *
     * @param applicationId Application id
     * @return Service request list
     */
    public Optional<ServiceRequestDTO> findByApplicationIdAndServiceCode(Long applicationId, String serviceCode) {
        return serviceRequestRepository.findByApplication_IdAndServiceCode(
                applicationId, serviceCode).map(serviceRequestMapper::toDto);
    }

    /**
     * Save a service request.
     *
     * @param serviceRequestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    @Transactional
    public ServiceRequestDTO update(ServiceRequestDTO serviceRequestDTO) {

        Optional<ServiceRequestDTO> oldServiceRequestDTO = findOne(serviceRequestDTO.getId());

        if(oldServiceRequestDTO.isPresent()) {
            serviceRequestDTO = commonUtil.copyNotNullProperty(oldServiceRequestDTO.get(), serviceRequestDTO);
        }

        ServiceRequest serviceRequest = serviceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);

        return serviceRequestMapper.toDto(serviceRequest);
    }

    /**
     * Confirm All Application Service Requests.
     *
     * @param customerInfoDTO customer info object.
     * @param applicationId application reference number
     */
    @Override
    @Transactional
    public void confirmAllApplicationServiceRequests(CustomerInfoDTO customerInfoDTO, Long applicationId) {

        Optional<List<ServiceRequest>> request = serviceRequestRepository.findByApplication_IdAndStatusIs(applicationId, ServiceRequestStatus.VERIFIED_AND_LOCKED);
        Optional<List<ServiceRequestDTO>> serviceRequests = request.map(serviceRequestMapper::toDto);

        String paymentMethod = customerInfoDTO.getPaymentMethod();
        Long paymentReference = customerInfoDTO.getPaymentReference();
        LocalDateTime paymentDate = customerInfoDTO.getPaymentDate();

        // Nullify payment details before reflect on product tables
        customerInfoDTO.setPaymentReference(null);
        customerInfoDTO.setPaymentMethod(null);
        customerInfoDTO.setPaymentDate(null);

        if (serviceRequests.isPresent()) {

            for (ServiceRequestDTO serviceRequest : serviceRequests.get()) {
                serviceRequestFactory.getServiceRequest(serviceRequest.getServiceCode()).confirm(serviceRequest, customerInfoDTO,
                                                        paymentMethod, paymentReference, paymentDate);
            }
        }
    }
}
