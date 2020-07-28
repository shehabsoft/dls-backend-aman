package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.service.dto.sys.DomainDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Domain.
 */
public interface DomainService {

    /**
     * Save a domain.
     *
     * @param domainDTO the entity to save
     * @return the persisted entity
     */
    DomainDTO save(DomainDTO domainDTO);

    /**
     * Update a domain.
     *
     * @param domainDTO the entity to save
     * @return the persisted entity
     */
    DomainDTO update(DomainDTO domainDTO);

    /**
     * Get all the domains.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DomainDTO> findAll(Pageable pageable);


    /**
     * Get the "id" domain.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DomainDTO> findOne(Long id);

    /**
     * Delete the "id" domain.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get domain entity by "code"
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<DomainDTO> findOne(String code);

    /**
     * Get domain entity by "code" with excluded values
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<DomainDTO> findOneWithExcludedValues(String code, List<String> excludedValues);
}
