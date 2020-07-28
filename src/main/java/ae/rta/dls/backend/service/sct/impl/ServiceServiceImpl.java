package ae.rta.dls.backend.service.sct.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceStatus;
import ae.rta.dls.backend.service.sct.ServiceService;
import ae.rta.dls.backend.domain.sct.Service;
import ae.rta.dls.backend.repository.sct.ServiceRepository;
import ae.rta.dls.backend.service.dto.sct.ServiceDTO;
import ae.rta.dls.backend.service.mapper.sct.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Service.
 */
@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final Logger log = LoggerFactory.getLogger(ServiceServiceImpl.class);

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    private final CacheManager cacheManager;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper,CacheManager cacheManager) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a service.
     *
     * @param serviceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceDTO save(ServiceDTO serviceDTO) {
        log.debug("Request to save Service : {}", serviceDTO);

        Service service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return serviceRepository.findAll(pageable)
                .map(serviceMapper::toDto);
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ServiceRepository.ServiceCache.GET_ACTIVE_SERVICES)
    public Page<ServiceDTO> findActiveServices(Pageable pageable) {
        log.debug("Request to get all Services");
        return serviceRepository.getServiceByStatusIsNot(ServiceStatus.INACTIVE,pageable)
                .map(serviceMapper::toDto);
    }

    /**
     * Get one service by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceDTO> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id)
            .map(serviceMapper::toDto);
    }

    /**
     * Deactivate the service by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deactivate(Long id) {
        log.debug("Request to delete Service : {}", id);
        Optional<Service> service = serviceRepository.findById(id);
        if(!service.isPresent()) {
            throw new SystemException("invalid Id passed  : {}", id);
        }
        service.get().setStatus(ServiceStatus.INACTIVE);

        serviceRepository.save(service.get());

        // Evict All Related Caches..
        evictAll();
    }

    /**
     * Get Service by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceDTO> findByCode(String code) {
        return serviceRepository.findByCodeAndStatus(code, ServiceStatus.ACTIVE).map(serviceMapper::toDto);
    }

    /**
     * Evect all active services.
     */
    public void evictAll() {
        cacheManager.getCache(ServiceRepository.ServiceCache.GET_ACTIVE_SERVICES).clear();
        cacheManager.getCache(ServiceRepository.ServiceCache.GET_SERVICE_BY_CODE).clear();
    }
}
