package ae.rta.dls.backend.service.prd;

import ae.rta.dls.backend.domain.prd.Handbook;
import ae.rta.dls.backend.service.dto.prd.HandbookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Handbook}.
 */
public interface HandbookService {

    /**
     * Save a handbook.
     *
     * @param handbookDTO the entity to save.
     * @return the persisted entity.
     */
    HandbookDTO save(HandbookDTO handbookDTO);

    /**
     * Get all the handbooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HandbookDTO> findAll(Pageable pageable);


    /**
     * Get the "id" handbook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HandbookDTO> findOne(Long id);

    /**
     * Find by Traffic Code No
     *
     * @param trafficCodeNo Traffic Code No
     * @return the entity
     */
    Optional<HandbookDTO> findByTrafficCodeNo(Long trafficCodeNo);

    /**
     * Delete the "id" handbook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
