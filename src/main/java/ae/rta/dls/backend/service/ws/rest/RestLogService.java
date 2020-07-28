package ae.rta.dls.backend.service.ws.rest;

import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RestLog.
 */
public interface RestLogService {

    /**
     * Save a restLog.
     *
     * @param restLogDTO the entity to save
     * @return the persisted entity
     */
    RestLogDTO save(RestLogDTO restLogDTO);

    /**
     * Asynchronous Save a restLog.
     *
     * @param restLogDTO the entity to save
     * @return the persisted entity
     */
    RestLogDTO asyncSave(RestLogDTO restLogDTO);

    /**
     * Get all the restLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RestLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" restLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RestLogDTO> findOne(Long id);

    /**
     * Delete the "id" restLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
