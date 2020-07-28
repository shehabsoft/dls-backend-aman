package ae.rta.dls.backend.service.sct;

import ae.rta.dls.backend.service.dto.sct.ServiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Service.
 */
public interface ServiceService {

    /**
     * Save a service.
     *
     * @param serviceDTO the entity to save
     * @return the persisted entity
     */
    ServiceDTO save(ServiceDTO serviceDTO);

    /**
     * Get all the services.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceDTO> findAll(Pageable pageable);

    /**
     * Get all Active Services.
     *
     * @param pageable the pagination information
     * @return the list of active service entities
     */
    Page<ServiceDTO> findActiveServices(Pageable pageable);

    /**
     * Get the "id" service.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceDTO> findOne(Long id);

    /**
     * Deactivate the "id" service.
     *
     * @param id the id of the entity
     */
    void deactivate(Long id);

    /**
     * Get Service by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<ServiceDTO> findByCode(String code);
}
