package ae.rta.dls.backend.service.sct.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.domain.enumeration.trn.ApplicationTypeStatus;
import ae.rta.dls.backend.service.sct.ApplicationTypeService;
import ae.rta.dls.backend.domain.sct.ApplicationType;
import ae.rta.dls.backend.repository.sct.ApplicationTypeRepository;
import ae.rta.dls.backend.service.dto.sct.ApplicationTypeDTO;
import ae.rta.dls.backend.service.mapper.sct.ApplicationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing ApplicationType.
 */
@Service
@Transactional
public class ApplicationTypeServiceImpl implements ApplicationTypeService {

    private final Logger log = LoggerFactory.getLogger(ApplicationTypeServiceImpl.class);

    private final ApplicationTypeRepository applicationTypeRepository;

    private final ApplicationTypeMapper applicationTypeMapper;

    private final CacheManager cacheManager;

    public ApplicationTypeServiceImpl(ApplicationTypeRepository applicationTypeRepository, ApplicationTypeMapper applicationTypeMapper,CacheManager cacheManager) {
        this.applicationTypeRepository = applicationTypeRepository;
        this.applicationTypeMapper = applicationTypeMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a applicationType.
     *
     * @param applicationTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplicationTypeDTO save(ApplicationTypeDTO applicationTypeDTO) {
        log.debug("Request to save ApplicationType : {}", applicationTypeDTO);

        evictAll();

        ApplicationType applicationType = applicationTypeMapper.toEntity(applicationTypeDTO);
        applicationType = applicationTypeRepository.save(applicationType);
        return applicationTypeMapper.toDto(applicationType);
    }

    /**
     * Get all the applicationTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationTypes");
        return applicationTypeRepository.findAll(pageable)
            .map(applicationTypeMapper::toDto);
    }

    /**
     * Get list of Active Applications.
     *
     * @return the list of Active Application Types
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ApplicationTypeRepository.ApplicationTypeCache.GET_ACTIVE_APPLICATION_TYPES)
    public Page<ApplicationTypeDTO> getActiveApplicationTypes(Pageable pageable) {
        log.debug("Request to get Active Applications");

        pageable = PageRequest.of(pageable.getPageNumber(),
                                  pageable.getPageSize(),
                                  new Sort(Sort.Direction.ASC, "sortOrder"));

        return applicationTypeRepository.getApplicationTypeByStatusIsNot(ApplicationTypeStatus.INACTIVE,pageable)
                .map(applicationTypeMapper::toDto);
    }

    /**
     * Get one applicationType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationTypeDTO> findOne(Long id) {
        log.debug("Request to get ApplicationType : {}", id);
        return applicationTypeRepository.findById(id)
            .map(applicationTypeMapper::toDto);
    }

    /**
     * Get one applicationType by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationTypeDTO> getByCode(Long code) {
        log.debug("Request to get ApplicationType By COde: {}", code);
        return applicationTypeRepository.getByCode(code)
                .map(applicationTypeMapper::toDto);
    }

    /**
     * Deactivate the applicationType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deactivate(Long id) {
        log.debug("Request to delete ApplicationType : {}", id);
        Optional<ApplicationType> applicationType = applicationTypeRepository.findById(id);
        if(!applicationType.isPresent()) {
            throw new SystemException("invalid Id passed  : {}", id);
        }
        applicationType.get().setStatus(ApplicationTypeStatus.INACTIVE);

        applicationTypeRepository.save(applicationType.get());

        // Evict All Related Caches..
        evictAll();
    }

    public void evictAll() {
        Objects.requireNonNull(
                cacheManager.getCache(ApplicationTypeRepository.ApplicationTypeCache.GET_ACTIVE_APPLICATION_TYPES))
                .clear();
    }
}
