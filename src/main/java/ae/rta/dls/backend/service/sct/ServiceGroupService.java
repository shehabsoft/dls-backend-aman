package ae.rta.dls.backend.service.sct;

import ae.rta.dls.backend.service.dto.sct.ServiceGroupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ServiceGroup.
 */
public interface ServiceGroupService {

    /**
     * Save a serviceGroup.
     *
     * @param serviceGroupDTO the entity to save
     * @return the persisted entity
     */
    ServiceGroupDTO save(ServiceGroupDTO serviceGroupDTO);

    /**
     * Get all the serviceGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceGroupDTO> findAll(Pageable pageable);

    /**
     * Get Active Service Groups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceGroupDTO> findActiveServiceGroup(Pageable pageable);

    /**
     * Get the "id" serviceGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceGroupDTO> findOne(Long id);

    /**
     * Deactivate the "id" serviceGroup.
     *
     * @param id the id of the entity
     */
    void deactivate(Long id);
}
