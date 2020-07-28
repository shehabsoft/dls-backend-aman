package ae.rta.dls.backend.service.sct;

import ae.rta.dls.backend.service.dto.sct.ApplicationTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ApplicationType.
 */
public interface ApplicationTypeService {

    /**
     * Save a applicationType.
     *
     * @param applicationTypeDTO the entity to save
     * @return the persisted entity
     */
    ApplicationTypeDTO save(ApplicationTypeDTO applicationTypeDTO);

    /**
     * Get all the applicationTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationTypeDTO> findAll(Pageable pageable);

    /**
     * Get list of Active Application Types.
     *
     * @return the list of Active Application Types
     */
    Page<ApplicationTypeDTO> getActiveApplicationTypes(Pageable pageable);

    /**
     * Get the "id" applicationType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ApplicationTypeDTO> findOne(Long id);

    /**
     * Get one applicationType by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<ApplicationTypeDTO> getByCode(Long code);

    /**
     * Deactivate the "id" applicationType.
     *
     * @param id the id of the entity
     */
    void deactivate(Long id);
}
