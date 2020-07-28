package ae.rta.dls.backend.service.trn;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import ae.rta.dls.backend.domain.enumeration.trn.PhaseType;
import ae.rta.dls.backend.domain.trn.Application;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.web.rest.vm.trn.ProcessInstanceAppInfoRequestVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing Application.
 */
public interface ApplicationService {

    /**
     * Create application.
     *
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    ApplicationDTO create(ApplicationDTO applicationDTO);

    /**
     * Update application.
     *
     * @param applicationDTO the entity to save
     * @return the persisted entity
     */
    ApplicationDTO update(ApplicationDTO applicationDTO);

    /**
     * Update Application Phase
     *
     * @param id : Application Id
     * @param phase : Active Phase
     *
     * @return the persisted entity
     */
    ApplicationDTO updatePhase(Long id, PhaseType phase);

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" application.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ApplicationDTO> findOne(Long id);

    /**
     * Get process instance application by information
     *
     * @param processInstanceId the Process instance id of the applicationDTO to retrieve
     * @return the application entity
     */
    Optional<ApplicationDTO> getApplicationByProcessInstanceId(Long processInstanceId);

    /**
     * Get process instance application by information
     *
     * @param processInstanceAppInfoRequestVM the Process instance application info
     * @return the application entity
     */
    Optional<ApplicationDTO> getProcessInstanceApplicationByInfo(ProcessInstanceAppInfoRequestVM processInstanceAppInfoRequestVM);

    /**
     * Get application by information
     *
     * @param processInstanceAppInfoRequestVM the Process instance application info
     * @return the applications list info
     */
    Map<String,Object> getApplicationsByInfo(ProcessInstanceAppInfoRequestVM processInstanceAppInfoRequestVM);
    
    /**
     * Delete the "id" application.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Getter for application by status and before passed status date
     *
     * @param applicationStatus
     * @param statusDate
     *
     * @return list of Application
     */
    List<ApplicationDTO> getByStatusAndStatusDateLessThan(ApplicationStatus applicationStatus, LocalDateTime statusDate);

    /**
     * Get the application by application type id , mobile number and birth date
     *
     * @param applicationTypeId application type id
     * @param mobileNo mobile number
     * @param birthDate birthdate
     * @return the entity
     */
    List<ApplicationDTO> findOne(Long applicationTypeId , String mobileNo , String birthDate);

    /**
     * Find application by emirates id and application type id criteria
     *
     * @param applicationTypeId application type id
     * @param eidNumber
     * @param eidExpiryDate
     *
     * Application which's fitted the passed criteria
     */
    Optional<ApplicationDTO> findOneByEmiratesId(Long applicationTypeId, String eidNumber, String eidExpiryDate);

    /**
     * Find application by application Type RefNo and user Profile RefNo criteria
     *
     * @param applicationTypeRefNo application type id
     * @param userProfileRefNo
     * Application which's fitted the passed criteria
     */
    Optional<ApplicationDTO> getByUserIdAndApplicationType(Long applicationTypeRefNo, Long userProfileRefNo);
}
