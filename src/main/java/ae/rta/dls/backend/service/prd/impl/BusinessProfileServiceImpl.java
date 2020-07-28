package ae.rta.dls.backend.service.prd.impl;

import ae.rta.dls.backend.service.prd.BusinessProfileService;
import ae.rta.dls.backend.domain.prd.BusinessProfile;
import ae.rta.dls.backend.repository.prd.BusinessProfileRepository;
import ae.rta.dls.backend.service.dto.prd.BusinessProfileDTO;
import ae.rta.dls.backend.service.mapper.prd.BusinessProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing BusinessProfile.
 */
@Service
@Transactional
public class BusinessProfileServiceImpl implements BusinessProfileService {

    private final Logger log = LoggerFactory.getLogger(BusinessProfileServiceImpl.class);

    private final BusinessProfileRepository businessProfileRepository;

    private final BusinessProfileMapper businessProfileMapper;

    public BusinessProfileServiceImpl(BusinessProfileRepository businessProfileRepository, BusinessProfileMapper businessProfileMapper) {
        this.businessProfileRepository = businessProfileRepository;
        this.businessProfileMapper = businessProfileMapper;
    }

    /**
     * Save a businessProfile.
     *
     * @param businessProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessProfileDTO save(BusinessProfileDTO businessProfileDTO) {
        log.debug("Request to save BusinessProfile : {}", businessProfileDTO);
        BusinessProfile businessProfile = businessProfileMapper.toEntity(businessProfileDTO);
        businessProfile = businessProfileRepository.save(businessProfile);
        return businessProfileMapper.toDto(businessProfile);
    }

    /**
     * Get all the businessProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessProfiles");
        return businessProfileRepository.findAll(pageable)
            .map(businessProfileMapper::toDto);
    }


    /**
     * Get one businessProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessProfileDTO> findOne(Long id) {
        log.debug("Request to get BusinessProfile : {}", id);
        return businessProfileRepository.findById(id)
            .map(businessProfileMapper::toDto);
    }

    /**
     * Get by traffic code number.
     *
     * @param trafficCodeNo
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessProfileDTO> findByTrafficCodeNo(Long trafficCodeNo) {
        log.debug("Request to get BusinessProfile by traffic code No: {}", trafficCodeNo);
        return businessProfileRepository.findByTrafficCodeNo(trafficCodeNo)
            .map(businessProfileMapper::toDto);
    }

    /**
     * Delete the businessProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessProfile : {}", id);
        businessProfileRepository.deleteById(id);
    }
}
