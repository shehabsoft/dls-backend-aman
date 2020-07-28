package ae.rta.dls.backend.service.prd;

import ae.rta.dls.backend.service.dto.prd.BusinessProfileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BusinessProfile.
 */
public interface BusinessProfileService {

    /**
     * Save a businessProfile.
     *
     * @param businessProfileDTO the entity to save
     * @return the persisted entity
     */
    BusinessProfileDTO save(BusinessProfileDTO businessProfileDTO);

    /**
     * Get all the businessProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessProfileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" businessProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessProfileDTO> findOne(Long id);

    /**
     * Get by traffic code number.
     *
     * @param trafficCodeNo
     * @return the entity
     */
    Optional<BusinessProfileDTO> findByTrafficCodeNo(Long trafficCodeNo);

    /**
     * Delete the "id" businessProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
