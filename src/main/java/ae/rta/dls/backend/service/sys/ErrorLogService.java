package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.service.dto.sys.ErrorLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service Interface for managing ErrorLog.
 */
public interface ErrorLogService {

    /**
     * Save a errorLog.
     *
     * @param errorLogDTO the entity to save
     * @return the persisted entity
     */
    ErrorLogDTO save(ErrorLogDTO errorLogDTO);

    /**
     * Get all the errorLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ErrorLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" errorLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ErrorLogDTO> findOne(Long id);

    /**
     * Delete the "id" errorLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
