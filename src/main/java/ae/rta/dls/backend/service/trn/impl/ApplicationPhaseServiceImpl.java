package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.domain.trn.ApplicationPhase;
import ae.rta.dls.backend.repository.trn.ApplicationPhaseRepository;
import ae.rta.dls.backend.service.dto.trn.ApplicationPhaseDTO;
import ae.rta.dls.backend.service.mapper.trn.ApplicationPhaseMapper;
import ae.rta.dls.backend.service.trn.ApplicationPhaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ApplicationPhase.
 */
@Service
@Transactional
public class ApplicationPhaseServiceImpl implements ApplicationPhaseService {

    private final Logger log = LoggerFactory.getLogger(ApplicationPhaseServiceImpl.class);

    private final ApplicationPhaseRepository applicationPhaseRepository;

    private final ApplicationPhaseMapper applicationPhaseMapper;

    public ApplicationPhaseServiceImpl(ApplicationPhaseRepository applicationPhaseRepository, ApplicationPhaseMapper applicationPhaseMapper) {
        this.applicationPhaseRepository = applicationPhaseRepository;
        this.applicationPhaseMapper = applicationPhaseMapper;
    }

    /**
     * Save a applicationPhase.
     *
     * @param applicationPhaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationPhaseDTO save(ApplicationPhaseDTO applicationPhaseDTO) {
        log.debug("Request to save ApplicationPhase : {}", applicationPhaseDTO);
        ApplicationPhase applicationPhase = applicationPhaseMapper.toEntity(applicationPhaseDTO);
        applicationPhase = applicationPhaseRepository.save(applicationPhase);
        return applicationPhaseMapper.toDto(applicationPhase);
    }

    /**
     * Get all the applicationPhases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationPhaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationPhases");
        return applicationPhaseRepository.findAll(pageable)
            .map(applicationPhaseMapper::toDto);
    }


    /**
     * Get one applicationPhase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationPhaseDTO> findOne(Long id) {
        log.debug("Request to get ApplicationPhase : {}", id);
        return applicationPhaseRepository.findById(id)
            .map(applicationPhaseMapper::toDto);
    }

    /**
     * Delete the applicationPhase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationPhase : {}", id);
        applicationPhaseRepository.deleteById(id);
    }

    /**
     * Get First Record of Application Phase By Application Id Order By PhaseSequence Descending
     * @param applicationId : Application Id
     *
     * @return Application Phase Entity
     */
    public ApplicationPhaseDTO getFirstByApplicationIdOrderByPhaseSequenceDesc(Long applicationId) {
        log.debug("Request to get application phase by application and phase type");
        if (applicationId == null) {
            throw new SystemException("application Id cannot be null");
        }

        return applicationPhaseMapper.toDto(applicationPhaseRepository.getFirstByApplication_IdOrderByPhaseSequenceDesc(applicationId));

    }
}
