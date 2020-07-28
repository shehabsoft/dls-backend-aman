package ae.rta.dls.backend.service.util;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.brm.calculation.fee.FeeVM;
import ae.rta.dls.backend.common.util.brm.calculation.fee.FeesListVM;
import ae.rta.dls.backend.config.Constants;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.sct.ServiceDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import ae.rta.dls.backend.service.sct.ServiceService;
import ae.rta.dls.backend.service.trn.ApplicationService;
import ae.rta.dls.backend.service.trn.ServiceRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

/**
 * Abstract Service Request Utility.
 *
 * This abstract class is designed to encapsulate all transaction template methods to be overriden
 * by any kind of service request.
 *
 * @author Mena Emiel
 */

@Service
@Transactional
public abstract class AbstractServiceRequest {

    /*
     * Class variables
     */

    /** Logger instance. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ServiceRequestService serviceRequestService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private CommonUtil commonUtil;

    /* ******************************************************* */
    /* ******************** Helper Methods ******************* */
    /* ******************************************************* */

    /**
     * Get Service Request Service
     * @return ServiceRequestService
     */
    public ServiceRequestService getServiceRequestService(){
        return serviceRequestService;
    }

    /**
     * Get Related Service DTO.
     *
     * @param code Service Code.
     * @return Related Service DTO.
     */
    protected ServiceDTO getService(String code) {
        Optional<ServiceDTO> serviceDTO = serviceService.findByCode(code);
        if(serviceDTO.isPresent()) {
            return serviceDTO.get();
        }
        return null;
    }

    /**
     * Get Related Service DTO.
     *
     * @param applicationId Service Code.
     * @return Related Service DTO.
     */
    protected Optional<ApplicationDTO> getApplication(Long applicationId) {
        return applicationService.findOne(applicationId);
    }

    /**
     * Get related service request dto
     *
     * @param serviceRequest service request DTO.
     * @return The related service request dto
     */
    protected <T extends AbstractAuditingDTO> T getServiceRequestDTO(ServiceRequestDTO serviceRequest) {
        try {

            // Load the related service DTO class from service entity
            ServiceDTO service = getService(serviceRequest.getServiceCode());

            if (service == null) {
                throw new SystemException("No active service for provided service code");
            }

            if (service.getServiceDTOClass() == null) {
                throw new SystemException("No service DTO found for this service");
            }

            return (T) CommonUtil.getMapper().convertValue(serviceRequest.getServiceDocument().getParameters(), Class.forName(service.getServiceDTOClass()));

        } catch (Exception ex) {
            log.error("Provided service document structure not matching related service DTO class.");
            throw new SystemException("Provided service document structure not matching related service DTO.");
        }
    }

    /**
     * Parse Service document.
     *
     * @param serviceRequest Service Request DTO.
     */
    private void parseRequest(ServiceRequestDTO serviceRequest) {
        try {
            // Load the related service DTO class from service entity
            ServiceDTO service = getService(serviceRequest.getServiceCode());

            if (service == null) {
                throw new SystemException("No active service for provided service code");
            }

            if (service.getServiceDTOClass() == null) {
                throw new SystemException("No service DTO found for this service");
            }

            CommonUtil.getMapper().convertValue(serviceRequest.getServiceDocument().getParameters(), Class.forName(service.getServiceDTOClass()));

        } catch (Exception ex) {
            log.error("Provided service document structure not matching related service DTO");
            throw new SystemException("Provided service document structure not matching related service DTO");
        }
    }


    /* ******************************************************* */
    /* ******************** Template Methods ******************* */
    /* ******************************************************* */

    /**
     * Pre create service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void preCreate(ServiceRequestDTO serviceRequestDTO);

    /**
     * Post create service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void postCreate(ServiceRequestDTO serviceRequestDTO);

    /**
     * Pre update service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void preUpdate(ServiceRequestDTO serviceRequestDTO);

    /**
     * Post update service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void postUpdate(ServiceRequestDTO serviceRequestDTO);

    /**
     * Pre verify service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void preVerify(ServiceRequestDTO serviceRequestDTO);

    /**
     * Post verify service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void postVerify(ServiceRequestDTO serviceRequestDTO);

    /**
     * Pre confirm service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void preConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO);

    /**
     * Post confirm service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void postConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO);

    /**
     * Pre reject service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void preReject(ServiceRequestDTO serviceRequestDTO);

    /**
     * Post reject service request
     *
     * @param serviceRequestDTO service request info
     */
    protected abstract void postReject(ServiceRequestDTO serviceRequestDTO);

                        /* ******************************************************* */
                        /* ******* Template Methods Initial Implementation ******* */
                        /* ******************************************************* */

    /**
     * Create service request
     *
     * @param serviceRequestDTO service request object
     * @return created service request object
     */
    public ServiceRequestDTO create(ServiceRequestDTO serviceRequestDTO) {

        // Log the entry point
        log.debug("Inside AbstractServiceRequest :: create() :: with dto {} ", serviceRequestDTO);

        parseRequest(serviceRequestDTO);

        // Check if there is a non-rejected request of the same type related to the same application
        Optional<ServiceRequestDTO> serviceRequestResponseDTO = serviceRequestService.findOne(serviceRequestDTO.getServiceCode(), serviceRequestDTO.getApplicationId());
        if(serviceRequestResponseDTO.isPresent()) {
            ServiceRequestStatus serviceRequestStatus = serviceRequestResponseDTO.get().getStatus();
            if(serviceRequestStatus != null && !serviceRequestStatus.equals(ServiceRequestStatus.REJECTED)) {
                return serviceRequestResponseDTO.get();
            }
        }

        // Check common rules
        checkCommonRules(serviceRequestDTO);

        // Call preCreate before request creation
        preCreate(serviceRequestDTO);

        // Log after pre create request
        log.debug("Inside AbstractServiceRequest :: create() :: preCreate() executed successfully");

        // Update the passed serviceRequestDTO with some system attributes
        serviceRequestDTO.setStatus(ServiceRequestStatus.UNDER_PROCESSING);
        serviceRequestDTO.setStatusDate(LocalDateTime.now());
        serviceRequestDTO.setStatusDescription(commonUtil
                                               .getDomainValueDescription(ServiceRequestStatus.UNDER_PROCESSING.value(),
                                                   ServiceRequestStatus.DOMAIN_CODE));
        // load current active phase from the related application
        Optional<ApplicationDTO> optionalApplication = getApplication(serviceRequestDTO.getApplicationId());
        if (! optionalApplication.isPresent()) {
            log.error("Invalid application details with application id {}", serviceRequestDTO.getApplicationId());
            throw new SystemException("Invalid application id");
        }
        // set request phase with the current active phase on the application level
        serviceRequestDTO.setPhaseType(optionalApplication.get().getActivePhase());

        // Persist service request
        ServiceRequestDTO result = serviceRequestService.save(serviceRequestDTO);

        // Call postCreate method after request creation
        postCreate(serviceRequestDTO);

        // Log after post create request
        log.debug("Inside  AbstractServiceRequest :: create() :: postCreate() executed successfully");

        // Return new created service request object
        return result;
    }

    /**
     * Update service request
     *
     * @param serviceRequestDTO service request object
     * @return updated service request object
     */
    public ServiceRequestDTO update(ServiceRequestDTO serviceRequestDTO) {

        // Log the entry point
        log.debug("Inside AbstractServiceRequest :: update() :: with dto {} ", serviceRequestDTO);

        parseRequest(serviceRequestDTO);

        // Validate parameters
        if (serviceRequestDTO.getId() == null) {
            log.error("Invalid service request details with ---> serviceRequestDTO {} ", serviceRequestDTO);
            throw new SystemException("Invalid service request id");
        }

        // Check if the service request is verified to prevent any update
        Optional<ServiceRequestDTO> optionalDto = serviceRequestService.findOne(serviceRequestDTO.getId());

        if (optionalDto.isPresent() &&
            (ServiceRequestStatus.VERIFIED_AND_LOCKED.value().equals(optionalDto.get().getStatus().value()) ||
             ServiceRequestStatus.CONFIRMED.value().equals(optionalDto.get().getStatus().value()) ||
             ServiceRequestStatus.REJECTED.value().equals(optionalDto.get().getStatus().value()))) {
            log.error("It's not allowed to update verified, confirmed, or rejected service request with the following details " +
                ":: serviceRequestDTO {} and" +
                ":: service request id {}", serviceRequestDTO, serviceRequestDTO.getId());
            throw new SystemException("It's not allowed to update a verified, confirmed, or rejected service request");
        }

        if(optionalDto.isPresent()) {
            LinkedHashMap newServiceDocument = (LinkedHashMap) serviceRequestDTO.getServiceDocument().getParameters();
            LinkedHashMap oldServiceDocument = (LinkedHashMap) optionalDto.get().getServiceDocument().getParameters();

            commonUtil.handleObjectType(oldServiceDocument,newServiceDocument);
        }

        if (optionalDto.isPresent() &&
            ! serviceRequestDTO.getServiceCode().equals(optionalDto.get().getServiceCode())) {
            throw new SystemException("t is not allowed to update the value of {serviceCode} element");
        }

        // Check common rules
        checkCommonRules(serviceRequestDTO);

        // Call preUpdate before request modification
        preUpdate(serviceRequestDTO);

        // Log after pre create request
        log.debug("Inside AbstractServiceRequest :: update() :: preUpdate() executed successfully");

        // Update the passed serviceRequestDTO with ssme values from existing dto
        if(optionalDto.isPresent()) {
            serviceRequestDTO.setStatus(optionalDto.get().getStatus());
            serviceRequestDTO.setStatusDate(LocalDateTime.now());
            serviceRequestDTO.setStatusDescription(optionalDto.get().getStatusDescription());
            serviceRequestDTO.setPhaseType(optionalDto.get().getPhaseType());
        }

        // Update service request
        ServiceRequestDTO result = serviceRequestService.save(serviceRequestDTO);

        // Call postUpdate method after request modification
        postUpdate(serviceRequestDTO);

        // Log after post update request
        log.debug("Inside  AbstractServiceRequest :: update() :: postUpdate() executed successfully");

        // Return the updated service request object
        return result;
    }

    /**
     * Verify service request
     *
     * @param serviceRequestId service request id
     * @return verified service request object
     */
    public ServiceRequestDTO verify(Long serviceRequestId) {

        // Log the entry point
        log.debug("Inside AbstractServiceRequest :: verify() :: with request id {} ", serviceRequestId);

        // Load the related service request
        Optional<ServiceRequestDTO> optionalServiceRequestDTO = serviceRequestService.findOne(serviceRequestId);

        // Check the retrieved object
        if (! optionalServiceRequestDTO.isPresent()) {
            log.error("Service request is not found with service id {} ", serviceRequestId);
            throw new SystemException("No service request found under the given id");
        }

        // Get service request object
        ServiceRequestDTO  serviceRequestDTO  = optionalServiceRequestDTO.get();

        if (ServiceRequestStatus.VERIFIED_AND_LOCKED.value().equals(serviceRequestDTO.getStatus().value())) {
            log.error("Service request id {} is already verified ", serviceRequestId);
            throw new SystemException("Re-verify service request is not allowed");
        }

        if (!ServiceRequestStatus.UNDER_PROCESSING.value().equals(serviceRequestDTO.getStatus().value())) {
            log.error("It's not allowed to update service request if the old status is not under processing");
            throw new SystemException("It's not allowed to update service request if the old status is not under processing");
        }

        // Call preVerify before request verification
        /* ******************************************************************************************* */
        /* *********************** Call preVerify before request verification ************************ */
        /* ******************************************************************************************* */

        preVerify(serviceRequestDTO);


        // Log after pre verify request
        log.debug("Inside AbstractServiceRequest :: verify() :: preVerify() executed successfully");

        /* ******************************************************************************************* */
        /* ****************** Set service request status verified and locked ************************* */
        /* ******************************************************************************************* */
        serviceRequestDTO.setStatus(ServiceRequestStatus.VERIFIED_AND_LOCKED);
        serviceRequestDTO.setStatusDate(LocalDateTime.now());
        serviceRequestDTO.setStatusDescription(commonUtil
            .getDomainValueDescription(ServiceRequestStatus.VERIFIED_AND_LOCKED.value(), ServiceRequestStatus.DOMAIN_CODE));


        /* ******************************************************************************************* */
        /* ******************** Call postVerify method after request verification ******************** */
        /* ******************************************************************************************* */
        postVerify(serviceRequestDTO);

        // Set Tcn Fees Collected flag on application criteria
        Optional<ApplicationDTO> applicationDTO = getApplication(serviceRequestDTO.getApplicationId());
        if(!applicationDTO.isPresent()) {
            log.error("No application found for provided id {} ", serviceRequestDTO.getApplicationId());
            throw new SystemException("No application found for provided id");
        }

        if(!applicationDTO.get().getApplicationCriteria().getTcnFeesCollected().booleanValue() &&
            hasOpenFileFees(serviceRequestDTO)) {
            applicationDTO.get().getApplicationCriteria().setTcnFeesCollected(Boolean.TRUE);
            applicationService.update(applicationDTO.get());
        }

        // Save the Changes
        ServiceRequestDTO result = serviceRequestService.save(serviceRequestDTO);

        // Log after post update request
        log.debug("Inside  AbstractServiceRequest :: verify() :: postVerify() executed successfully");

        // Return the updated service request object
        return result;
    }

    /**
     * Confirm service request
     *
     * @param serviceRequestDTO
     * @return confirmed service request object
     */
    public ServiceRequestDTO confirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO,
                                     String paymentMethod, Long paymentReference, LocalDateTime paymentDate) {

        // Log the entry point
        log.debug("Inside AbstractServiceRequest :: confirm() :: with request id {} ", serviceRequestDTO.getId());

        parseRequest(serviceRequestDTO);

        // Load the related service request
        Optional<ServiceRequestDTO> optionalServiceRequestDTO = serviceRequestService.findOne(serviceRequestDTO.getId());

        // Check the retrieved object
        if (! optionalServiceRequestDTO.isPresent()) {
            log.error("Service request is not found with service id {} ", serviceRequestDTO.getId());
            throw new SystemException("No service request found with the given id");
        }

        // Get service request object
        ServiceRequestDTO oldServiceRequestDTO = optionalServiceRequestDTO.get();

        if (ServiceRequestStatus.CONFIRMED.value().equals(oldServiceRequestDTO.getStatus().value())) {
            log.error("Service request id {} is already confirmed ", serviceRequestDTO.getId());
            throw new SystemException("Re-confirm service request is not allowed");
        }

        if (!ServiceRequestStatus.VERIFIED_AND_LOCKED.value().equals(oldServiceRequestDTO.getStatus().value())) {
            log.error("It's not allowed to update service request if the old status is not verified and locked");
            throw new SystemException("It's not allowed to update service request if the old status is not verified and locked");
        }

        // Call preConfirm before request confirmation
        preConfirm(oldServiceRequestDTO, customerInfoDTO);

        // Log after pre confirm request
        log.debug("Inside AbstractServiceRequest :: confirm() :: preConfirm() executed successfully");

        // Set service request status confirmed
        oldServiceRequestDTO.setStatus(ServiceRequestStatus.CONFIRMED);
        oldServiceRequestDTO.setStatusDate(LocalDateTime.now());
        oldServiceRequestDTO.setStatusDescription(commonUtil
            .getDomainValueDescription(ServiceRequestStatus.CONFIRMED.value(), ServiceRequestStatus.DOMAIN_CODE));

        // Set payment information
        oldServiceRequestDTO.setPaymentReference(paymentReference);
        oldServiceRequestDTO.setPaymentMethod(paymentMethod);
        oldServiceRequestDTO.setPaymentDate(paymentDate);
        oldServiceRequestDTO.setPaidBy(Constants.SYSTEM_ACCOUNT);

        // Confirm service request
        ServiceRequestDTO result = serviceRequestService.save(oldServiceRequestDTO);

        // Call postConfirm method after request confirmation
        postConfirm(oldServiceRequestDTO, customerInfoDTO);

        // Log after post update request
        log.debug("Inside  AbstractServiceRequest :: confirm() :: postConfirm() executed successfully");

        // Return the updated service request object
        return result;
    }

    /**
     * Verify service request
     *
     * @param serviceRequestId service request id
     * @return verified service request object
     */
    public ServiceRequestDTO reject(Long serviceRequestId) {
        return null;
    }

    /**
     * Check common rules
     *
     * @param serviceRequestDTO service request object
     */
    private void checkCommonRules(ServiceRequestDTO serviceRequestDTO) {
        // Check the common validations
        if (serviceRequestDTO.getStatus() != null) {
            throw new SystemException("It is not allowed to send any value for {status} element");
        }

        if (serviceRequestDTO.getStatusDate() != null) {
            throw new SystemException("It is not allowed to send any value for {statusDate} element");
        }

        if (serviceRequestDTO.getStatusDescription() != null) {
            throw new SystemException("It is not allowed to send any value for {statusDescription} element");
        }

        if (serviceRequestDTO.getPhaseType() != null) {
            throw new SystemException("It is not allowed to send any value for {phaseType} element");
        }
    }

    /**
     * Has Open File Fees
     * @param serviceRequestDTO
     * @return true/false
     */
    private boolean hasOpenFileFees(ServiceRequestDTO serviceRequestDTO) {

        if (serviceRequestDTO == null || serviceRequestDTO.getFeeDocument() == null ||
            serviceRequestDTO.getFeeDocument().getFeesListVM() == null ||
            serviceRequestDTO.getFeeDocument().getFeesListVM().getList() == null ||
            serviceRequestDTO.getFeeDocument().getFeesListVM().getList().isEmpty()) {
            return false;
        }

        FeeDocumentJsonType feeDocumentJsonType =  serviceRequestDTO.getFeeDocument();
        FeesListVM feesListVM = feeDocumentJsonType.getFeesListVM();
        Set<FeeVM> list = feesListVM.getList();

        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            FeeVM feeVM = (FeeVM) iterator.next();
            if(feeVM.getCode().equals("SVC_006_FEE_01")) {
                return true;
            }
        }

        return false;
    }
}
