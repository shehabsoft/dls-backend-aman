package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.domain.sys.EntityAuditConfiguration;

import ae.rta.dls.backend.service.dto.sys.EntityAuditConfigurationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EntityAuditConfiguration.
 */
public interface EntityAuditConfigurationService {

    /**
     * Save a entityAuditConfiguration.
     *
     * @param entityAuditConfigurationDTO the entity to save
     * @return the persisted entity
     */
    EntityAuditConfigurationDTO save(EntityAuditConfigurationDTO entityAuditConfigurationDTO);

    /**
     * Get all the entityAuditConfigurations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EntityAuditConfigurationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" entityAuditConfiguration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EntityAuditConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" entityAuditConfiguration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the "entityAuditConfiguration" entity by "entityName".
     *
     * @param entityName the id of the entity
     * @return the entity
     */
    Optional<EntityAuditConfigurationDTO> findByEntityName(String entityName);

}
