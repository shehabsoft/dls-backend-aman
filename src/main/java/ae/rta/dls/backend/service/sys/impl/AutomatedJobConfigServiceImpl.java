package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.domain.enumeration.sys.JobStatus;
import ae.rta.dls.backend.domain.sys.AutomatedJobConfig;
import ae.rta.dls.backend.repository.sys.AutomatedJobConfigRepository;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobConfigDTO;
import ae.rta.dls.backend.service.mapper.sys.AutomatedJobConfigMapper;
import ae.rta.dls.backend.service.sys.AutomatedJobConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing AutomatedJobConfig.
 */
@Service
@Transactional
public class AutomatedJobConfigServiceImpl implements AutomatedJobConfigService {

    private final Logger log = LoggerFactory.getLogger(AutomatedJobConfigServiceImpl.class);

    private final AutomatedJobConfigRepository automatedJobConfigRepository;

    private final AutomatedJobConfigMapper automatedJobConfigMapper;

    public AutomatedJobConfigServiceImpl(AutomatedJobConfigRepository automatedJobConfigRepository, AutomatedJobConfigMapper automatedJobConfigMapper) {
        this.automatedJobConfigRepository = automatedJobConfigRepository;
        this.automatedJobConfigMapper = automatedJobConfigMapper;
    }

    /**
     * Save a automatedJobConfig.
     *
     * @param automatedJobConfigDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AutomatedJobConfigDTO save(AutomatedJobConfigDTO automatedJobConfigDTO) {
        log.debug("Request to save AutomatedJobConfig : {}", automatedJobConfigDTO);
        AutomatedJobConfig automatedJobConfig = automatedJobConfigMapper.toEntity(automatedJobConfigDTO);
        automatedJobConfig = automatedJobConfigRepository.save(automatedJobConfig);
        return automatedJobConfigMapper.toDto(automatedJobConfig);
    }

    /**
     * Get all the automatedJobConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutomatedJobConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutomatedJobConfigs");
        return automatedJobConfigRepository.findAll(pageable)
                .map(automatedJobConfigMapper::toDto);
    }

    /**
     * Get Active Automated Job Configs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutomatedJobConfigDTO> getActiveJobConfigs(Pageable pageable) {
        log.debug("Request to get all AutomatedJobConfigs");
        return automatedJobConfigRepository.getByStatusIsNot(JobStatus.INACTIVE,pageable)
                .map(automatedJobConfigMapper::toDto);
    }

    /**
     * Get one automatedJobConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AutomatedJobConfigDTO> findOne(Long id) {
        log.debug("Request to get AutomatedJobConfig : {}", id);
        return automatedJobConfigRepository.findById(id)
            .map(automatedJobConfigMapper::toDto);
    }

    /**
     * Deactivate the automatedJobConfig by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deactivate(Long id) {
        log.debug("Request to delete AutomatedJobConfig : {}", id);
        Optional<AutomatedJobConfig> automatedJobConfig = automatedJobConfigRepository.findById(id);
        if(!automatedJobConfig.isPresent()) {
            throw new SystemException("invalid Id passed  : {}", id);
        }
        automatedJobConfig.get().setStatus(JobStatus.INACTIVE);

        automatedJobConfigRepository.save(automatedJobConfig.get());
    }

    /**
     * Get the automatedJobConfig by jobName
     *
     * @param jobName the name of the job
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AutomatedJobConfigDTO> findByJobName(String jobName) {
        log.debug("Request to find AutomatedJobConfig by job name : {}", jobName);
        return automatedJobConfigRepository.findByJobName(jobName).map(automatedJobConfigMapper::toDto);
    }

    /**
     * Get Active Automated Job Config List.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AutomatedJobConfigDTO> getActiveJobConfigList() {
        log.debug("Request to get list of active Automated Job Config List");
        return automatedJobConfigMapper.toDto(automatedJobConfigRepository.getByStatusIs(JobStatus.ACTIVE));
    }
}
