package ae.rta.dls.backend.service.trn;

import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;
import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ServiceRequest.
 */
public interface ServiceRequestService {

    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save
     * @return the persisted entity
     */
    ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO);

    /**
     * Get all the serviceRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceRequestDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceRequest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceRequestDTO> findOne(Long id);

    /**
     * Get the "id" serviceRequest.
     *
     * @param applicationId the id of the application
     * @return the entity
     */
    List<ServiceRequestDTO> findByApplicationId(Long applicationId);

    /**
     * Delete the "id" serviceRequest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Is service request exist (check by service code and application id)
     *
     * @param serviceCode service code
     * @param applicationId application id
     * @return true/false
     */
    boolean isServiceRequestExit(String serviceCode, Long applicationId);

    /**
     * Find Service Request.
     *
     * @param serviceCode Service Code.
     * @param applicationId Application Id.
     * @return Service Request DTO.
     */
    Optional<ServiceRequestDTO> findOne(String serviceCode, Long applicationId);

    /**
     * Application Service Requests Verification.
     *
     * @param applicationDTO Application DTO.
     * @return Application DTO.
     */
    ApplicationDTO applicationServiceRequestsVerification(ApplicationDTO applicationDTO);

    /**
     * Find Verified and Locked Service Request.
     *
     * @param applicationId Application Id.
     * @return Application DTO.
     */
    ApplicationDTO findVerifiedAndLockedServiceRequest(Long applicationId);

    /**
     * Find By Application_Id And Status and service code
     *
     * @param applicationId Application id
     * @param status Service request status
     * @param serviceCode Service code
     * @return Service request list
     */
    Optional<ServiceRequestDTO> findByApplication_IdAndStatusAndServiceCode(Long applicationId, ServiceRequestStatus status, String serviceCode);

    /**
     * Find By Application Id
     *
     * @param applicationId Application id
     * @param serviceCode Service code
     * @return Service request list
     */
    Optional<ServiceRequestDTO> findByApplicationIdAndServiceCode(Long applicationId, String serviceCode);

    /**
     * Save a service request.
     *
     * @param serviceRequestDTO the entity to save
     * @return the persisted entity
     */

    ServiceRequestDTO update(ServiceRequestDTO serviceRequestDTO);

    /**
     * Confirm All Application Service Requests.
     *
     * @param customerInfoDTO customer info object.
     * @param applicationId application reference number
     *
     */
    void confirmAllApplicationServiceRequests(CustomerInfoDTO customerInfoDTO, Long applicationId);
}
