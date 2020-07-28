package ae.rta.dls.backend.repository.trn;

import ae.rta.dls.backend.domain.enumeration.trn.ServiceRequestStatus;
import ae.rta.dls.backend.domain.trn.ServiceRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ServiceRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {


    /**
     * Is service request exist (check by service code and application id)
     *
     * @param serviceCode service code
     * @param applicationId application id
     * @return true/false
     */
    boolean existsByServiceCodeAndApplication_Id(String serviceCode, Long applicationId);

    /**
     * Find Service Request.
     *
     * @param serviceCode Service Code.
     * @param applicationId Application Id.
     * @return Service Request entity.
     */
    Optional<ServiceRequest> findByServiceCodeAndApplication_Id(String serviceCode, Long applicationId);

    /**
     * Find By Application_Id And Status.
     *
     * @param applicationId Application id
     * @param status Service request status
     * @return Service request list
     */
    Optional<List<ServiceRequest>> findByApplication_IdAndStatusIs(Long applicationId, ServiceRequestStatus status);

    /**
     * Find By Application_Id And Status.
     *
     * @param applicationId Application id
     * @param status Service request status
     * @return Service request list
     */
    Optional<List<ServiceRequest>> findByApplication_IdAndStatus(Long applicationId, ServiceRequestStatus status);

    /**
     * Find By Application_Id And Status and service code
     *
     * @param applicationId Application id
     * @param status Service request status
     * @param serviceCode Service code
     * @return Service request list
     */
    Optional<ServiceRequest> findByApplication_IdAndStatusAndServiceCode(Long applicationId, ServiceRequestStatus status, String serviceCode);

    /**
     * Find By application id And service code
     *
     * @param applicationId Application id
     * @return Service request list
     */
    Optional<ServiceRequest> findByApplication_IdAndServiceCode(Long applicationId, String serviceCode);

    /**
     * Get the "id" serviceRequest.
     *
     * @param applicationId the id of the application
     * @return the entity
     */
    List<ServiceRequest> findByApplication_Id(Long applicationId);
}
