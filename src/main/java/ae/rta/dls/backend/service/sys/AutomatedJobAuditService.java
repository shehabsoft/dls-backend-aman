package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.service.dto.sys.AutomatedJobAuditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AutomatedJobAudit.
 */
public interface AutomatedJobAuditService {

    /**
     * Save a automatedJobAudit.
     *
     * @param automatedJobAuditDTO the entity to save
     * @return the persisted entity
     */
    AutomatedJobAuditDTO save(AutomatedJobAuditDTO automatedJobAuditDTO);

    /**
     * Get all the automatedJobAudits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AutomatedJobAuditDTO> findAll(Pageable pageable);


    /**
     * Get the "id" automatedJobAudit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AutomatedJobAuditDTO> findOne(Long id);

    /**
     * Delete the "id" automatedJobAudit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
