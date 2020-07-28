package ae.rta.dls.backend.service.trn;

import ae.rta.dls.backend.service.dto.trn.ApplicationPhaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ApplicationPhase.
 */
public interface ApplicationPhaseService {

    /**
     * Save a applicationPhase.
     *
     * @param applicationPhaseDTO the entity to save
     * @return the persisted entity
     */
    ApplicationPhaseDTO save(ApplicationPhaseDTO applicationPhaseDTO);

    /**
     * Get all the applicationPhases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationPhaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" applicationPhase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ApplicationPhaseDTO> findOne(Long id);

    /**
     * Delete the "id" applicationPhase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get First Record of Application Phase By Application Id Order By PhaseSequence Descending
     * @param applicationId : Application Id
     *
     * @return Application Phase Entity
     */
    ApplicationPhaseDTO getFirstByApplicationIdOrderByPhaseSequenceDesc(Long applicationId);
}
