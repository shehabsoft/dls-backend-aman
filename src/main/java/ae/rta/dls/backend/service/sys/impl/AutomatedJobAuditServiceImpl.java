package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.domain.sys.AutomatedJobAudit;
import ae.rta.dls.backend.repository.sys.AutomatedJobAuditRepository;
import ae.rta.dls.backend.service.dto.sys.AutomatedJobAuditDTO;
import ae.rta.dls.backend.service.mapper.sys.AutomatedJobAuditMapper;
import ae.rta.dls.backend.service.sys.AutomatedJobAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing AutomatedJobAudit.
 */
@Service
@Transactional
public class AutomatedJobAuditServiceImpl implements AutomatedJobAuditService {

    private final Logger log = LoggerFactory.getLogger(AutomatedJobAuditServiceImpl.class);

    private final AutomatedJobAuditRepository automatedJobAuditRepository;

    private final AutomatedJobAuditMapper automatedJobAuditMapper;

    public AutomatedJobAuditServiceImpl(AutomatedJobAuditRepository automatedJobAuditRepository, AutomatedJobAuditMapper automatedJobAuditMapper) {
        this.automatedJobAuditRepository = automatedJobAuditRepository;
        this.automatedJobAuditMapper = automatedJobAuditMapper;
    }

    /**
     * Save a automatedJobAudit.
     *
     * @param automatedJobAuditDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AutomatedJobAuditDTO save(AutomatedJobAuditDTO automatedJobAuditDTO) {
        log.debug("Request to save AutomatedJobAudit : {}", automatedJobAuditDTO);
        AutomatedJobAudit automatedJobAudit = automatedJobAuditMapper.toEntity(automatedJobAuditDTO);
        automatedJobAudit = automatedJobAuditRepository.save(automatedJobAudit);
        return automatedJobAuditMapper.toDto(automatedJobAudit);
    }

    /**
     * Get all the automatedJobAudits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutomatedJobAuditDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutomatedJobAudits");
        return automatedJobAuditRepository.findAll(pageable)
            .map(automatedJobAuditMapper::toDto);
    }


    /**
     * Get one automatedJobAudit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AutomatedJobAuditDTO> findOne(Long id) {
        log.debug("Request to get AutomatedJobAudit : {}", id);
        return automatedJobAuditRepository.findById(id)
            .map(automatedJobAuditMapper::toDto);
    }

    /**
     * Delete the automatedJobAudit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AutomatedJobAudit : {}", id);
        automatedJobAuditRepository.deleteById(id);
    }
}
