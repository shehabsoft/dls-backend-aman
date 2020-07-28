package ae.rta.dls.backend.service.ws.rest.impl;

import ae.rta.dls.backend.domain.ws.rest.RestLog;
import ae.rta.dls.backend.repository.ws.rest.RestLogRepository;
import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import ae.rta.dls.backend.service.mapper.ws.rest.RestLogMapper;
import ae.rta.dls.backend.service.ws.rest.RestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RestLog.
 */
@Service
@Transactional
public class RestLogServiceImpl implements RestLogService {

    private final Logger log = LoggerFactory.getLogger(RestLogServiceImpl.class);

    private final RestLogRepository restLogRepository;

    private final RestLogMapper restLogMapper;

    public RestLogServiceImpl(RestLogRepository restLogRepository, RestLogMapper restLogMapper) {
        this.restLogRepository = restLogRepository;
        this.restLogMapper = restLogMapper;
    }

    /**
     * Save a restLog.
     *
     * @param restLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RestLogDTO save(RestLogDTO restLogDTO) {
        log.debug("Request to save RestLog : {}", restLogDTO);

        RestLog restLog = restLogMapper.toEntity(restLogDTO);
        restLog = restLogRepository.save(restLog);
        return restLogMapper.toDto(restLog);
    }

    /**
     * Asynchronous Save a restLog.
     *
     * @param restLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    @Async
    public RestLogDTO asyncSave(RestLogDTO restLogDTO) {
        log.debug("Request to asyncSave RestLog : {}", restLogDTO);

        RestLog restLog = restLogMapper.toEntity(restLogDTO);
        restLog = restLogRepository.save(restLog);
        return restLogMapper.toDto(restLog);
    }

    /**
     * Get all the restLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RestLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RestLogs");
        return restLogRepository.findAll(pageable)
            .map(restLogMapper::toDto);
    }


    /**
     * Get one restLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RestLogDTO> findOne(Long id) {
        log.debug("Request to get RestLog : {}", id);
        return restLogRepository.findById(id)
            .map(restLogMapper::toDto);
    }

    /**
     * Delete the restLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RestLog : {}", id);
        restLogRepository.deleteById(id);
    }
}
