package ae.rta.dls.backend.service.trn;

import ae.rta.dls.backend.service.dto.trn.ApplicationViolationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ApplicationViolation.
 */
public interface ApplicationViolationService {

    /**
     * Save a applicationViolation.
     *
     * @param applicationViolationDTO the entity to save
     * @return the persisted entity
     */
    ApplicationViolationDTO save(ApplicationViolationDTO applicationViolationDTO);

    /**
     * Get all the applicationViolations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationViolationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" applicationViolation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ApplicationViolationDTO> findOne(Long id);

    /**
     * Delete the "id" applicationViolation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
