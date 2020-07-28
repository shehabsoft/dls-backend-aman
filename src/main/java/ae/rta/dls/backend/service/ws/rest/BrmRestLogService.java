package ae.rta.dls.backend.service.ws.rest;

import ae.rta.dls.backend.service.dto.ws.rest.BrmRestLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BrmRestLog.
 */
public interface BrmRestLogService {

    /**
     * Save a brmRestLog.
     *
     * @param brmRestLogDTO the entity to save
     * @return the persisted entity
     */
    BrmRestLogDTO save(BrmRestLogDTO brmRestLogDTO);

    /**
     * Async Save a brmRestLog.
     *
     * @param brmRestLogDTO the entity to save
     * @return the persisted entity
     */
    BrmRestLogDTO asyncSave(BrmRestLogDTO brmRestLogDTO);

    /**
     * Get all the brmRestLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BrmRestLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" brmRestLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BrmRestLogDTO> findOne(Long id);

    /**
     * Delete the "id" brmRestLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
