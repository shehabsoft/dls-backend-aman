package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.domain.sys.ErrorLog;
import ae.rta.dls.backend.repository.sys.ErrorLogRepository;
import ae.rta.dls.backend.service.dto.sys.ErrorLogDTO;
import ae.rta.dls.backend.service.mapper.sys.ErrorLogMapper;
import ae.rta.dls.backend.service.sys.ErrorLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ErrorLog.
 */
@Service
@Transactional
public class ErrorLogServiceImpl implements ErrorLogService {

    private final Logger log = LoggerFactory.getLogger(ErrorLogServiceImpl.class);

    private final ErrorLogRepository errorLogRepository;

    private final ErrorLogMapper errorLogMapper;

    public ErrorLogServiceImpl(ErrorLogRepository errorLogRepository,ErrorLogMapper errorLogMapper) {
        this.errorLogRepository = errorLogRepository;
        this.errorLogMapper = errorLogMapper;
    }

    /**
     * Save a errorLog.
     *
     * @param errorLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ErrorLogDTO save(ErrorLogDTO errorLogDTO) {
        try {
            log.debug("Request to save ErrorLog : {}", errorLogDTO);
            ErrorLog errorLog = errorLogMapper.toEntity(errorLogDTO);
            return errorLogMapper.toDto(errorLogRepository.save(errorLog));
        } catch(Exception t) {
            log.error("save Error Log Failed in ErrorLogServicecImpl.save");
            return new ErrorLogDTO();
        }
    }

    /**
     * Get all the errorLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ErrorLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ErrorLogs");
        return errorLogRepository.findAll(pageable).map(errorLogMapper::toDto);
    }


    /**
     * Get one errorLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ErrorLogDTO> findOne(Long id) {
        log.debug("Request to get ErrorLog : {}", id);
        return errorLogRepository.findById(id).map(errorLogMapper::toDto);
    }

    /**
     * Delete the errorLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ErrorLog : {}", id);
        errorLogRepository.deleteById(id);
    }
}
