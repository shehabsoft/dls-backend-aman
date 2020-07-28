package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.service.trn.ApplicationViolationService;
import ae.rta.dls.backend.domain.trn.ApplicationViolation;
import ae.rta.dls.backend.repository.trn.ApplicationViolationRepository;
import ae.rta.dls.backend.service.dto.trn.ApplicationViolationDTO;
import ae.rta.dls.backend.service.mapper.trn.ApplicationViolationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ApplicationViolation.
 */
@Service
@Transactional
public class ApplicationViolationServiceImpl implements ApplicationViolationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationViolationServiceImpl.class);

    private final ApplicationViolationRepository applicationViolationRepository;

    private final ApplicationViolationMapper applicationViolationMapper;

    public ApplicationViolationServiceImpl(ApplicationViolationRepository applicationViolationRepository, ApplicationViolationMapper applicationViolationMapper) {
        this.applicationViolationRepository = applicationViolationRepository;
        this.applicationViolationMapper = applicationViolationMapper;
    }

    /**
     * Save a applicationViolation.
     *
     * @param applicationViolationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationViolationDTO save(ApplicationViolationDTO applicationViolationDTO) {
        log.debug("Request to save ApplicationViolation : {}", applicationViolationDTO);
        ApplicationViolation applicationViolation = applicationViolationMapper.toEntity(applicationViolationDTO);
        applicationViolation = applicationViolationRepository.save(applicationViolation);
        return applicationViolationMapper.toDto(applicationViolation);
    }

    /**
     * Get all the applicationViolations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationViolationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationViolations");
        return applicationViolationRepository.findAll(pageable)
            .map(applicationViolationMapper::toDto);
    }


    /**
     * Get one applicationViolation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationViolationDTO> findOne(Long id) {
        log.debug("Request to get ApplicationViolation : {}", id);
        return applicationViolationRepository.findById(id)
            .map(applicationViolationMapper::toDto);
    }

    /**
     * Delete the applicationViolation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationViolation : {}", id);
        applicationViolationRepository.deleteById(id);
    }
}
