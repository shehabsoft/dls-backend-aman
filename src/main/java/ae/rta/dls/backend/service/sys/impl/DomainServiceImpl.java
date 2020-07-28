package ae.rta.dls.backend.service.sys.impl;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.service.dto.sys.DomainValueDTO;
import ae.rta.dls.backend.service.sys.DomainService;
import ae.rta.dls.backend.domain.sys.Domain;
import ae.rta.dls.backend.repository.sys.DomainRepository;
import ae.rta.dls.backend.service.dto.sys.DomainDTO;
import ae.rta.dls.backend.service.mapper.sys.DomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing Domain.
 */
@Service
@Transactional
public class DomainServiceImpl implements DomainService {

    private final Logger log = LoggerFactory.getLogger(DomainServiceImpl.class);

    private final DomainRepository domainRepository;

    private final DomainMapper domainMapper;

    private final CacheManager cacheManager;

    public DomainServiceImpl(DomainRepository domainRepository, DomainMapper domainMapper, CacheManager cacheManager) {
        this.domainRepository = domainRepository;
        this.domainMapper = domainMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a domain.
     *
     * @param domainDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DomainDTO save(DomainDTO domainDTO) {
        log.debug("Request to save Domain : {}", domainDTO);
        Domain domain = domainMapper.toEntity(domainDTO);
        domain = domainRepository.save(domain);
        return domainMapper.toDto(domain);
    }

    /**
     * Update a domain.
     *
     * @param domainDTO the entity to save
     * @return the persisted entity
     */
    public DomainDTO update(DomainDTO domainDTO) {
        log.debug("Request to save Domain : {}", domainDTO);
        Domain domain = domainMapper.toEntity(domainDTO);
        domainRepository.save(domain);

        Objects.requireNonNull(cacheManager.getCache(DomainRepository.DomainCache.DOMAIN_BY_CODE)).evict(domainDTO.getCode());

        Optional<DomainDTO> updatedDomainDTO = findOne(domainDTO.getCode());

        return updatedDomainDTO.isPresent() ? updatedDomainDTO.get() : null;
    }

    /**
     * Get all the domains.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Domains");
        return domainRepository.findAll(pageable)
            .map(domainMapper::toDto);
    }

    /**
     * Get one domain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DomainDTO> findOne(Long id) {
        log.debug("Request to get Domain by id : {}", id);
        return domainRepository.findById(id)
            .map(domainMapper::toDto);
    }

    /**
     * Delete the domain by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Domain : {}", id);

        Optional<DomainDTO> domainDTO = findOne(id);
        if(!domainDTO.isPresent()) {
            return;
        }

        Objects.requireNonNull(cacheManager.getCache(DomainRepository.DomainCache.DOMAIN_BY_CODE)).evict(domainDTO.get().getCode());

        domainRepository.deleteById(id);
    }

    /**
     * Get domain entity by "code"
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DomainDTO> findOne(String code) {
        log.debug("Request to get Domain by code : {} ", code);
        return domainRepository.findByCode(StringUtil.upperCase(code))
            .map(domainMapper::toDto);
    }

    /**
     * Get domain entity by "code" with excluded values
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DomainDTO> findOneWithExcludedValues(String code, List<String> excludedValues) {
        log.debug("Request to get Domain with excluded values by code : {} ", code);

        Optional<DomainDTO> domainDTO = domainRepository.findByCode(StringUtil.upperCase(code))
                .map(domainMapper::toDto);

        Set<DomainValueDTO> filteredDomainValues = new HashSet<>();
        if(domainDTO.isPresent()) {
            DomainDTO domain = domainDTO.get();
            Set<DomainValueDTO> domainValues = domain.getDomainValues();
            domainValues.forEach(domainValueDTO -> {
                if(!excludedValues.contains(domainValueDTO.getValue())) {
                    filteredDomainValues.add(domainValueDTO);
                }
            });
        }
        if(domainDTO.isPresent()) {
            domainDTO.get().setDomainValues(filteredDomainValues);
        }

        return domainDTO;
    }
}
