package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.sys.DomainValueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DomainValue.
 */
public interface DomainValueService {

    /**
     * Save a domainValue.
     *
     * @param domainValueDTO the entity to save
     * @return the persisted entity
     */
    DomainValueDTO save(DomainValueDTO domainValueDTO);

    /**
     * Get all the domainValues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DomainValueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" domainValue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DomainValueDTO> findOne(Long id);

    /**
     * Get one domainValue by Value and domain id.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the multilingual json type of the the given value
     */
    MultilingualJsonType getDomainValueDescription(String value, String domainCode);

    /**
     * Delete the "id" domainValue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get one Domain Value by Value and domain code.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the entity
     */
    MultilingualJsonType getDomainValue(String value, String domainCode);
}
