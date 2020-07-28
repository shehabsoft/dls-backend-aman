package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.service.dto.sys.EntityAuditConfigurationDTO;
import ae.rta.dls.backend.service.mapper.sys.EntityAuditConfigurationMapper;
import ae.rta.dls.backend.service.sys.EntityAuditConfigurationService;
import ae.rta.dls.backend.domain.sys.EntityAuditConfiguration;
import ae.rta.dls.backend.repository.sys.EntityAuditConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EntityAuditConfiguration.
 */
@Service
@Transactional
public class EntityAuditConfigurationServiceImpl implements EntityAuditConfigurationService {

    private final Logger log = LoggerFactory.getLogger(EntityAuditConfigurationServiceImpl.class);

    private final EntityAuditConfigurationRepository entityAuditConfigurationRepository;

    private final EntityAuditConfigurationMapper entityAuditConfigurationMapper;

    public EntityAuditConfigurationServiceImpl(EntityAuditConfigurationRepository entityAuditConfigurationRepository ,EntityAuditConfigurationMapper entityAuditConfigurationMapper) {
        this.entityAuditConfigurationRepository = entityAuditConfigurationRepository;
        this.entityAuditConfigurationMapper = entityAuditConfigurationMapper;
    }

    /**
     * Save a entityAuditConfiguration.
     *
     * @param entityAuditConfigurationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntityAuditConfigurationDTO save(EntityAuditConfigurationDTO entityAuditConfigurationDTO) {
        log.debug("Request to save EntityAuditConfiguration : {}", entityAuditConfigurationDTO);
        EntityAuditConfiguration entityAuditConfiguration = entityAuditConfigurationMapper.toEntity(entityAuditConfigurationDTO);

        return entityAuditConfigurationMapper.toDto(entityAuditConfigurationRepository.save(entityAuditConfiguration));
    }

    /**
     * Get all the entityAuditConfigurations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntityAuditConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EntityAuditConfigurations");
        return entityAuditConfigurationRepository.findAll(pageable).map(entityAuditConfigurationMapper::toDto);
    }


    /**
     * Get one entityAuditConfiguration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EntityAuditConfigurationDTO> findOne(Long id) {
        log.debug("Request to get EntityAuditConfiguration : {}", id);
        return entityAuditConfigurationRepository.findById(id).map(entityAuditConfigurationMapper::toDto);
    }

    /**
     * Delete the entityAuditConfiguration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntityAuditConfiguration : {}", id);
        entityAuditConfigurationRepository.deleteById(id);
    }

    /**
     * Get the "entityAuditConfiguration" entity by "entityName".
     *
     * @param entityName the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<EntityAuditConfigurationDTO> findByEntityName(String entityName) {
        log.debug("Request to get EntityAuditConfiguration by entity name : {}", entityName);
        return entityAuditConfigurationRepository.findByEntityName(entityName).map(entityAuditConfigurationMapper::toDto);
    }
}
