package ae.rta.dls.backend.service.ws.rest.impl;

import ae.rta.dls.backend.service.ws.rest.BrmRestLogService;
import ae.rta.dls.backend.domain.ws.rest.BrmRestLog;
import ae.rta.dls.backend.repository.ws.rest.BrmRestLogRepository;
import ae.rta.dls.backend.service.dto.ws.rest.BrmRestLogDTO;
import ae.rta.dls.backend.service.mapper.ws.rest.BrmRestLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BrmRestLog.
 */
@Service
@Transactional
public class BrmRestLogServiceImpl implements BrmRestLogService {

    private final Logger log = LoggerFactory.getLogger(BrmRestLogServiceImpl.class);

    private final BrmRestLogRepository brmRestLogRepository;

    private final BrmRestLogMapper brmRestLogMapper;

    public BrmRestLogServiceImpl(BrmRestLogRepository brmRestLogRepository, BrmRestLogMapper brmRestLogMapper) {
        this.brmRestLogRepository = brmRestLogRepository;
        this.brmRestLogMapper = brmRestLogMapper;
    }

    /**
     * Save a brmRestLog.
     *
     * @param brmRestLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BrmRestLogDTO save(BrmRestLogDTO brmRestLogDTO) {
        log.debug("Request to save BrmRestLog : {}", brmRestLogDTO);

        BrmRestLog brmRestLog = brmRestLogMapper.toEntity(brmRestLogDTO);
        brmRestLog = brmRestLogRepository.save(brmRestLog);
        return brmRestLogMapper.toDto(brmRestLog);
    }

    /**
     * Async Save a brmRestLog.
     *
     * @param brmRestLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    @Async
    public BrmRestLogDTO asyncSave(BrmRestLogDTO brmRestLogDTO) {
        log.debug("Request to Async Save BrmRestLog : {}", brmRestLogDTO);

        BrmRestLog brmRestLog = brmRestLogMapper.toEntity(brmRestLogDTO);
        brmRestLog = brmRestLogRepository.save(brmRestLog);
        return brmRestLogMapper.toDto(brmRestLog);
    }

    /**
     * Get all the brmRestLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrmRestLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BrmRestLogs");
        return brmRestLogRepository.findAll(pageable)
            .map(brmRestLogMapper::toDto);
    }


    /**
     * Get one brmRestLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BrmRestLogDTO> findOne(Long id) {
        log.debug("Request to get BrmRestLog : {}", id);
        return brmRestLogRepository.findById(id)
            .map(brmRestLogMapper::toDto);
    }

    /**
     * Delete the brmRestLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BrmRestLog : {}", id);
        brmRestLogRepository.deleteById(id);
    }
}
