package ae.rta.dls.backend.service.sct.impl;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceGroupStatus;
import ae.rta.dls.backend.service.sct.ServiceGroupService;
import ae.rta.dls.backend.domain.sct.ServiceGroup;
import ae.rta.dls.backend.repository.sct.ServiceGroupRepository;
import ae.rta.dls.backend.service.dto.sct.ServiceGroupDTO;
import ae.rta.dls.backend.service.mapper.sct.ServiceGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ServiceGroup.
 */
@Service
@Transactional
public class ServiceGroupServiceImpl implements ServiceGroupService {

    private final Logger log = LoggerFactory.getLogger(ServiceGroupServiceImpl.class);

    private final ServiceGroupRepository serviceGroupRepository;

    private final ServiceGroupMapper serviceGroupMapper;

    public ServiceGroupServiceImpl(ServiceGroupRepository serviceGroupRepository, ServiceGroupMapper serviceGroupMapper) {
        this.serviceGroupRepository = serviceGroupRepository;
        this.serviceGroupMapper = serviceGroupMapper;
    }

    /**
     * Save a serviceGroup.
     *
     * @param serviceGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceGroupDTO save(ServiceGroupDTO serviceGroupDTO) {
        log.debug("Request to save ServiceGroup : {}", serviceGroupDTO);

        ServiceGroup serviceGroup = serviceGroupMapper.toEntity(serviceGroupDTO);
        serviceGroup = serviceGroupRepository.save(serviceGroup);
        return serviceGroupMapper.toDto(serviceGroup);
    }

    /**
     * Get all the serviceGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceGroups");
        return serviceGroupRepository.findAll(pageable)
                .map(serviceGroupMapper::toDto);
    }

    /**
     * Get all the serviceGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceGroupDTO> findActiveServiceGroup(Pageable pageable) {
        log.debug("Request to get all ServiceGroups");
        return serviceGroupRepository.findByStatusIsNot(ServiceGroupStatus.INACTIVE,pageable)
                .map(serviceGroupMapper::toDto);
    }

    /**
     * Get one serviceGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceGroupDTO> findOne(Long id) {
        log.debug("Request to get ServiceGroup : {}", id);
        return serviceGroupRepository.findById(id)
            .map(serviceGroupMapper::toDto);
    }

    /**
     * Deactivate the serviceGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void deactivate(Long id) {
        log.debug("Request to delete ServiceGroup : {}", id);
        Optional<ServiceGroup> serviceGroup = serviceGroupRepository.findById(id);
        if(!serviceGroup.isPresent()) {
            throw new SystemException("invalid Id passed  : {}", id);
        }
        serviceGroup.get().setStatus(ServiceGroupStatus.INACTIVE);

        serviceGroupRepository.save(serviceGroup.get());
    }
}
