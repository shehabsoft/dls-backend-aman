package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.sys.DomainDTO;
import ae.rta.dls.backend.service.sys.DomainService;
import ae.rta.dls.backend.service.sys.DomainValueService;
import ae.rta.dls.backend.domain.sys.DomainValue;
import ae.rta.dls.backend.repository.sys.DomainValueRepository;
import ae.rta.dls.backend.service.dto.sys.DomainValueDTO;
import ae.rta.dls.backend.service.mapper.sys.DomainValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DomainValue.
 */
@Service
@Transactional
public class DomainValueServiceImpl implements DomainValueService {

    private final Logger log = LoggerFactory.getLogger(DomainValueServiceImpl.class);

    private final DomainValueRepository domainValueRepository;

    private final DomainService domainService;

    private final DomainValueMapper domainValueMapper;

    private final CacheManager cacheManager;

    public DomainValueServiceImpl(DomainValueRepository domainValueRepository, DomainService domainService, DomainValueMapper domainValueMapper, CacheManager cacheManager) {
        this.domainValueRepository = domainValueRepository;
        this.domainService = domainService;
        this.domainValueMapper = domainValueMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a domainValue.
     *
     * @param domainValueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DomainValueDTO save(DomainValueDTO domainValueDTO) {
        log.debug("Request to save DomainValue : {}", domainValueDTO);

        evictAll();

        DomainValue domainValue = domainValueMapper.toEntity(domainValueDTO);
        domainValue = domainValueRepository.save(domainValue);
        return domainValueMapper.toDto(domainValue);
    }

    /**
     * Get all the domainValues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DomainValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DomainValues");
        return domainValueRepository.findAll(pageable)
            .map(domainValueMapper::toDto);
    }

    /**
     * Get one domainValue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DomainValueDTO> findOne(Long id) {
        log.debug("Request to get DomainValue : {}", id);
        return domainValueRepository.findById(id)
            .map(domainValueMapper::toDto);
    }

    /**
     * Get one domainValue by Value and domain id.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the multilingual json type of the the given value
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public MultilingualJsonType getDomainValueDescription(String value,String domainCode) {
        log.debug("Request to get DomainValue : {} / domainCode : {}", value,domainCode);
        Optional<DomainDTO> domain = domainService.findOne(domainCode);

        if(!domain.isPresent()) {
            return null;
        }
        Optional<DomainValueDTO> domainValue = domainValueRepository.findByValueAndDomain_Id(value,domain.get().getId())
                .map(domainValueMapper::toDto);

        if(!domainValue.isPresent()) {
            return null;
        }
        return domainValue.get().getDescription();
    }

    /**
     * Delete the domainValue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DomainValue : {}", id);
        evictAll();
        domainValueRepository.deleteById(id);
    }

    /**
     * Evict All Related Caches
     */
    public void evictAll() {
        cacheManager.getCache(DomainValueRepository.DomainValueCache.DOMAIN_VALUE_BY_DOMAIN_ID).clear();
    }

    /**
     * Get one Domain Value by Value and domain code.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MultilingualJsonType getDomainValue(String value,String domainCode) {

        Optional<DomainDTO> domain = domainService.findOne(domainCode);

        if(!domain.isPresent()) {
            return null;
        }
        Optional<DomainValueDTO> domainValue = domainValueRepository.findByValueAndDomain_Id(value,domain.get().getId())
            .map(domainValueMapper::toDto);

        if(!domainValue.isPresent()) {
            return null;
        }

        return domainValue.get().getDescription();
    }
}
